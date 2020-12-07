package com.show.live

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.show.livebus.LiveDataEvent

/**
 *  com.show.myapplication
 *  2020/9/30
 *  11:05
 *  ShowMeThe
 */
class MyApplication : Application() {

    companion object{

        val life = LiveDataEvent<String>()

        val live = MutableLiveData<String>()

    }

    override fun onCreate() {
        super.onCreate()


    }
}