package com.vikination.blogpostapp.data.repos

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.DeletePostBody
import com.vikination.blogpostapp.data.models.Post

interface MainRepository{
    fun getAllPost() : LiveData<List<Post>>
    fun deletePost(body: DeletePostBody, id :Int) :LiveData<Post>
}