package com.vikination.blogpostapp.data.source

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.DeletePostBody
import com.vikination.blogpostapp.data.models.Post

interface RemoteSource {
    fun getAllPosts() :LiveData<List<Post>>
    fun deletePost(body: DeletePostBody, id :Int) :LiveData<Post>
}