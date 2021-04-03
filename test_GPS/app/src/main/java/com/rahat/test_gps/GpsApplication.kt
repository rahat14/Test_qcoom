package com.rahat.test_gps

import android.app.Application
import com.google.android.gms.maps.MapsInitializer

class GpsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(this)
    }
}