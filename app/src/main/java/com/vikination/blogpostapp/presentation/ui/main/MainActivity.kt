package com.vikination.blogpostapp.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vikination.blogpostapp.databinding.ActivityMainBinding
import com.vikination.blogpostapp.presentation.ui.adapter.ListPostAdapter
import com.vikination.blogpostapp.presentation.ui.newpost.CreatePostDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private lateinit var createPostDialog :CreatePostDialogFragment
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter :ListPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        createPostDialog = CreatePostDialogFragment()

        binding.btnAddpost.setOnClickListener { showCreatePostDialog() }

        binding.swipeLayout.setOnRefreshListener { loadAllPost() }
    }

    override fun onResume() {
        super.onResume()
        loadAllPost()
    }

    private fun loadAllPost(){
        showLoadingBar(true)
        viewModel.getAllPost().observe(this){
            Log.i("TAG", "onCreate: ${Gson().toJson(it)}")
            adapter.submitList(it)
            showLoadingBar(false)
        }
    }

    private fun setupList(){
        adapter = ListPostAdapter()
        binding.rvListpost.adapter = adapter
        binding.rvListpost.layoutManager = LinearLayoutManager(this)
    }

    private fun showCreatePostDialog(){
        createPostDialog.show(supportFragmentManager, "Create Post")
    }

    private fun showLoadingBar(show :Boolean){
        binding.swipeLayout.isRefreshing = show
    }
}