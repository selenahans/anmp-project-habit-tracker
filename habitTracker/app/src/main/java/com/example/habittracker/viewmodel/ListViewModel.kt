package com.example.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.model.Habit

class ListViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

        habitsLD.value = arrayListOf(
            Habit(
                "Drink Water",
                "Stay hydrated throughout the day",
                8,
                3,
                "glasses",
                "Water"
            ),
            Habit(
                "Morning Jogging",
                "Burn calories and stay fit",
                30,
                15,
                "minutes",
                "Run"
            ),
            Habit(
                "Reading Book",
                "Improve knowledge and focus",
                20,
                5,
                "pages",
                "Books"
            )
        )

        loadingLD.value = false
    }
}