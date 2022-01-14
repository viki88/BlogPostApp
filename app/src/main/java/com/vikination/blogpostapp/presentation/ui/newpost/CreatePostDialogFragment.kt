package com.vikination.blogpostapp.presentation.ui.newpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vikination.blogpostapp.R
import com.vikination.blogpostapp.databinding.LayoutNewPostBinding

class CreatePostDialogFragment :DialogFragment(){

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

        binding.btnPost.setOnClickListener { if (isValidInput()) dismiss() }
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
}