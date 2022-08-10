package com.example.locus.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locus.data.Item
import com.example.locus.data.Resource
import com.example.locus.data.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) :
    ViewModel() {

    private val _getItemListResponse: MutableLiveData<Resource<ArrayList<Item>>> =
        MutableLiveData()

    val getItemListResponse: LiveData<Resource<ArrayList<Item>>>
        get() = _getItemListResponse


    fun getItemList() = viewModelScope.launch {
        _getItemListResponse.value = Resource.Loading
        _getItemListResponse.value = repository.getQuestionList()
    }

}