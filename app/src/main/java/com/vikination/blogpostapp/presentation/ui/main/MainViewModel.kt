package com.vikination.blogpostapp.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.repos.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainRepository: MainRepository) :ViewModel(){

    fun getAllPost() :LiveData<List<Post>> = mainRepository.getAllPost()

    fun deletePost(body: RequestPostBody, id :Int) = mainRepository.deletePost(body, id)

    fun createPost(body: RequestPostBody) = mainRepository.createPost(body)

}