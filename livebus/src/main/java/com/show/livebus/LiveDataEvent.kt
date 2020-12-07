package com.show.livebus

import android.util.ArrayMap
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.lang.ref.WeakReference
import java.util.ArrayList

open class LiveDataEvent<T> constructor(value: T? = null, private var isSticky: Boolean = false) :
    MutableLiveData<T>() {

    private val wrapperStores by lazy { ArrayMap<Int, Boolean>() }

    init {
        if (value != null) {
            this.value = value
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        var storeId = -1
        if (!isSticky) {
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
            if (wrapperStores[storeId] == null) {
                wrapperStores[storeId] = true
            }
        }
        val callback = Observer<T> {
            if (!isSticky) {
                if (wrapperStores[storeId] != null) {
                    val store = wrapperStores[storeId]!!
                    if (!store) {
                        wrapperStores[storeId] = true
                        observer.onChanged(it)
                    }
                }
            } else {
                observer.onChanged(it)
            }
        }
        val lifeCallBack = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                removeObservers(owner)
                removeObserver(callback)
                owner.lifecycle.removeObserver(this)
            }
        }
        owner.lifecycle.addObserver(lifeCallBack)
        super.observe(owner, callback)
    }

    fun observeForever(owner: LifecycleOwner, observer: Observer<in T>) {
        var storeId = -1
        if (!isSticky) {
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
            if (wrapperStores[storeId] == null) {
                wrapperStores[storeId] = true
            }
        }
        val callback = Observer<T> {
            if (!isSticky) {
                if (wrapperStores[storeId] != null) {
                    val store = wrapperStores[storeId]!!
                    if (!store) {
                        wrapperStores[storeId] = true
                        observer.onChanged(it)
                    }
                }
            } else {
                observer.onChanged(it)
            }
        }
        val lifeCallBack = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                removeObservers(owner)
                removeObserver(callback)
                owner.lifecycle.removeObserver(this)
            }
        }
        owner.lifecycle.addObserver(lifeCallBack)
        super.observeForever(callback)
    }


    @Synchronized
    override fun setValue(value: T?) {
        if (!isSticky) {
            for (entry in wrapperStores.entries) {
                entry.setValue(false)
            }
        }
        super.setValue(value)
    }

}