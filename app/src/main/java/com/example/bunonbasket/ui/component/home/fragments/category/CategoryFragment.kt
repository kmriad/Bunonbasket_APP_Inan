package com.example.bunonbasket.ui.component.home.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.databinding.FragmentCategoryBinding
import com.example.bunonbasket.ui.component.home.adapters.CategoryAdapter
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category), CategoryAdapter.OnItemClickListener {

    private val categoryViewModel: CategoryViewModel by viewModels()
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            categoryAdapter = CategoryAdapter(this@CategoryFragment)
            binding.categoryListView.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }

            categoryViewModel.fetchRemoteEvents(CategoryStateEvent.FetchCategories)
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        categoryViewModel.categoryState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<CategoryModel> -> {
                    dataState.data.let { categoryModel ->
                        categoryAdapter.submitList(categoryModel.categories)
                    }
                }
            }
        })
    }

    override fun onItemClick(category: Category) {
        TODO("Not yet implemented")
    }

}