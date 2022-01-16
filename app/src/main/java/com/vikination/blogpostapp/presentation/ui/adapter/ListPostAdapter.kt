package com.vikination.blogpostapp.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.databinding.ItemLayoutListPostBinding
import com.vikination.blogpostapp.presentation.ui.listener.OnClickMenuListListener
import com.vikination.blogpostapp.utils.Utils

class ListPostAdapter(var onClickMenuListListener: OnClickMenuListListener) : ListAdapter<Post, ListPostAdapter.PostItemViewHolder>(PostDiffUtils()){

    inner class PostItemViewHolder(var binding :ItemLayoutListPostBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(post: Post){
            binding.titlePost.text = post.title
            Utils.getStringFromHtml(binding.contentPost, post.content)

            binding.editLayout.setOnClickListener {
                closeItemMenu()
                onClickMenuListListener.onEditClicked(post)
            }

            binding.deleteLayout.setOnClickListener {
                closeItemMenu()
                onClickMenuListListener.onDeleteClicked(post)
            }

            binding.itemContentLayout.setOnClickListener {
                onClickMenuListListener.onClickItemListener(post)
            }

        }

        private fun closeItemMenu(){
            binding.layoutSwipeReveal.close(true)
        }
    }

    class PostDiffUtils :DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemLayoutListPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        Log.i("TAG", "onBindViewHolder: ${getItem(position).title}")
        holder.bind(getItem(position))
    }

}