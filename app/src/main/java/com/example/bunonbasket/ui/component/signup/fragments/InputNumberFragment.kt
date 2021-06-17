package com.example.bunonbasket.ui.component.signup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.FragmentInputNumberBinding
import com.example.bunonbasket.ui.component.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputNumberFragment : Fragment() {

    lateinit var binding: FragmentInputNumberBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_input_number, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            binding.forwardButton.setOnClickListener {
                if (binding.phoneNumberInputField.text.toString().length >= 11) {
                    val action =
                        InputNumberFragmentDirections.actionInputNumberFragmentToSignUpFragment(
                            binding.phoneNumberInputField.text.toString()
                        )
                    findNavController().navigate(action)
                }
            }
//            viewModel.addSourcetToPhoneNumber()
//            viewModel.userPhoneNumberValidator.observe(viewLifecycleOwner, { validator ->
//                binding.forwardButton.isEnabled = validator
//            })

//            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//                viewModel.signUpEvent.collect { event ->
//                    when (event) {
//                        is SignupStateEvent.NavigateToSignUpFragment -> {
//                            Log.d("InputNumberFragment", event.phoneNumber)
//
//                        }
//                    }
//                }
        }
    }
}

