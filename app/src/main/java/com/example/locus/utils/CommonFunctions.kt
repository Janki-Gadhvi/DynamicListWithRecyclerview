package com.example.locus.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.example.locus.R
import java.util.ArrayList

object CommonFunctions {

    @JvmStatic
    fun checkPermissions(
        context: Context,
        launcherPermision: ActivityResultLauncher<Array<String>>,
        launcherResultLauncher: ActivityResultLauncher<Intent>,
    ) {
        var permission = true
        val listPermissionsNeeded = ArrayList<String>()

        val storagePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }


        if (listPermissionsNeeded.isNotEmpty()) {
            launcherPermision.launch(
                listPermissionsNeeded.toArray(Array(listPermissionsNeeded.size) { "it = $it" })
            )
            permission = false
        }

        if (permission)
            goToNext(
                context,
                launcherResultLauncher,
            )
    }


    fun handleOnPermissionResult(
        permissions: Map<String, Boolean>,
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        var value = true
        permissions.entries.forEach {
            Log.e("DEBUG", "${it.key} = ${it.value}")

            if (!it.value) {
                value = false
                return@forEach
            }
        }

        if (value) {
            goToNext(
                context,
                launcher,
            )
        } else {
            CommonViews.showError(context, context.getString(R.string.permission_denied))
        }
    }

    fun goToNext(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        openGallary(context, launcher)
    }

    fun openGallary(
        context: Context,
        launcher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    fun whenImageIsPicked(context: Context, data: Intent): ArrayList<String?> {
        val imagesEncodedList = ArrayList<String?>()
        // Get the Image from data
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        if (data.data != null) {
            val mImageUri = data.data

            // Get the cursor
            val cursor: Cursor = context.getContentResolver().query(
                mImageUri!!,
                filePathColumn, null, null, null
            )!!
            // Move to first row
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            imagesEncodedList.add(getPath(context, mImageUri))
            cursor.close()
        } else {
            if (data.clipData != null) {
                val mClipData = data.clipData
                val mArrayUri = java.util.ArrayList<Uri>()
                for (i in 0 until mClipData!!.itemCount) {
                    val item = mClipData!!.getItemAt(i)
                    val uri = item.uri
                    mArrayUri.add(uri)
                    // Get the cursor
                    val cursor: Cursor =
                        context.contentResolver.query(uri, filePathColumn, null, null, null)!!
                    // Move to first row
                    cursor.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    imagesEncodedList.add(getPath(context, uri))
                    cursor.close()
                }
                Log.d("LOG_TAG", "Selected Images" + mArrayUri.size)
                Log.d("LOG_TAG", "Selected Images$imagesEncodedList")
            }
        }

        return imagesEncodedList
    }

    fun getPath(context: Context, uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

}