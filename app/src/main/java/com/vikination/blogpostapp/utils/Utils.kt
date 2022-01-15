package com.vikination.blogpostapp.utils

import android.os.Build
import android.text.Html
import android.widget.TextView

object Utils {

    fun getStringFromHtml(textView: TextView, htmlString :String) {
        textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
        }else Html.fromHtml(htmlString)
    }
}