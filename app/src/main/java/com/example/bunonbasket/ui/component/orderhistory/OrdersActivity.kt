package com.example.bunonbasket.ui.component.orderhistory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityOrderHistoryBinding
import com.example.bunonbasket.ui.component.orderhistory.adapters.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrdersActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderHistoryBinding
    lateinit var fragmentAdapter: FragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_history)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val fm: FragmentManager = supportFragmentManager
        fragmentAdapter = FragmentAdapter(fm, lifecycle)
        binding.pager.apply {
            adapter = fragmentAdapter
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Orders"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Delivered"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Canceled"))

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val parentIntent = NavUtils.getParentActivityIntent(this)
                parentIntent!!.flags =
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(parentIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}