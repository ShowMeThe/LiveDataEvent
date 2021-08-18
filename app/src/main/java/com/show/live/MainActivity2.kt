package com.show.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn.setOnClickListener {
            getShareViewModel().life.observeForever(this){
                Log.e("222222222","first observeForever == $it")
            }
        }


        btn2.setOnClickListener {
            getShareViewModel().life.observe(this){
                Log.e("222222222","Secound == $it")
            }
        }


    }


    override fun onBackPressed() {
        finish()
    }
}