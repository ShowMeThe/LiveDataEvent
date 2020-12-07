package com.show.live

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.show.livebus.BroadcastLiveDataBus
import com.show.livebus.LiveDataEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType


class MainActivity : AppCompatActivity() {

    private val live = BroadcastLiveDataBus<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.life.observe(this, Observer {
            it?.apply {
                Log.e("222222222","from new $it")
            }
        })


        MyApplication.live.observe(this, Observer {
            it?.apply {
                Log.e("222222222","from old $it")
            }
        })

        MyApplication.life.observeForever(this, Observer {
            it?.apply {
                Log.e("222222222","from new observeForever $it")
            }
        })


        btn.setOnClickListener {
             startActivity(Intent(this,MainActivity2::class.java))
        }

    }
}