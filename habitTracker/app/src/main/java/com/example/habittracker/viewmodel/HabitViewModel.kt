package com.example.habittracker.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDatabase

class HabitViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()

    val loadingLD = MutableLiveData<Boolean>()

    fun refresh(context: Context) {

        loadingLD.value = true

        val dao = HabitDatabase
            .getInstance(context)
            .habitDao()

        habitsLD.value =
            ArrayList(dao.selectAllHabit())

        loadingLD.value = false

        habitLoadErrorLD.value = false
    }

    fun saveHabit(
        context: Context,
        habit: Habit
    ) {
        HabitDatabase.getInstance(context).habitDao().insertAll(habit)
        refresh(context)
    }

    fun updateHabit(
        context: Context,
        habit: Habit
    ) {
        HabitDatabase.getInstance(context).habitDao().updateHabit(habit)
        refresh(context)
    }
}