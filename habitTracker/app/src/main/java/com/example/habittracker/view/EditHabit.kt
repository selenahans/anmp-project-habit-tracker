package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentEditHabitBinding
import com.example.habittracker.viewmodel.HabitViewModel

class EditHabit : Fragment() {

    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: HabitViewModel

    private val args: EditHabitArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_habit,
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

        viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val icons = arrayOf(
            "Biking",
            "Books",
            "Exercise",
            "Laptop",
            "Meditate",
            "Music",
            "Protein",
            "Run",
            "Salad",
            "Water"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            icons
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinner2.adapter = adapter

        viewModel.fetch(args.habitId)

        viewModel.selectedIcon.observe(viewLifecycleOwner) { icon ->
            val index = icons.indexOf(icon)
            if (index >= 0) {
                binding.spinner2.setSelection(index)
            }
        }

        binding.btnSubmit.setOnClickListener {

            val habit = viewModel.habitLD.value ?: return@setOnClickListener

            habit.name = viewModel.habitName.value ?: ""
            habit.description = viewModel.habitDescription.value ?: ""
            habit.goal = viewModel.habitGoal.value?.toIntOrNull() ?: 0
            habit.unit = viewModel.habitUnit.value ?: ""
            habit.icon = binding.spinner2.selectedItem.toString()

            viewModel.updateHabit(habit)

            Toast.makeText(
                requireContext(),
                "Habit Updated",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
        }
    }
}