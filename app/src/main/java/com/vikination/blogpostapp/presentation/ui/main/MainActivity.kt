package com.vikination.blogpostapp.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.databinding.ActivityMainBinding
import com.vikination.blogpostapp.presentation.ui.adapter.ListPostAdapter
import com.vikination.blogpostapp.presentation.ui.listener.OnClickMenuListListener
import com.vikination.blogpostapp.presentation.ui.newpost.CreatePostDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    OnClickMenuListListener,
    CreatePostDialogFragment.OnFinishCreatePostListener {

    private lateinit var binding :ActivityMainBinding
    private lateinit var createPostDialog :CreatePostDialogFragment
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter :ListPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        createPostDialog = CreatePostDialogFragment(this)

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

    private fun deletePost(post: Post){
        showLoadingBar(true)
        val body = RequestPostBody(post.title, post.content)
        viewModel.deletePost(body, post.id).observe(this){
            Toast.makeText(this@MainActivity, "Delete \"${it.title}\" Success", Toast.LENGTH_SHORT).show()
            showLoadingBar(false)
            loadAllPost()
        }
    }

    private fun setupList(){
        adapter = ListPostAdapter(this)
        binding.rvListpost.adapter = adapter
        binding.rvListpost.layoutManager = LinearLayoutManager(this)
    }

    private fun showCreatePostDialog(){
        createPostDialog.show(supportFragmentManager, "Create Post")
    }

    private fun showLoadingBar(show :Boolean){
        binding.swipeLayout.isRefreshing = show
    }

    override fun onEditClicked(post: Post) {
        Toast.makeText(this, "edit ${post.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClicked(post: Post) {
        showDeleteDialog(post)
    }

    private fun showDeleteDialog(post: Post){
        AlertDialog.Builder(this)
            .setTitle("Delete Post")
            .setMessage("Delete \"${post.title}\" post?")
            .setPositiveButton("Delete"){ dialog,_ ->
                dialog.dismiss()
                deletePost(post)
            }
            .setNegativeButton("Cancel"){dialog, _ -> dialog.dismiss()}
            .create().show()
    }

    override fun onFinishCreatePost(body: RequestPostBody) {
        createNewPost(body)
    }

    private fun createNewPost(requestPostBody: RequestPostBody){
        showLoadingBar(true)
        viewModel.createPost(requestPostBody).observe(this){
            Toast.makeText(this@MainActivity, "Create \"${it.title}\" Success", Toast.LENGTH_SHORT).show()
            loadAllPost()
        }
    }

}