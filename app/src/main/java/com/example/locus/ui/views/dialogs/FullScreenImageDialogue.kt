package com.example.locus.ui.views.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.locus.databinding.LayoutFullscreenBinding


class FullScreenImageDialogue(context: Context, var uri: String) :
    Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {


    private lateinit var binding: LayoutFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding = LayoutFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        Glide.with(context.applicationContext).load(uri)
            .into(binding.ivPhoto)
    }
}