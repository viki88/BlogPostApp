package com.vikination.blogpostapp.data.service

import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.models.Post
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/posts")
    fun getAllPosts() : Call<List<Post>>

    @HTTP(method = "DELETE", path = "/posts/{id}", hasBody = true)
    fun deletePost(@Body body: RequestPostBody, @Path("id") id :Int) : Call<Post>

    @POST("/posts")
    fun createPost(@Body requestPostBody: RequestPostBody) :Call<Post>

}