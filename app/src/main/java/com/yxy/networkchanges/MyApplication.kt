package com.yxy.networkchanges

import android.app.Application
import com.yxy.library.NetworkMonitorManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkMonitorManager.getInstance().init(this)
    }
}