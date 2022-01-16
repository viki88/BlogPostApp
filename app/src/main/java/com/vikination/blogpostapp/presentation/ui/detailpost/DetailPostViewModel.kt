package com.vikination.blogpostapp.presentation.ui.detailpost

import androidx.lifecycle.ViewModel
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.repos.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPostViewModel @Inject constructor(var mainRepository: MainRepository) :ViewModel(){

    fun getPost(id :Int) = mainRepository.getPost(id)

    fun updatePost(body: RequestPostBody, id: Int) = mainRepository.updatePost(body, id)
}