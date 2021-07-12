package com.example.bunonbasket.ui.component.orderstatus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bunonbasket.ui.component.orderhistory.fragments.CancelledFragment
import com.example.bunonbasket.ui.component.orderhistory.fragments.DeliveryFragment

class FragmentOrderStatusAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DeliveryFragment()
        }

        return CancelledFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}