package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HabitViewModel(
    application: Application
) : AndroidViewModel(application), CoroutineScope {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLD = MutableLiveData<Habit>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {

        loadingLD.value = true

        launch {
            val dao = HabitDatabase
                .getInstance(getApplication())
                .habitDao()

            habitsLD.postValue(
                ArrayList(dao.selectAllHabit())
            )

            habitLoadErrorLD.postValue(false)
            loadingLD.postValue(false)
        }
    }

    fun fetch(id: Int) {

        launch {
            val dao = HabitDatabase
                .getInstance(getApplication())
                .habitDao()

            habitLD.postValue(
                dao.selectHabit(id)
            )
        }
    }

    fun saveHabit(habit: Habit) {

        launch {
            val dao = HabitDatabase
                .getInstance(getApplication())
                .habitDao()

            dao.insertAll(habit)

            habitsLD.postValue(
                ArrayList(dao.selectAllHabit())
            )
        }
    }

    fun updateHabit(habit: Habit) {

        launch {
            val dao = HabitDatabase
                .getInstance(getApplication())
                .habitDao()

            dao.updateHabit(habit)

            habitsLD.postValue(
                ArrayList(dao.selectAllHabit())
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}