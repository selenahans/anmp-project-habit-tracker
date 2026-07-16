package com.example.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.viewmodel.LoginViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Login : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Auto Login
        val pref = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        if (pref.getBoolean("isLogin", false)) {
            val action = LoginDirections.actionLoginToDashboard()
            findNavController().navigate(action)
            return
        }

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtUsername = view.findViewById<EditText>(R.id.txtUsername)
        val txtPassword = view.findViewById<EditText>(R.id.txtPassword)

        btnLogin.setOnClickListener {

            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (viewModel.login(requireContext(), username, password)) {

                val action = LoginDirections.actionLoginToDashboard()
                Navigation.findNavController(it).navigate(action)

            } else {

                Toast.makeText(
                    context,
                    "Username atau Password Salah",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}