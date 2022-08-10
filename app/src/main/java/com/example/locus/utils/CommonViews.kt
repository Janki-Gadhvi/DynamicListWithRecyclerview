package com.example.locus.utils

import android.content.Context
import android.widget.Toast
import com.example.mentalhealthpatient.ui.view.commonviews.classes.LoadingUtils

object CommonViews {

    @JvmStatic
    fun showError(context: Context, error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun startLoading(context: Context, isCancelable: Boolean = true) {
        LoadingUtils.showDialog(context, isCancelable)
    }

    @JvmStatic
    fun hideLoading() {
        LoadingUtils.hideDialog()
    }
}