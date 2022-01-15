package com.vikination.blogpostapp.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vikination.blogpostapp.data.models.DeletePostBody
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.repos.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainRepository: MainRepository) :ViewModel(){

    fun getAllPost() :LiveData<List<Post>> = mainRepository.getAllPost()

    fun deletePost(body: DeletePostBody, id :Int) = mainRepository.deletePost(body, id)

}