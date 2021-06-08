package com.example.bunonbasket.ui.component.home.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.FragmentAccountBinding
import com.example.bunonbasket.ui.component.home.fragments.category.CategoryFragmentDirections
import com.example.bunonbasket.ui.component.home.fragments.category.CategoryStateEvent
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

            binding.loginButton.setOnClickListener {
                accountViewModel.onLoginButtonClicked()
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                accountViewModel.accountStateEvent.collect { event ->
                    when (event) {
                        is AccountStateEvent.NavigateToLoginActivity -> {
                            val action = AccountFragmentDirections.actionAccountFragmentToLoginActivity()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

}