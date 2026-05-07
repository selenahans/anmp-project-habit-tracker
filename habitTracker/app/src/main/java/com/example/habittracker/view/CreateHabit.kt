package com.example.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentCreateHabitBinding
import com.example.habittracker.model.Model
import com.example.habittracker.viewmodel.HabitViewModel
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateHabit.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateHabit : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var habitViewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)

        habitViewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

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
        val iconAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            icons
        )
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = iconAdapter

        binding.btnCreateHabit.setOnClickListener {
            binding.btnCreateHabit.isEnabled = false
            createHabit()
        }

        return binding.root
    }
    private fun createHabit() {
        val name = binding.txtHabitName.text.toString()
        val description = binding.txtDescription.text.toString()
        val goals = binding.txtGoal.text.toString().toIntOrNull()
        val unit = binding.txtUnit.text.toString()
        val selectedIcon = binding.spinner2.selectedItem as? String

        if (name.isEmpty() || description.isEmpty() || goals == null || selectedIcon == null) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            binding.btnCreateHabit.isEnabled = true
        } else {
            val newHabit = Model.Habit(name, description, goals, 0, unit, selectedIcon)
            habitViewModel.saveHabit(newHabit)
            Toast.makeText(context, "Habit Created", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateHabit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateHabit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}