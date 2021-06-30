package com.example.bunonbasket.ui.component.orderhistory.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bunonbasket.ui.component.orderhistory.fragments.CancelledFragment
import com.example.bunonbasket.ui.component.orderhistory.fragments.DeliveryFragment
import com.example.bunonbasket.ui.component.orderhistory.fragments.OrdersFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return DeliveryFragment()
            2 -> return CancelledFragment()
        }

        return OrdersFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}