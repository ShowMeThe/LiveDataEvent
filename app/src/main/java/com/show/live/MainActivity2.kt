package com.show.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn.setOnClickListener {
            getShareViewModel().life.observe(this){

            }
        }





    }


    override fun onBackPressed() {
        finish()
    }
}