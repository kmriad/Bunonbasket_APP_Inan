package com.example.bunonbasket.ui.component.home.fragments.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.category.*
import com.example.bunonbasket.databinding.FragmentCategoryBinding
import com.example.bunonbasket.ui.component.home.adapters.CategoryAdapter
import com.example.bunonbasket.ui.component.home.adapters.SubCategoryAdapter
import com.example.bunonbasket.utils.Resource
import com.example.bunonbasket.utils.widgets.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category), CategoryAdapter.OnItemClickListener,
    SubCategoryAdapter.OnItemClickListener {

    private val categoryViewModel: CategoryViewModel by viewModels()
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var subCategoryAdapter: SubCategoryAdapter
    lateinit var dialog: LoadingDialog
    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            categoryAdapter = CategoryAdapter(this@CategoryFragment)
            binding.categoryListView.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
            subCategoryAdapter =
                SubCategoryAdapter(
                    this@CategoryFragment,
                )

            binding.subCategoryListView.apply {
                adapter = subCategoryAdapter
                layoutManager = myLayoutManager
            }

            dialog = activity?.let { LoadingDialog(it) }!!
            dialog.showLoadingDialog()
            categoryViewModel.fetchRemoteEvents(CategoryStateEvent.FetchCategories)
            subscribeObservers()

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                categoryViewModel.subCategoryEvent.collect { event ->
                    when (event) {
                        is CategoryStateEvent.NavigateToProductList -> {
                            val action =
                                CategoryFragmentDirections.actionCategoryFragmentToProductListActivity(
                                    event.subCategory!!
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        categoryViewModel.categoryState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<Category>> -> {
                    dataState.data.let { categoryModel ->
                        val category = args.category
                        for (i in categoryModel.results) {
                            if (i.id == category.id) {
                                i.isSelected = true
                            }
                        }
                        categoryAdapter.submitList(categoryModel.results)
                        categoryViewModel.onCategoryClicked(category)
                    }
                }
            }
        })

        categoryViewModel.subCategoryState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<SubCategory>> -> {
                    dataState.data.let { subCategoryModel ->
                        dialog.closeLoadingDialog()
                        subCategoryAdapter.submitList(subCategoryModel.results)
                    }
                }
            }
        })

        categoryViewModel.productState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BasePaginatedModel<PaginatedModel>> -> {
                    dataState.data.let { productModel ->
                        if (productModel.success) {
                            Log.d("CategoryFragment", productModel.message)
                        }
                    }
                }
            }
        })

    }


    override fun onItemClick(category: Category) {
        categoryViewModel.onCategoryClicked(category)
    }

    override fun onItemClick(subCategory: SubCategory?) {
        categoryViewModel.onSubCategoryClicked(subCategory, 1, 5)
    }

    override fun onViewAllClicked(subCategory: SubCategory?) {
        categoryViewModel.onViewAllClicked(subCategory)
    }

}