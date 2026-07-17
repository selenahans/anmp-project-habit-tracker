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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.viewmodel.LoginViewModel

class Login : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_login,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        val pref = requireContext().getSharedPreferences(
            "session",
            Context.MODE_PRIVATE
        )

        if (pref.getBoolean("isLogin", false)) {
            val action =
                LoginDirections.actionLoginToDashboard()

            findNavController().navigate(action)
            return
        }

        val btnLogin =
            view.findViewById<Button>(R.id.btnLogin)

        val txtUsername =
            view.findViewById<EditText>(R.id.txtUsername)

        val txtPassword =
            view.findViewById<EditText>(R.id.txtPassword)

        viewModel.loginResultLD.observe(
            viewLifecycleOwner,
            Observer { isSuccess ->

                if (isSuccess == true) {
                    val action =
                        LoginDirections.actionLoginToDashboard()

                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Username atau Password Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        btnLogin.setOnClickListener {

            val username =
                txtUsername.text.toString().trim()

            val password =
                txtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Username dan Password harus diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            viewModel.login(
                username,
                password
            )
        }
    }
}