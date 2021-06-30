package com.example.bunonbasket.ui.component

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityCheckoutCompleteBinding
import com.example.bunonbasket.ui.component.orderhistory.OrdersActivity

class CheckoutCompleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.DemoTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout_complete)

        binding.deliveryStatusBtn.setOnClickListener {
            var intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }
    }
}