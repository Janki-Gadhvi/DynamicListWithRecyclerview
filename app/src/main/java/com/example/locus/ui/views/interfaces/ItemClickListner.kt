package com.example.mentalhealthpatient.ui.view.appointment.interfaces

import com.example.locus.data.Item


interface ItemClickListner {

    fun onPhotoClicked(
        pos: Int,
        item: Item
    )

    fun onRemovePhotoClicked(
        pos: Int,
        item: Item
    )

    fun onOptionClicked(
        pos: Int,
        item: Item,
        selectedOption: String,
        selectedOptionPosition : Int
    )

    fun onTextChange(
        pos: Int,
        item: Item,
        text: String
    )

}