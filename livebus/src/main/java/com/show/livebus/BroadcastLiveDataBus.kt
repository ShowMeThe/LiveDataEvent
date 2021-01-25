package com.show.livebus

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.*
import com.show.livebus.util.Apps
import com.show.livebus.util.Wrapper
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType

/**
 *  com.show.livebus
 *  2020/11/21
 *  17:01
 *  ShowMeThe
 */
const val add_key = "BroadcastLiveDataBus_"
class BroadcastLiveDataBus<T>(value: T? = null) :
    LiveDataEvent<T>(value,false) {

    private val receiverKeeper by lazy { ArrayMap<String, IpcReceiver>() }

    fun broadcast(key:String,t:T){
        value = t
        val application = Apps.getApplication()
        val intent = Intent()
        intent.putExtra(key,Wrapper(t))
        intent.action = "$add_key$key"
        application.sendBroadcast(intent)
    }

    fun observeBroadcast(key: String,owner: LifecycleOwner, observer: Observer<T>){
        val application = Apps.getApplication()
        if(receiverKeeper[key] == null){
            val ipc = IpcReceiver(key)
            receiverKeeper[key] = ipc
            application.registerReceiver(ipc, IntentFilter("$add_key$key"))
            owner.lifecycle.addObserver(object : LifecycleObserver{
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy(){
                    owner.lifecycle.removeObserver(this)
                    application.unregisterReceiver(ipc)
                    receiverKeeper.remove(key)
                }
            })
        }
        super.observe(owner, observer)
    }


    private inner class IpcReceiver(val key: String) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.action == "$add_key$key"){
                val data = intent.getSerializableExtra(key) as Wrapper<T>
                value = data.t
            }
        }
    }

}