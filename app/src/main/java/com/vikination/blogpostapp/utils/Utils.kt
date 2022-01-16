package com.vikination.blogpostapp.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat


object Utils {

    fun getStringFromHtml(textView: TextView, htmlString :String) {
        textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
        }else Html.fromHtml(htmlString)
    }

    fun isNetworkConnected(context :Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}