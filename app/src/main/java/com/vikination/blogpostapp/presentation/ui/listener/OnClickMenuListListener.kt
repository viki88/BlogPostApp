package com.vikination.blogpostapp.presentation.ui.listener

import com.vikination.blogpostapp.data.models.Post

interface OnClickMenuListListener {
    fun onEditClicked(post :Post)
    fun onDeleteClicked(post: Post)
}