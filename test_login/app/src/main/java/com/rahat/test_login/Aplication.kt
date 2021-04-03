package com.rahat.test_login

import android.app.Application
import com.rahat.test_login.utils.SharedPrefManager


class Aplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefManager.with(this)
    }
}
