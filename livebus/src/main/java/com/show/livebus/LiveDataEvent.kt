package com.show.livebus

import android.util.ArrayMap
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.util.ArrayList

open class LiveDataEvent<T> constructor(value: T? = null, private var isSticky: Boolean = false) : MutableLiveData<T>() {

    private val wrapperStores by lazy { ArrayMap<Int, Boolean>() }
    private val lifeOwnerKeeper = ArrayList<LifeOwnerKeeper>()

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
            lifeOwnerKeeper.add(LifeOwnerKeeper(owner, storeId))
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

    fun observeForever(owner: LifecycleOwner,observer: Observer<in T>) {
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
            lifeOwnerKeeper.add(LifeOwnerKeeper(owner, storeId))
        }
        super.observeForever {
            if (!isSticky) {
                if (!wrapperStores[storeId]!!) {
                    wrapperStores[storeId] = true
                    observer.onChanged(it)
                }
            } else {
                observer.onChanged(it)
            }
        }
    }


    private inner class LifeOwnerKeeper(var owner: LifecycleOwner?, val code: Int)
        : LifecycleObserver {
        init {
            owner?.lifecycle?.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            if(BuildConfig.DEBUG){
                Log.d("LiveDataEvent","$owner destroy")
            }
            wrapperStores.remove(code)
            owner?.lifecycle?.removeObserver(this)
            owner = null
            lifeOwnerKeeper.remove(this)
        }
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