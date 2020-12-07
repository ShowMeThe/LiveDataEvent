package com.show.live

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 *  com.show.myapplication
 *  2020/9/30
 *  11:05
 *  ShowMeThe
 */
class MyApplication : Application(), ViewModelStoreOwner {

    private val modelStore by lazy { ViewModelStore() }

    override fun onCreate() {
        super.onCreate()
        AppContext.get().attach(this)


    }

    override fun getViewModelStore(): ViewModelStore = modelStore
}