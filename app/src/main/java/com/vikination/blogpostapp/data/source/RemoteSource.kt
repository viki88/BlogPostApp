package com.vikination.blogpostapp.data.source

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.Post

interface RemoteSource {
    fun getAllPosts() :LiveData<List<Post>>
}