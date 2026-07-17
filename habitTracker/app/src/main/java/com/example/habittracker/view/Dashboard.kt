package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.databinding.FragmentDashboardBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel

class Dashboard : Fragment(), HabitClickListener {

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(
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

        habitListAdapter = HabitListAdapter(
            arrayListOf(),
            this
        )

        binding.recViewHabits.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recViewHabits.adapter =
            habitListAdapter

        observeViewModel()

        viewModel.refresh()

        binding.btnAddHabit.setOnClickListener {
            val action =
                DashboardDirections.actionDashboardToCreateHabit()

            findNavController().navigate(action)
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {

        viewModel.habitsLD.observe(
            viewLifecycleOwner,
            Observer { habitList ->

                habitListAdapter.updateHabitList(habitList)
            }
        )

        viewModel.habitLoadErrorLD.observe(
            viewLifecycleOwner,
            Observer { isError ->

                if (isError == true) {
                    binding.txtError.visibility = View.VISIBLE
                } else {
                    binding.txtError.visibility = View.GONE
                }
            }
        )

        viewModel.loadingLD.observe(
            viewLifecycleOwner,
            Observer { isLoading ->

                if (isLoading == true) {
                    binding.recViewHabits.visibility = View.GONE
                    binding.progressLoad.visibility = View.VISIBLE
                } else {
                    binding.recViewHabits.visibility = View.VISIBLE
                    binding.progressLoad.visibility = View.GONE
                }
            }
        )
    }

    override fun onPlusClick(habit: Habit) {
        if (habit.currentProgress < habit.goal) {
            habit.currentProgress++
            viewModel.updateHabit(habit)
        }
    }

    override fun onMinusClick(habit: Habit) {
        if (habit.currentProgress > 0) {
            habit.currentProgress--
            viewModel.updateHabit(habit)
        }
    }

    override fun onTitleClick(habit: Habit) {

        val action =
            DashboardDirections
                .actionDashboardToEditHabit(habit.uuid)

        findNavController().navigate(action)
    }
}