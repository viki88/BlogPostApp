package com.vikination.blogpostapp.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vikination.blogpostapp.databinding.ActivityMainBinding
import com.vikination.blogpostapp.presentation.ui.newpost.CreatePostDialogFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding
    lateinit var createPostDialog :CreatePostDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createPostDialog = CreatePostDialogFragment()

        binding.btnAddpost.setOnClickListener { showCreatePostDialog() }
    }

    private fun showCreatePostDialog(){
        createPostDialog.show(supportFragmentManager, "Create Post")
    }
}