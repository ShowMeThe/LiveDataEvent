package com.show.live

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.show.livebus.BroadcastLiveDataBus
import com.show.livebus.LiveDataEvent


private val viewModel by lazy { ShareViewModel::class.java.getAppViewModel() }
fun getShareViewModel() = viewModel

inline fun <reified T : ViewModel> Class<T>.getAppViewModel() = ViewModelProvider(
    AppContext.get().context.applicationContext as ViewModelStoreOwner,
    ViewModelProvider.AndroidViewModelFactory(AppContext.get().context.applicationContext as Application)).get(this)

class ShareViewModel(application: Application) : AndroidViewModel(application) {


    val life = LiveDataEvent<String>()


}