package com.example.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.model.Habit

class HabitViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val habitList = arrayListOf<Habit>()

    fun refresh() {
        habitsLD.value = habitList
        loadingLD.value = false
        habitLoadErrorLD.value = false
    }

    fun saveHabit(habit: Habit) {
        habitList.add(habit)
        habitsLD.value = ArrayList(habitList)
    }
}