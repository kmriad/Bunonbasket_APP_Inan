package com.example.bunonbasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Remove title bar
        getSupportActionBar()!!.hide();
        setContentView(R.layout.activity_main)
    }
}
