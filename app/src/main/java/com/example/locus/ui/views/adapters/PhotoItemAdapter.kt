package com.example.locus.ui.views.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locus.data.Item
import com.example.locus.databinding.RvPhotoLayoutBinding
import com.example.mentalhealthpatient.ui.view.appointment.interfaces.ItemClickListner

internal class PhotoItemAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        context: Context,
        list: ArrayList<Item>,
        position: Int,
        binding: RvPhotoLayoutBinding,
        itemClickListner: ItemClickListner
    ) {

        val item = list[position]
        with(item) {



            Glide.with(context.applicationContext).load(this.photoObject?.photoUrl)
                .into(binding.ivPhoto)


            if (this.photoObject?.photoUrl.isNullOrBlank()) {
                binding.ivRemove.visibility = View.GONE
            } else {
                binding.ivRemove.visibility = View.VISIBLE
            }


            binding.tvTitle.text = this.title

            binding.ivPhoto.setOnClickListener {
                itemClickListner.onPhotoClicked(position, this)
            }

            binding.ivRemove.setOnClickListener {
                itemClickListner.onRemovePhotoClicked(position, this)
            }
        }

    }
}