package com.show.livebus

import android.util.ArrayMap
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicLong

open class LiveDataEvent<T> constructor(
    value: T? = null, private var isSticky: Boolean = false,
    private var distinctUntilChanged: Boolean = false
) :
    MutableLiveData<T>() {

    private val mLiveVersion = AtomicLong(-1)

    init {
        if (value != null) {
            this.value = value
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val out = if (isSticky.not()) {
            val wrapper : Observer<in T>  = ObserverWrapper(mLiveVersion.get(), observer)
            super.observe(owner, wrapper)
            wrapper
        } else {
            super.observe(owner, observer)
            observer
        }
        val lifeCallBack = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                removeObservers(owner)
                removeObserver(out)
                owner.lifecycle.removeObserver(this)
            }
        }
        owner.lifecycle.addObserver(lifeCallBack)
    }

    fun observeForever(owner: LifecycleOwner, observer: Observer<in T>) {
        val out = if (isSticky.not()) {
            val wrapper : Observer<in T> = ObserverWrapper(mLiveVersion.get(), observer)
            super.observeForever(wrapper)
            wrapper
        } else {
            super.observeForever(observer)
            observer
        }
        val lifeCallBack = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                removeObservers(owner)
                removeObserver(out)
                owner.lifecycle.removeObserver(this)
            }
        }
        owner.lifecycle.addObserver(lifeCallBack)
    }


    inner class ObserverWrapper<T>(
        private val parentVersion: Long,
        private val observer: Observer<in T>
    ) : Observer<T> {
        override fun onChanged(t: T) {
            if (mLiveVersion.get() > parentVersion) {
                observer.onChanged(t)
            }
        }
    }

    @Synchronized
    override fun setValue(value: T?) {
        if (isSticky.not()) {
            mLiveVersion.getAndIncrement()
        }
        if (distinctUntilChanged && value == getValue()) {
            return
        }
        super.setValue(value)
    }

}