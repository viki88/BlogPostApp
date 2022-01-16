package com.vikination.blogpostapp.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    private const val GENERAL_FORMAT_TIME = "dd MMMM yyyy - hh:mm"

    fun getStringFromHtml(textView: TextView, htmlString :String) {
        textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        }else Html.fromHtml(htmlString)
    }

    fun isNetworkConnected(context :Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun parseTimeToDate(dateTime :String) :Date?{
        // 2022-01-05T01:49:03.624Z
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.parse(dateTime)
    }

    private fun getDateTimeString(date: Date?, format :String) :String{
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun getDateString(rawFormat :String) :String{
        return getDateTimeString(parseTimeToDate(rawFormat), GENERAL_FORMAT_TIME)
    }
}