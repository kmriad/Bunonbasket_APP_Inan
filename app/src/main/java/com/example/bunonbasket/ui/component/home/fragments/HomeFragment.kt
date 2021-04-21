package com.example.bunonbasket.ui.component.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bunonbasket.R
import com.example.bunonbasket.ui.component.home.HomeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {


    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}