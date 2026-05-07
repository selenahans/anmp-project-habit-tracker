package com.example.habittracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Model
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("habitTracker", Context.MODE_PRIVATE)
    private val gson = Gson()
    val habitsLD = MutableLiveData<ArrayList<Model.Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()

    fun saveHabit(habit: Model.Habit) {
        val currentList = loadHabits()
        currentList.add(habit)
        val json = gson.toJson(currentList)

        sharedPreferences.edit()
            .putString("habit_list", json)
            .apply()
        habitsLD.value = currentList
    }

    fun refresh() {
        loadingLD.value = true
        val habitList = loadHabits()
        habitsLD.value = habitList
        habitLoadErrorLD.value = habitList.isEmpty()
        loadingLD.value = false
    }

    private fun loadHabits(): ArrayList<Model.Habit> {
        val json = sharedPreferences.getString("habit_list", null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<Model.Habit>>() {}.type
            gson.fromJson(json, type)
        } else {
            arrayListOf()
        }
    }
}