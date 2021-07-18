package com.example.bunonbasket.ui.component.signup.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.bunonbasket.R
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.databinding.FragmentSignUpBinding
import com.example.bunonbasket.ui.component.HomeActivity
import com.example.bunonbasket.ui.component.signup.SignUpViewModel
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    val args: SignUpFragmentArgs by navArgs()

    lateinit var mProgressDialog: ProgressDialog

    lateinit var dataStore: DataStoreManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            // instantiate it within the onCreate method
            mProgressDialog = ProgressDialog(activity);
            mProgressDialog.setMessage("Registering your profile");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);

            binding.data = viewModel

            dataStore = DataStoreManager(requireContext())

            binding.signUpButton.setOnClickListener {
                if (binding.nameEditText.text.toString().isNotEmpty()) {
                    if (binding.passwordEditText.text.toString().isNotEmpty()) {
                        viewModel.registerUser(
                            args.phoneNumber,
                            name = binding.nameEditText.text.toString(),
                            password = binding.passwordEditText.text.toString()
                        )
                    }
                }
            }

            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        viewModel.loginState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<LoginModel>> -> {
                    mProgressDialog.dismiss()
                    dataState.data.let { loginModel ->
                        viewModel.saveUserProfile(loginModel.results)
                        lifecycleScope.launch {
                            dataStore.saveAuthToken(loginModel.results.token)
                        }
                        Toast.makeText(activity, "Logged In Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }
                is Resource.Loading -> {
                    mProgressDialog.show()
                }
                is Resource.Error -> {
                    mProgressDialog.dismiss()
                }
            }
        })

        viewModel.tokenState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<String> -> {
                    dataState.data.let { data ->
                        viewModel.saveToken(data)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { e ->
                        Log.d("SignUpFragment", e.message.toString())
                    }
                }
            }
        })
        viewModel.saveUserState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<Long> -> {
                    requireActivity().onBackPressed()
                }
                is Resource.Error -> {
                }
            }
        })
    }
}