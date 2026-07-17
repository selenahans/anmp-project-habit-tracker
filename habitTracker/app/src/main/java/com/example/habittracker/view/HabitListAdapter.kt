package com.example.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.databinding.HabitListItemBinding
import com.example.habittracker.model.Habit

interface HabitClickListener {
    fun onTitleClick(habit: Habit)
    fun onPlusClick(habit: Habit)
    fun onMinusClick(habit: Habit)
}

class HabitListAdapter(
    private val habitList: ArrayList<Habit>,
    private val listener: HabitClickListener
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {

        val binding = HabitListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        val habit = habitList[position]

        holder.binding.habit = habit
        holder.binding.listener = listener

        val imageId = when (habit.icon.lowercase()) {
            "biking" -> R.drawable.biking
            "books" -> R.drawable.books
            "exercise" -> R.drawable.exercise
            "laptop" -> R.drawable.laptop
            "meditate" -> R.drawable.meditate
            "music" -> R.drawable.music
            "protein" -> R.drawable.protein
            "run" -> R.drawable.run
            "salad" -> R.drawable.salad
            else -> R.drawable.exercise
        }

        holder.binding.imgHabit.setImageResource(imageId)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    class HabitViewHolder(
        val binding: HabitListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}