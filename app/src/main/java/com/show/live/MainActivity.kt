package com.show.live

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }


        btn2.setOnClickListener {
           btn2.postDelayed({
               getShareViewModel().life.value = "data"
           },5000)
        }

        btn3.setOnClickListener {
            btn2.postDelayed({
                getShareViewModel().life.broadcast("MyName","data")
            },2000)
        }


        startService(Intent(this,ProcessService::class.java))

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}