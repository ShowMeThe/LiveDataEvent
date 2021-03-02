package com.show.livebus.util

import android.app.Application
import android.content.Context
import com.show.launch.Initializer
import kotlinx.coroutines.CancellableContinuation

class LiveDataBusInitializer :Initializer<Boolean> {
    override fun onCreate(
        context: Context,
        isMainProcess: Boolean,
        continuation: CancellableContinuation<Boolean>?
    ) {
        Apps.initApplication(context.applicationContext as Application)
    }

}