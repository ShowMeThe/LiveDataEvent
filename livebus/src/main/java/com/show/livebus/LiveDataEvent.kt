package com.show.livebus

import android.util.ArrayMap
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

open class LiveDataEvent<T> constructor(value: T? = null, private var isSticky: Boolean = false) : MutableLiveData<T>(value) {

    private val wrapperStores by lazy { ArrayMap<Int, Boolean>() }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        var storeId = -1
        if(!isSticky){
            storeId = when (owner) {
                is AppCompatActivity -> {
                    System.identityHashCode(owner.viewModelStore)
                }
                is Fragment -> {
                    System.identityHashCode(owner.viewLifecycleOwner)
                }
                else -> {
                    System.identityHashCode(owner)
                }
            }
            if(wrapperStores[storeId] == null){
                wrapperStores[storeId] = true
            }
        }
        super.observe(owner, {
            if (!isSticky) {
                if (!wrapperStores[storeId]!!) {
                    wrapperStores[storeId] = true
                    observer.onChanged(it)
                }
            } else {
                observer.onChanged(it)
            }
        })
    }




    @Synchronized
    override fun setValue(value: T) {
        if(!isSticky){
            for(entry in wrapperStores.entries){
                entry.setValue(false)
            }
        }
        super.setValue(value)
    }

}