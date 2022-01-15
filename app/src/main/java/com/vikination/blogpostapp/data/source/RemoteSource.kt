package com.vikination.blogpostapp.data.source

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.models.Post

interface RemoteSource {
    fun getAllPosts() :LiveData<List<Post>>
    fun deletePost(body: RequestPostBody, id :Int) :LiveData<Post>
    fun createPost(body: RequestPostBody) :LiveData<Post>
}