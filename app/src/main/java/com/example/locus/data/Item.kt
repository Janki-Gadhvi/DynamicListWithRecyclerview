package com.example.locus.data

data class Item(
    val id: String,
    val type: String,
    val title: String,
    val dataMap: DataMap?,
    val photoObject: PhotoObject? = null,
    val singleChoiceObject: SingleChoiceObject? = null,
    val commentObject: CommentObject? = null
) {

    fun getString(): String {
        return "id is $id type is $type title is $title datamap is  $dataMap"
    }

}
