package com.rahat.test_login.ui.Registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rahat.test_login.data.User
import com.rahat.test_login.databinding.FragmentRegistrationBinding
import com.rahat.test_login.utils.Utils
import kotlinx.coroutines.flow.collect

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater)

        // registering viewModel to the view
        registerViewModel = ViewModelProviders.of(this)
            .get(RegisterViewModel::class.java)

        binding.regBtn.setOnClickListener {
            // sending the  object of user to validate data
            registerViewModel.checkUserData(
                User("",
                    binding.userNameET.text.toString(),
                    binding.emailEt.text.toString(),
                    Utils.convertPassToHash(binding.passordEt.text.toString())
                )

            )
        }

        // listening for the event
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            registerViewModel.registerEvent.collect { event ->
                when(event){
                    is RegisterViewModel.RegisterEvent.RegisterDone -> {
                        // registration complete
                        findNavController().popBackStack()

                    }
                    is RegisterViewModel.RegisterEvent.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), event.msg , Toast.LENGTH_LONG).show()
                    }

                    is RegisterViewModel.RegisterEvent.ShowProgressBar ->{
                        if(event.show){
                            binding.progressBar.visibility =View.VISIBLE
                        }else {
                            binding.progressBar.visibility =View.INVISIBLE
                        }
                    }
                }
            }
        }

        return binding.root
    }


}