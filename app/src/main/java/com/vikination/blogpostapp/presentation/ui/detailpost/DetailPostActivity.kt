package com.vikination.blogpostapp.presentation.ui.detailpost

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.vikination.blogpostapp.R
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.databinding.ActivityDetailPostBinding
import com.vikination.blogpostapp.presentation.ui.newpost.CreateEditPostDialogFragment
import com.vikination.blogpostapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDetailPostBinding
    private val viewModel by viewModels<DetailPostViewModel>()
    private var post :Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val idPost = intent.getIntExtra(ID_POST, 0)
        if (isInternetConnectionAvailable(this))getPost(idPost)
    }

    private fun setupToolbar(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Loading Post..."
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_delete -> {
                showDeleteDialog(post)
                true
            }
            R.id.menu_edit -> {
                showEditPostDialog(post)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun getPost(id :Int){
        showLoadingBar(true)
        viewModel.getPost(id).observe(this){
            Log.i("TAG", "getPost: ${Gson().toJson(it)}")
            post = it
            supportActionBar?.title = it.title
            binding.titleDetail.text = it.title
            Utils.getStringFromHtml(binding.contentDetail, it.content)
            showLoadingBar(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDeleteDialog(post: Post?){
        post?.let {
            AlertDialog.Builder(this)
                .setTitle("Delete Post")
                .setMessage("Delete this post?")
                .setPositiveButton("Delete"){ dialog,_ ->
                    dialog.dismiss()
                    deletePost(it)
                }
                .setNegativeButton("Cancel"){ dialog, _ -> dialog.dismiss()}
                .create().show()
        }
    }

    private fun deletePost(post: Post){
        intent.putExtra(ID_KEY, post.id)
        intent.putExtra(POST_KEY, post)
        setResult(DELETE_RESULT, intent)
        finish()
    }

    private fun isInternetConnectionAvailable(context: Context) :Boolean{
        val isAvailable = Utils.isNetworkConnected(context)
        if (!isAvailable) Toast.makeText(this, "Problem with your connection, Please Try Again", Toast.LENGTH_SHORT).show()
        return isAvailable
    }

    private fun showLoadingBar(show :Boolean){
        binding.loadingView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showEditPostDialog(post: Post?){
        val bundle = Bundle()
        bundle.putParcelable(CreateEditPostDialogFragment.POST_DATA, post)
        val createEditPostDialog = CreateEditPostDialogFragment(object : CreateEditPostDialogFragment.OnFinishCreatePostListener{
            override fun onFinishCreatePost(body: RequestPostBody) {/* Not Implemented */ }

            override fun onFinishEditPost(body: RequestPostBody, id: Int) { editPost(body, id) }

        }, true)
        createEditPostDialog.arguments = bundle
        createEditPostDialog.show(supportFragmentManager, "Edit Post")
    }

    private fun editPost(requestPostBody: RequestPostBody, id: Int){
        if (isInternetConnectionAvailable(this)){
            showLoadingBar(true)
            viewModel.updatePost(requestPostBody, id).observe(this){
                Toast.makeText(this@DetailPostActivity, "Update Success", Toast.LENGTH_SHORT).show()
                getPost(id)
            }
        }
    }

    companion object{
        const val ID_POST = "id_post"
        const val DELETE_RESULT = 1001
        const val POST_KEY = "post_key"
        const val ID_KEY = "id_key"
    }
}