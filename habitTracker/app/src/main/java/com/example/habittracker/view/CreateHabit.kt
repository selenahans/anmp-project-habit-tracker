package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.databinding.FragmentCreateHabitBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel

class CreateHabit : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateHabitBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        setupSpinner()

        binding.btnCreateHabit.setOnClickListener {
            createHabit()
        }
    }

    private fun setupSpinner() {

        val icons = arrayOf(
            "Biking",
            "Books",
            "Exercise",
            "Laptop",
            "Meditate",
            "Music",
            "Protein",
            "Run",
            "Salad"
        )

        val iconAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            icons
        )

        iconAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinner2.adapter = iconAdapter
    }

    private fun createHabit() {

        val name =
            binding.txtHabitName.text.toString().trim()

        val description =
            binding.txtDescription.text.toString().trim()

        val goal =
            binding.txtGoal.text.toString().toIntOrNull()

        val unit =
            binding.txtUnit.text.toString().trim()

        val selectedIcon =
            binding.spinner2.selectedItem.toString().lowercase()

        if (
            name.isEmpty() ||
            description.isEmpty() ||
            goal == null ||
            goal <= 0 ||
            unit.isEmpty()
        ) {
            Toast.makeText(
                requireContext(),
                "Please fill in all fields correctly",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val habit = Habit(
            name = name,
            description = description,
            goal = goal,
            currentProgress = 0,
            unit = unit,
            icon = selectedIcon
        )

        viewModel.saveHabit(habit)

        Toast.makeText(
            requireContext(),
            "Habit Created",
            Toast.LENGTH_SHORT
        ).show()

        findNavController().popBackStack()
    }
}