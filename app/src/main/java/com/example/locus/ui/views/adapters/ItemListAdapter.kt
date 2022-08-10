package com.example.locus.ui.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locus.data.Item
import com.example.locus.databinding.RvCommentBinding
import com.example.locus.databinding.RvPhotoLayoutBinding
import com.example.locus.databinding.RvSingleChoiceBinding
import com.example.locus.utils.Constants
import com.example.mentalhealthpatient.ui.view.appointment.interfaces.ItemClickListner

class ItemListAdapter(
    private val context: Context,
    val list: ArrayList<Item>,
    val itemClickListner: ItemClickListner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var photoLayoutBinding: RvPhotoLayoutBinding
    private lateinit var singleChoiceBinding: RvSingleChoiceBinding
    private lateinit var commentBinding: RvCommentBinding

    override fun getItemCount(): Int {
        return list.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        val item = list[position] as Item
        return when (item.type) {
            Constants.COMMENT -> {
                VIEW_TYPE_COMMENT
            }

            Constants.PHOTO -> {
                VIEW_TYPE_PHOTO
            }

            else -> {
                VIEW_TYPE_SINGLE_CHOICE
            }
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {

            VIEW_TYPE_PHOTO -> {
                photoLayoutBinding =
                    RvPhotoLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
                return PhotoItemAdapter(
                    photoLayoutBinding.root
                )
            }
            VIEW_TYPE_COMMENT -> {
                commentBinding =
                    RvCommentBinding.inflate(LayoutInflater.from(context), parent, false)
                return CommentItemAdapter(
                    commentBinding.root
                )
            }
            else -> {
                singleChoiceBinding =
                    RvSingleChoiceBinding.inflate(LayoutInflater.from(context), parent, false)
                return SingleChoiceItemAdapter(
                    singleChoiceBinding.root
                )
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    companion object {
        private const val VIEW_TYPE_PHOTO = 1
        private const val VIEW_TYPE_COMMENT = 2
        private const val VIEW_TYPE_SINGLE_CHOICE = 3
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder.setIsRecyclable(false)
        when (holder.itemViewType) {


            VIEW_TYPE_PHOTO -> {
                (holder as PhotoItemAdapter).bind(
                    context,
                    list,
                    position,
                    photoLayoutBinding,
                    itemClickListner
                )
            }
            VIEW_TYPE_COMMENT -> {
                (holder as CommentItemAdapter).bind(
                    context,
                    list,
                    position,
                    commentBinding,
                    itemClickListner,
                    this
                )
            }
            VIEW_TYPE_SINGLE_CHOICE -> {
                (holder as SingleChoiceItemAdapter).bind(
                    context,
                    list,
                    position,
                    singleChoiceBinding,
                    itemClickListner
                )
            }
        }
    }
}