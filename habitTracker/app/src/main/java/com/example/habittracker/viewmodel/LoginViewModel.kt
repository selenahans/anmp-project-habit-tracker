package com.example.habittracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.habittracker.model.HabitDatabase
import com.example.habittracker.model.User

class LoginViewModel : ViewModel() {

    fun login(
        context: Context,
        username: String,
        password: String
    ): Boolean {

        val dao = HabitDatabase(context).userDao()

        // Membuat akun default jika belum ada user
        if (dao.countUser() == 0) {
            dao.insertAll(
                User(
                    username = "student",
                    password = "123"
                )
            )
        }

        val user = dao.login(username, password)

        return if (user != null) {

            val pref = context.getSharedPreferences(
                "session",
                Context.MODE_PRIVATE
            )

            pref.edit()
                .putBoolean("isLogin", true)
                .putString("username", user.username)
                .putInt("userId", user.uuid)
                .apply()

            true

        } else {

            false

        }
    }

    fun logout(context: Context) {

        val pref = context.getSharedPreferences(
            "session",
            Context.MODE_PRIVATE
        )

        pref.edit().clear().apply()
    }
}