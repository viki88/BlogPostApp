package com.vikination.blogpostapp.domain.implementation

import androidx.lifecycle.LiveData
import com.vikination.blogpostapp.data.models.DeletePostBody
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.repos.MainRepository
import com.vikination.blogpostapp.data.source.RemoteSource

class MainRepositoryImpl(var remoteSource: RemoteSource) : MainRepository {

    override fun getAllPost(): LiveData<List<Post>> = remoteSource.getAllPosts()
    override fun deletePost(body: DeletePostBody, id: Int): LiveData<Post> = remoteSource.deletePost(body, id)

}