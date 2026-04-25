package com.example.habittracker.view

import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitListItemBinding
import com.example.habittracker.model.Habit

class HabitListAdapter (val habitList:ArrayList<Habit>){
    class HabitViewHolder(var binding: HabitListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

}