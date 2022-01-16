package com.vikination.blogpostapp.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.databinding.ActivityMainBinding
import com.vikination.blogpostapp.presentation.ui.adapter.ListPostAdapter
import com.vikination.blogpostapp.presentation.ui.detailpost.DetailPostActivity
import com.vikination.blogpostapp.presentation.ui.listener.OnClickMenuListListener
import com.vikination.blogpostapp.presentation.ui.newpost.CreateEditPostDialogFragment
import com.vikination.blogpostapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    OnClickMenuListListener,
    CreateEditPostDialogFragment.OnFinishCreatePostListener {

    private lateinit var binding :ActivityMainBinding
    private lateinit var createEditPostDialog :CreateEditPostDialogFragment
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter :ListPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        createEditPostDialog = CreateEditPostDialogFragment(this)

        binding.btnAddpost.setOnClickListener { showCreatePostDialog() }

        binding.swipeLayout.setOnRefreshListener { loadAllPost() }

        viewModel.errorResponse().observe(this){
            Toast.makeText(this, "Error Response : $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllPost()
    }

    private fun loadAllPost(){
        if (isInternetConnectionAvailable(this)){
            showLoadingBar(true)
            viewModel.getAllPost().observe(this){
                Log.i("TAG", "onCreate: ${Gson().toJson(it)}")
                adapter.submitList(it)
                showLoadingBar(false)
            }
        }
    }

    private fun deletePost(post: Post){
        if (isInternetConnectionAvailable(this)){
            showLoadingBar(true)
            val body = RequestPostBody(post.title, post.content)
            viewModel.deletePost(body, post.id).observe(this){
                Toast.makeText(this@MainActivity, "Delete \"${it.title}\" Success", Toast.LENGTH_SHORT).show()
                showLoadingBar(false)
                loadAllPost()
            }
        }
    }

    private fun setupList(){
        adapter = ListPostAdapter(this)
        binding.rvListpost.adapter = adapter
        binding.rvListpost.layoutManager = LinearLayoutManager(this)
    }

    private fun showCreatePostDialog(){
        createEditPostDialog = CreateEditPostDialogFragment(this)
        createEditPostDialog.show(supportFragmentManager, "Create Post")
    }

    private fun showEditPostDialog(post: Post){
        val bundle = Bundle()
        bundle.putParcelable(CreateEditPostDialogFragment.POST_DATA, post)
        createEditPostDialog = CreateEditPostDialogFragment(this, true)
        createEditPostDialog.arguments = bundle
        createEditPostDialog.show(supportFragmentManager, "Edit Post")
    }

    private fun showLoadingBar(show :Boolean){
        binding.swipeLayout.isRefreshing = show
    }

    override fun onEditClicked(post: Post) {
        showEditPostDialog(post)
    }

    override fun onDeleteClicked(post: Post) {
        showDeleteDialog(post)
    }

    override fun onClickItemListener(post: Post) {
        val intent = Intent(this, DetailPostActivity::class.java)
        intent.putExtra(DetailPostActivity.ID_POST, post.id)
        detailActivityLauncher.launch(intent)
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

    override fun onFinishEditPost(body: RequestPostBody, id: Int) {
        editPost(body, id)
    }

    private fun createNewPost(requestPostBody: RequestPostBody){
        if (isInternetConnectionAvailable(this)){
            showLoadingBar(true)
            viewModel.createPost(requestPostBody).observe(this){
                Toast.makeText(this@MainActivity, "Create \"${it.title}\" Success", Toast.LENGTH_SHORT).show()
                loadAllPost()
            }
        }
    }

    private fun editPost(requestPostBody: RequestPostBody, id: Int){
        if (isInternetConnectionAvailable(this)){
            showLoadingBar(true)
            viewModel.updatePost(requestPostBody, id).observe(this){
                Toast.makeText(this@MainActivity, "Update \"${it.title}\" Success", Toast.LENGTH_SHORT).show()
                loadAllPost()
            }
        }
    }

    private fun isInternetConnectionAvailable(context: Context) :Boolean{
        val isAvailable = Utils.isNetworkConnected(context)
        if (!isAvailable) Toast.makeText(this, "Problem with your connection, Please Try Again", Toast.LENGTH_SHORT).show()
        return isAvailable
    }

    private val detailActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            DetailPostActivity.DELETE_RESULT -> {
                it.data?.getParcelableExtra<Post>(DetailPostActivity.POST_KEY)?.
                    let { postData -> deletePost(postData)}
            }
        }
    }

}