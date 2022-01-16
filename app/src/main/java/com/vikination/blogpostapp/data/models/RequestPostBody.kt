package com.vikination.blogpostapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestPostBody(var title :String, var content :String) :Parcelable