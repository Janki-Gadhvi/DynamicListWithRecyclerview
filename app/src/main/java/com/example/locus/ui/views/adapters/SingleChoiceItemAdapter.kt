package com.example.locus.ui.views.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locus.data.Item
import com.example.locus.databinding.RvPhotoLayoutBinding
import com.example.locus.databinding.RvSingleChoiceBinding
import com.example.mentalhealthpatient.ui.view.appointment.interfaces.ItemClickListner

internal class SingleChoiceItemAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        context: Context,
        list: ArrayList<Item>,
        position: Int,
        binding: RvSingleChoiceBinding,
        itemClickListner: ItemClickListner
    ) {
        val item = list[position]
        with(item) {


            binding.tvTitle.text = this.title

            binding.radiogroup.removeAllViews()

            this.dataMap?.options?.let {
                for (index in it.indices) {
                    val radioButton1 = RadioButton(context)
                    radioButton1.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    radioButton1.text = it[index]
                    radioButton1.id = index
                    binding.radiogroup.addView(radioButton1)
                }

                binding.radiogroup.setOnCheckedChangeListener(null)

                //persist the data if stored already while scrolling
                if (this.singleChoiceObject?.isOptionSelected == true) {
                    binding.radiogroup.check(
                        binding.radiogroup.getChildAt(this.singleChoiceObject.selectedOptionPosition)
                            .id
                    );
                }


                binding.radiogroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                    //    val selectedid = binding.radiogroup.checkedRadioButtonId
                    val findSelectedItem = it[i]
                    itemClickListner.onOptionClicked(position, this, findSelectedItem, i)
                })

            }


        }
    }
}