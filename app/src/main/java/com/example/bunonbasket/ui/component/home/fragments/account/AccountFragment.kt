package com.example.bunonbasket.ui.component.home.fragments.account

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
import androidx.navigation.fragment.findNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.databinding.FragmentAccountBinding
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            accountViewModel.setStateEvent(AccountStateEvent.LoadToken)

            subscribeObservers()

            binding.loginButton.setOnClickListener {
                accountViewModel.onLoginButtonClicked()
            }

            binding.orderHistory.setOnClickListener {
                accountViewModel.onOrderHistoryClicked()
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                accountViewModel.accountStateEvent.collect { event ->
                    when (event) {
                        is AccountStateEvent.NavigateToLoginActivity -> {
                            val action =
                                AccountFragmentDirections.actionAccountFragmentToLoginActivity()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                accountViewModel.profileStateEvent.collect { event ->
                    when (event) {
                        is AccountStateEvent.NavigateToOrderHistory -> {
                            val action =
                                AccountFragmentDirections.actionAccountFragmentToOrdersActivity()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        try {
            accountViewModel.token.observe(viewLifecycleOwner, { dataState ->
                when (dataState) {
                    is String -> {
                        if (dataState.isNotEmpty()) {
                            accountViewModel.setStateEvent(AccountStateEvent.LoadProfile)
                            subscribeProfile()
                        }
                    }
                }
            })
        } catch (exception: Exception) {
            Toast.makeText(activity, "Please Login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeProfile() {
        accountViewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<List<LoginModel>> -> {
                    dataState.data.let { loginModel ->
                        Log.d("AccountFragment", loginModel.get(0).token)
                        binding.data = loginModel.get(0)
                        binding.topLayout.visibility = View.GONE
                        binding.optionsOne.visibility = View.GONE
                        binding.profileSection.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.topLayout.visibility = View.VISIBLE
                    binding.optionsOne.visibility = View.VISIBLE
                    binding.profileSection.visibility = View.GONE
                }
            }
        })
    }
}