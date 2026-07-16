package com.example.habittracker.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitListItemBinding
import com.example.habittracker.model.Habit

class HabitListAdapter(
    private val habitList: ArrayList<Habit>
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

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {

        val habit = habitList[position]

        with(holder.binding) {

            txtTitle.text = habit.name
            txtDesc.text = habit.description

            progressBar.max = habit.goal
            progressBar.progress = habit.currentProgress

            txtProgressRatio.text =
                "${habit.currentProgress} / ${habit.goal} ${habit.unit}"

            val image = holder.itemView.context.resources.getIdentifier(
                habit.icon.lowercase(),
                "drawable",
                holder.itemView.context.packageName
            )

            imageView.setImageResource(image)

            btnPlus.setOnClickListener {

                if (habit.currentProgress < habit.goal) {
                    habit.currentProgress++
                    notifyItemChanged(position)
                }

            }

            btnMinus.setOnClickListener {

                if (habit.currentProgress > 0) {
                    habit.currentProgress--
                    notifyItemChanged(position)
                }

            }

            if (habit.currentProgress == habit.goal) {
                txtStatus.text = "Completed"
                txtStatus.setBackgroundColor(Color.parseColor("#BDFFB6"))
            } else {
                txtStatus.text = "In Progress"
                txtStatus.setBackgroundColor(Color.parseColor("#E8E7E7"))
            }
        }
    }

    override fun getItemCount(): Int = habitList.size

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    class HabitViewHolder(
        val binding: HabitListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}