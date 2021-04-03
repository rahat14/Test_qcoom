package com.rahat.test_login.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.rahat.test_login.R
import com.rahat.test_login.data.User
import com.rahat.test_login.utils.SharedPrefManager
import com.rahat.test_login.utils.Utils
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
         container activity
         it holds the fragments
          */

        checkUserSaved()
    }

    // check user persistance
    fun checkUserSaved() {

        val user: User? = SharedPrefManager.get(Utils.USER_PREF)

        if (user != null) {
            // if user not equal null
            // that means user exist
            // sending the user to dashboard
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.dashboardFragment)

        }


    }


    // back stack management
    override fun onBackPressed() {
        val navigationController = findNavController(R.id.nav_host_fragment)
        when (navigationController.currentDestination?.id) {

            R.id.loginFragment -> {
                triggerDialoguer()
            }
            R.id.dashboardFragment -> {
                triggerDialoguer()
            }
            else -> {
                super.onBackPressed()
            }


        }


    }

    // exist dialogue
    private fun triggerDialoguer() {
        //var dialog : AlertDialog =
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton(
            "OK"
        ) { dialog, _ ->
            dialog.cancel()
            moveTaskToBack(true)
            exitProcess(-1)
        }
        alertDialogBuilder.setNegativeButton("no", null)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }

}