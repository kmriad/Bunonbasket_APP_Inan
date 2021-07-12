package com.example.bunonbasket.utils.widgets

import android.app.Activity
import android.app.AlertDialog
import com.example.bunonbasket.R

/**
 * Created by inan on 5/5/21
 */
class LoadingDialog internal constructor(myActivity: Activity) {
    private val activity: Activity
    private lateinit var dialog: AlertDialog
    fun showLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))

        builder.setCancelable(false)
        dialog = builder.create()
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun closeLoadingDialog() {
        dialog.dismiss()
    }

    init {
        activity = myActivity
    }
}