package com.example.habittracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Model

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("habitTracker", Context.MODE_PRIVATE)

    val habitsLD = MutableLiveData<ArrayList<Model.Habit>>()

    val loadingLD = MutableLiveData<Boolean>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()

    fun saveHabit(habit: Model.Habit) {
        val editor = sharedPreferences.edit()

        editor.putString("habit_name", habit.name)
        editor.putString("habit_description", habit.description)
        editor.putInt("habit_progress", habit.currentProgress ?: 0)
        editor.putInt("habit_goal", habit.goal ?: 0)
        editor.putString("habit_unit", habit.unit)
        editor.putString("habit_icon", habit.icon)

        editor.apply()
    }

    fun refresh() {
        loadingLD.value = true

        val habitList = ArrayList<Model.Habit>()
        val name = sharedPreferences.getString("habit_name", null)
        val description = sharedPreferences.getString("habit_description", null)
        val progress = sharedPreferences.getInt("habit_progress", 0)
        val goal = sharedPreferences.getInt("habit_goal", 0)
        val unit = sharedPreferences.getString("habit_unit", null)
        val icon = sharedPreferences.getString("habit_icon", null)

        if (name != null && description != null) {
            habitList.add(Model.Habit(name, description, goal, progress, unit, icon))
        }

        habitsLD.value = habitList
        habitLoadErrorLD.value = habitList.isEmpty()
        loadingLD.value = false
    }
}