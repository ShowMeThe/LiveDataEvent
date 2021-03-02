package com.show.live

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.show.livebus.BroadcastLiveDataBus

/**
 *  com.show.live
 *  2020/11/21
 *  16:59
 *  ShowMeThe
 */
class ProcessService : LifecycleService() {

    override fun onCreate() {
        super.onCreate()


        getShareViewModel().life.observeBroadcast("data",this,{
            it?.apply {
                Log.e("22222","${it}")
            }
        })

    }

}