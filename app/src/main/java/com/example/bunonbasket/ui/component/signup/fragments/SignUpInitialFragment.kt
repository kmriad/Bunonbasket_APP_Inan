package com.example.bunonbasket.ui.component.signup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.FragmentSignUpInitialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInitialFragment : Fragment() {

    lateinit var binding: FragmentSignUpInitialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_initial, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.inputNumberLayout.setOnClickListener { text ->
                val action =
                    SignUpInitialFragmentDirections.actionSignUpInitialFragmentToInputNumberFragment()
                text.findNavController().navigate(action)
            }
        }
    }

}