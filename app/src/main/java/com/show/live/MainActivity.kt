package com.show.live

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.show.livebus.BroadcastLiveDataBus
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType


class MainActivity : AppCompatActivity() {

    private val live = BroadcastLiveDataBus<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startService(Intent(this,ProcessService::class.java))



        btn.setOnClickListener {
            live.broadcast("data",1)
        }


    }
}