package com.rahat.test_login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rahat.test_login.R
import com.rahat.test_login.databinding.FragmentLoginBinding
import com.rahat.test_login.utils.Utils
import kotlinx.coroutines.flow.collect


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        loginViewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)

        binding.loginTv.setOnClickListener {
            // sending the  object of user to validate data
            loginViewModel.loginUser(
                binding.emailEt.text.toString(),
                Utils.convertPassToHash(binding.passEt.text.toString())
            )
        }

        // listening for the event
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            loginViewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LoginEvent.LoginDone -> {
                        // go to dash board
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }
                    is LoginViewModel.LoginEvent.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), event.msg, Toast.LENGTH_LONG).show()
                    }
                    is LoginViewModel.LoginEvent.ShowProgressBar -> {
                        if (event.show) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }

                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAllClickListeners()
    }

    private fun setAllClickListeners() {
        binding.signUpTv.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }
}