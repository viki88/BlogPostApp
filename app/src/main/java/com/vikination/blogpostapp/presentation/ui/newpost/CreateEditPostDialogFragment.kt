package com.vikination.blogpostapp.presentation.ui.newpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vikination.blogpostapp.R
import com.vikination.blogpostapp.data.models.Post
import com.vikination.blogpostapp.data.models.RequestPostBody
import com.vikination.blogpostapp.databinding.LayoutNewPostBinding
import retrofit2.http.POST

class CreateEditPostDialogFragment(var onFinishCreatePostListener: OnFinishCreatePostListener, var isEdit :Boolean = false) :DialogFragment(){

    private lateinit var binding : LayoutNewPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postData = arguments?.getParcelable(POST_DATA) as Post?

        binding.btnPost.text = if (isEdit) "Update" else "Create"
        binding.inputTitlePost.setText(if (isEdit) postData?.title?:"" else "")
        binding.inputContentPost.setText(if (isEdit) postData?.content?:"" else "")

        binding.btnPost.setOnClickListener {
            if (isValidInput()) {
                val requestPostBody =
                    RequestPostBody(
                        binding.inputTitlePost.text.toString(),
                        binding.inputContentPost.text.toString()
                    )
                if (isEdit) onFinishCreatePostListener.onFinishEditPost(requestPostBody, postData?.id?:0)
                else onFinishCreatePostListener.onFinishCreatePost(requestPostBody)
                dismiss()
            }
        }
    }

    private fun isValidInput() :Boolean{
        return when{
            binding.inputTitlePost.text.isEmpty() -> {
                binding.inputTitlePost.error = resources.getString(R.string.error_empty_input_title_message)
                binding.inputTitlePost.requestFocus()
                false
            }

            binding.inputContentPost.text.isEmpty() -> {
                binding.inputContentPost.error = resources.getString(R.string.error_empty_input_content_message)
                binding.inputContentPost.requestFocus()
                false
            }

            else -> true
        }
    }

    interface OnFinishCreatePostListener{
        fun onFinishCreatePost(body :RequestPostBody)
        fun onFinishEditPost(body: RequestPostBody, id:Int)
    }

    companion object{
        const val POST_DATA = "post_data"
    }
}