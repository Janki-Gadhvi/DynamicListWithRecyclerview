package com.example.locus.data

data class SingleChoiceObject(
    var isOptionSelected: Boolean? = false,
    var selectedOptionText: String = "",
    var selectedOptionPosition : Int = -1
)