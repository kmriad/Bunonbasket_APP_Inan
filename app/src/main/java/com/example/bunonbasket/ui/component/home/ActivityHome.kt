package com.example.bunonbasket.ui.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bunonbasket.R

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        setContentView(R.layout.activity_home)
    }
}