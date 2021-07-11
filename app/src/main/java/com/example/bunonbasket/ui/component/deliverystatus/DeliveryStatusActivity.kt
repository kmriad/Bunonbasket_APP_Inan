package com.example.bunonbasket.ui.component.deliverystatus

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.deliverystatus.DeliveryStatusModel
import com.example.bunonbasket.databinding.ActivityDeliveryStatusBinding
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DeliveryStatusActivity : AppCompatActivity() {

    lateinit var binding: ActivityDeliveryStatusBinding

    private val viewModel: DeliveryStatusViewModel by viewModels()

    lateinit var deliveryAdapter: DeliveryStatusAdapter

    var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_status)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        id = intent.getIntExtra("cartId", -1)

        binding.apply {

            deliveryAdapter = DeliveryStatusAdapter()
            binding.itemsRv.apply {
                layoutManager = LinearLayoutManager(
                    this@DeliveryStatusActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = deliveryAdapter

            }

            subscribeObservers()
        }
    }

    private fun subscribeObservers() {

        viewModel.token.observe(this, { dataState ->
            when (dataState) {
                is String -> {
                    if (dataState.isNotEmpty()) {
                        if (id > -1) {
                            viewModel.fetchRemoteEvents(
                                DeliveryStatusEvent.FetchDeliveryStatus(
                                    cartId = id
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Please Log in",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

        viewModel.deliverStatusState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<DeliveryStatusModel>> -> {
                    dataState.data.let { deliveryStatus ->
                        if (deliveryStatus.results.delivery_status == "pending") {
                            binding.stepView.statusView.run {
                                currentCount = 0
                                circleFillColorCurrent = Color.GREEN
                            }
                        } else if (deliveryStatus.results.delivery_status == "processing") {
                            binding.stepView.statusView.run {
                                currentCount = 1
                            }
                        } else {
                            binding.stepView.statusView.run {
                                currentCount = 2
                            }
                        }
                        deliveryAdapter.submitList(deliveryStatus.results.detials)
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val parentIntent = NavUtils.getParentActivityIntent(this)
                parentIntent!!.flags =
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(parentIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}