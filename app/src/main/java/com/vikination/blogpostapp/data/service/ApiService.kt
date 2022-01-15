package com.vikination.blogpostapp.data.service

import com.vikination.blogpostapp.data.models.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    fun getAllPosts() : Call<List<Post>>

}