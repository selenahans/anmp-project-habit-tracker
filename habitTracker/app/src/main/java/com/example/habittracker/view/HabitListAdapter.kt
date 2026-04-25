package com.example.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitListItemBinding
import com.example.habittracker.model.Model

class HabitListAdapter(val habitList:ArrayList<Model.Habit>)
    :RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>()
{
    override fun onCreateViewHolder
        (parent: ViewGroup, viewType: Int)
    : HabitViewHolder {
        val binding = HabitListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)

    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        with(holder.binding) {
            txtTitle.text = habit.title
            txtDesc.text = habit.description
            val goalValue = habit.goal ?: 0
            val currentProgressValue = habit.currentProgress ?: 0
            progressBar.max = goalValue
            progressBar.progress = currentProgressValue
            txtProgressRatio.text = "$currentProgressValue / $goalValue ${habit.unit ?: ""}"
            btnPlus.setOnClickListener {
                val currentPos = habit.currentProgress ?: 0
                val maxGoal = habit.goal ?: 0
                if (currentPos < maxGoal) {
                    habit.currentProgress = currentPos + 1
                    notifyItemChanged(position)
                }
            }
            btnMinus.setOnClickListener {
                val currentPos = habit.currentProgress ?: 0
                if (currentPos > 0) {
                    habit.currentProgress = currentPos - 1
                    notifyItemChanged(position)
                }
            }
            if (habit.currentProgress == goalValue) {
                txtStatus.text = "Completed"
            } else {
                txtStatus.text = "In Progress"
            }
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }
    fun updateHabitList(newHabitList: ArrayList<Model.Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    class HabitViewHolder(var binding: HabitListItemBinding)
        :RecyclerView.ViewHolder(binding.root)


}
