package com.vikination.blogpostapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var id :Int,
    var title :String,
    var content :String,
    var published_at :String,
    var created_at :String,
    var updated_at :String
) :Parcelable