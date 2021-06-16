package com.example.bunonbasket.ui.component.signup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.FragmentInputNumberBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputNumberFragment : Fragment() {

    lateinit var binding: FragmentInputNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_input_number, container, false)

        return binding.root
    }

}