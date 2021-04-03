package com.rahat.test_login.utils

import android.util.Patterns
import java.security.MessageDigest

class Utils {
    companion object {

        const val USER_PREF = "user"
        fun convertPassToHash(text: String): String {
            val bytes = text.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }

        fun isValidEmail(email: String): Boolean {

            return if (email.isEmpty()) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }


        }
    }


}
