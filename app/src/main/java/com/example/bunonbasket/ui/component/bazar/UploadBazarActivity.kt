package com.example.bunonbasket.ui.component.bazar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityUploadBazarBinding

class UploadBazarActivity : AppCompatActivity() {

    lateinit var binding:ActivityUploadBazarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_bazar)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}