package com.example.habittracker.model

class Model {
    data class Habit(
        val name: String?,
        val description: String?,
        val goal: Int?,
        var currentProgress: Int?,
        val unit: String?,
        val icon: String?
    )
}