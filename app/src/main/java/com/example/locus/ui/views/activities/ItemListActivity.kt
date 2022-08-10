package com.example.locus.ui.views.activities

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.locus.data.Item
import com.example.locus.data.Resource
import com.example.locus.databinding.ActivityMainBinding
import com.example.locus.ui.viewmodels.ItemViewModel
import com.example.locus.ui.views.adapters.ItemListAdapter
import com.example.locus.ui.views.dialogs.FullScreenImageDialogue
import com.example.locus.utils.CommonFunctions
import com.example.locus.utils.CommonViews
import com.example.locus.utils.Constants
import com.example.mentalhealthpatient.ui.view.appointment.interfaces.ItemClickListner
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemListActivity : AppCompatActivity(), ItemClickListner {


    private lateinit var binding: ActivityMainBinding
    private val viewmodel by viewModels<ItemViewModel>()
    private val itemList = ArrayList<Item>()
    private lateinit var itemListAdapter: ItemListAdapter

    private var selectedIndex = -1

    private var requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            handleOnPermissionResult(permissions)
        }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleOnActivityResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        starObserver()
        initializeRecyclerview()
        getList()
    }

    private fun starObserver() {
        viewmodel.getItemListResponse.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    CommonViews.startLoading(this, false)
                }
                is Resource.Success -> {
                    CommonViews.hideLoading()
                    Log.d("json_data", it.value.toString())
                    addToList(it.value)
                }
                is Resource.Failure -> {
                    CommonViews.hideLoading()
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun addToList(list: ArrayList<Item>) {
        itemList.clear()
        itemList.addAll(list)
        itemListAdapter.notifyDataSetChanged()
    }

    private fun initializeRecyclerview() {
        itemListAdapter = ItemListAdapter(this, itemList, this)
        itemListAdapter.hasStableIds()
        binding.rvList.adapter = itemListAdapter

    }

    private fun getList() {
        viewmodel.getItemList()
    }


    override fun onPhotoClicked(pos: Int, item: Item) {
        selectedIndex = pos

        //if photo already exist then open full screen else choose from gallary

        item.photoObject?.photoUrl?.let {
            openFullScreenImage(it)
        } ?: kotlin.run {
            CommonFunctions.checkPermissions(
                this,
                requestMultiplePermissions, resultLauncher
            )
        }
    }

    override fun onRemovePhotoClicked(pos: Int, item: Item) {
        selectedIndex = pos
        removePhotoUrl()
    }

    override fun onOptionClicked(
        pos: Int,
        item: Item,
        selectedOption: String,
        selectedOptionPosition: Int
    ) {
        selectedIndex = pos
        itemList[pos].singleChoiceObject?.isOptionSelected = true
        itemList[pos].singleChoiceObject?.selectedOptionText = selectedOption
        itemList[pos].singleChoiceObject?.selectedOptionPosition = selectedOptionPosition

        binding.rvList.post(Runnable { itemListAdapter.notifyDataSetChanged() })

    }

    override fun onTextChange(pos: Int, item: Item, text: String) {
        selectedIndex = pos
        itemList[pos].commentObject?.commentText = text
    }

    private fun handleOnPermissionResult(permissions: Map<String, Boolean>) {
        CommonFunctions.handleOnPermissionResult(
            permissions,
            this,
            resultLauncher
        )
    }

    private fun handleOnActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            Log.d("intenetdata", data.toString())
            setAssetsToTheView(data)
        }
    }

    private fun setAssetsToTheView(data: Intent?) {
        if (data != null) {
            val assetsPath = CommonFunctions.whenImageIsPicked(this, data)
            Log.d("assets_print", assetsPath.toString())

            if (!assetsPath.isNullOrEmpty())
                assetsPath[0]?.let { setImagePreview(it) }
        }
    }

    private fun setImagePreview(uri: String) {
        if (selectedIndex > -1) {
            itemList[selectedIndex].photoObject?.photoUrl = uri
            itemListAdapter.notifyDataSetChanged()
        }
    }

    private fun removePhotoUrl() {
        if (selectedIndex > -1) {
            itemList[selectedIndex].photoObject?.photoUrl = null
            itemListAdapter.notifyDataSetChanged()
        }
    }


    private fun openFullScreenImage(uri: String) {
        FullScreenImageDialogue(this, uri).show()
    }

    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        menuInflater.inflate(com.example.locus.R.menu.custom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == com.example.locus.R.id.submit) {
            // do something here
            printLogs()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun printLogs() {
        for (item in itemList) {
            when (item.type) {

                Constants.COMMENT -> {
                    if (!item.commentObject?.commentText.isNullOrBlank()) {
                        println("Item id : ${item.id} selection : ${item.commentObject?.commentText}")
                    } else {
                        //   println("Item id : ${item.id} and user has not selected anything")
                    }
                }
                Constants.PHOTO -> {
                    if (!item.photoObject?.photoUrl.isNullOrBlank()) {
                        println("Item id : ${item.id} selection : ${item.photoObject?.photoUrl}")
                    } else {
                        //   println("Item id : ${item.id} and user has not selected anything")
                    }
                }
                Constants.SINGLE_CHOICE -> {
                    if (!item.singleChoiceObject?.selectedOptionText.isNullOrBlank()) {
                        println("Item id : ${item.id} selection : ${item.singleChoiceObject?.selectedOptionText}")
                    } else {
                        //   println("Item id : ${item.id} and user has not selected anything")
                    }
                }

            }
        }
    }


}