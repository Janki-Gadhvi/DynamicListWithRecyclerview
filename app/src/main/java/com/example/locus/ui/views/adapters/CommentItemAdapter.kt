package com.example.locus.ui.views.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locus.data.Item
import com.example.locus.databinding.RvCommentBinding
import com.example.locus.databinding.RvPhotoLayoutBinding
import com.example.mentalhealthpatient.ui.view.appointment.interfaces.ItemClickListner

internal class CommentItemAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        context: Context,
        list: ArrayList<Item>,
        position: Int,
        binding: RvCommentBinding,
        itemClickListner: ItemClickListner,
        itemListAdapter: ItemListAdapter
    ) {
        val item = list[position]
        with(item) {
            binding.tvTitle.text = this.title



            binding.swComment.setOnCheckedChangeListener(null)

            if (this.commentObject?.isSwitchOn == true) {
                binding.etComment.visibility = View.VISIBLE
                binding.swComment.isChecked = true
            } else {
                binding.swComment.isChecked = false
                binding.etComment.visibility = View.GONE
                binding.etComment.setText("")
                this.commentObject?.commentText = ""
            }

            binding.swComment.setOnCheckedChangeListener { compoundButton, b ->
                this.commentObject?.isSwitchOn = b
                itemListAdapter.notifyDataSetChanged()
            }

            //persist the data if stored already while scrolling
            this.commentObject?.commentText?.let {
                binding.etComment.setText(it)
            } ?: kotlin.run {
                binding.etComment.setText("")
                this.commentObject?.commentText = ""
            }


            binding.etComment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    itemClickListner.onTextChange(position, this@with, s.toString())
                }
            })
        }
    }
}