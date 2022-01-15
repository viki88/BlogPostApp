package com.vikination.blogpostapp.domain.implementation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.service.ApiService
import com.vikination.blogpostapp.data.source.RemoteSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@DelicateCoroutinesApi
class RemoteSourceImpl @Inject constructor(var apiService: ApiService) :RemoteSource{

    override fun getAllPosts(): LiveData<List<Post>> {
        val postLiveData = MutableLiveData<List<Post>>()
        apiService.getAllPosts().enqueue(object :Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                postLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}" )
            }

        })
        return postLiveData
    }

}