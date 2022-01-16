package com.vikination.blogpostapp.domain.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.service.ApiService
import com.vikination.blogpostapp.data.source.RemoteSource
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@DelicateCoroutinesApi
class RemoteSourceImpl @Inject constructor(var apiService: ApiService) :RemoteSource{

    private var errorResponseLiveData = MutableLiveData<String>()

    override fun getAllPosts(): LiveData<List<Post>> {
        val postLiveData = MutableLiveData<List<Post>>()
        apiService.getAllPosts().enqueue(object :Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                postLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) =
                errorResponseLiveData.postValue(t.message)

        })
        return postLiveData
    }

    override fun deletePost(body: RequestPostBody, id :Int): LiveData<Post> {
        val deletePostData = MutableLiveData<Post>()
        apiService.deletePost(body, id).enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                deletePostData.postValue(response.body())
            }

            override fun onFailure(call: Call<Post>, t: Throwable) =
                errorResponseLiveData.postValue(t.message)

        })
        return deletePostData
    }

    override fun createPost(body: RequestPostBody): LiveData<Post> {
        val createPostLiveData = MutableLiveData<Post>()
        apiService.createPost(body).enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                createPostLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Post>, t: Throwable) =
                errorResponseLiveData.postValue(t.message)
        })
        return createPostLiveData
    }

    override fun updatePost(body: RequestPostBody, id: Int): LiveData<Post> {
        val updatePostLiveData = MutableLiveData<Post>()
        apiService.updatePost(body, id).enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                updatePostLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Post>, t: Throwable) =
                errorResponseLiveData.postValue(t.message)
        })
        return updatePostLiveData
    }

    override fun getPost(id: Int): LiveData<Post> {
        val getPostLiveData = MutableLiveData<Post>()
        apiService.getPost(id).enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                getPostLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Post>, t: Throwable) =
                errorResponseLiveData.postValue(t.message)
        })
        return getPostLiveData
    }

    override fun errorResponse(): LiveData<String> = errorResponseLiveData


}