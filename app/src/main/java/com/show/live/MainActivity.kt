package com.show.live

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    val live = LiveDataEvent<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("222222222","onCreate")

        getShareViewModel().life.observe(this, Observer {
            it?.apply {
                Log.e("222222222","from new $it")
            }
        })




//        getShareViewModel().life.observeForever(this, Observer {
//            it?.apply {
//                Log.e("222222222","from new observeForever $it")
//            }
//        })


        btn.setOnClickListener {
             startActivity(Intent(this,MainActivity2::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}