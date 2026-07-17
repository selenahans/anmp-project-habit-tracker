package com.example.habittracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.HabitDatabase
import com.example.habittracker.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    application: Application
) : AndroidViewModel(application), CoroutineScope {

    val loginResultLD = MutableLiveData<Boolean>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun login(
        username: String,
        password: String
    ) {
        launch {
            val context = getApplication<Application>()

            val dao = HabitDatabase
                .getInstance(context)
                .userDao()

            if (dao.countUser() == 0) {
                dao.insertAll(
                    User(
                        username = "student",
                        password = "123"
                    )
                )
            }

            val user = dao.login(
                username,
                password
            )

            if (user != null) {
                val pref = context.getSharedPreferences(
                    "session",
                    Context.MODE_PRIVATE
                )

                pref.edit()
                    .putBoolean("isLogin", true)
                    .putString("username", user.username)
                    .putInt("userId", user.uuid)
                    .apply()

                loginResultLD.postValue(true)
            } else {
                loginResultLD.postValue(false)
            }
        }
    }

    fun logout() {
        val context = getApplication<Application>()

        val pref = context.getSharedPreferences(
            "session",
            Context.MODE_PRIVATE
        )

        pref.edit()
            .clear()
            .apply()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}