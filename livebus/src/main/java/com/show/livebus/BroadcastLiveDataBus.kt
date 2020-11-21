package com.show.livebus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.show.livebus.util.Apps
import com.show.livebus.util.Wrapper
import java.lang.IllegalStateException

/**
 *  com.show.livebus
 *  2020/11/21
 *  17:01
 *  ShowMeThe
 */
class BroadcastLiveDataBus<T>(value: T? = null, isSticky: Boolean = false) :
    LiveDataEvent<Wrapper<T>>(Wrapper(value),isSticky) {

    companion object{
        private const val add_key = "BroadcastLiveDataBus_"
    }


    fun broadcast(key:String,t:T){
        setValue(Wrapper(t))
        val application = Apps.getApplication()
        val intent = Intent()
        intent.putExtra(key,value)
        intent.action = "$add_key$key"
        application.sendBroadcast(intent)
    }


    fun observeBroadcast(key: String,owner: LifecycleOwner, observer: Observer<in Wrapper<T>>){
        val receiver = IpcReceiver(key)
        val application = Apps.getApplication()
        application.registerReceiver(receiver, IntentFilter("$add_key$key"))
        super.observe(owner, observer)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in Wrapper<T>>) {
        throw IllegalStateException("use observeBroadcast instead")
    }


    inner class IpcReceiver(val key: String) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.action == "$add_key$key"){
                val data = intent.getSerializableExtra(key) as Wrapper<T>
                setValue(data)
            }
        }
    }

}