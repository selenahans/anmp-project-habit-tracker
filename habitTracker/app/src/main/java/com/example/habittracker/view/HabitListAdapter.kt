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

        val imageId = holder.itemView.context.resources.getIdentifier(
            habit.icon.lowercase(),
            "drawable",
            holder.itemView.context.packageName
        )

        if (imageId != 0) {
            holder.binding.imgHabit.setImageResource(imageId)
        } else {
            holder.binding.imgHabit.setImageResource(R.drawable.exercise)
        }

        holder.binding.executePendingBindings()
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