package com.rahat.test_login.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rahat.test_login.data.User
import com.rahat.test_login.databinding.FragmentDashboardBinding
import com.rahat.test_login.utils.SharedPrefManager
import com.rahat.test_login.utils.Utils

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater)

        val userData = SharedPrefManager.get<User>(Utils.USER_PREF)
        binding.data.text =  userData.toString()
        return binding.root
    }


}