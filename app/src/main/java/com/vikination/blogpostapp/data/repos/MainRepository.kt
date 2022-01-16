package com.vikination.blogpostapp.data.repos

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.models.Post

interface MainRepository{
    fun getAllPost() : LiveData<List<Post>>
    fun deletePost(body: RequestPostBody, id :Int) :LiveData<Post>
    fun createPost(body: RequestPostBody) :LiveData<Post>
    fun updatePost(body: RequestPostBody, id: Int) :LiveData<Post>
    fun getPost(id: Int) :LiveData<Post>
}