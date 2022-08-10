package com.example.locus.data.repositories

import android.content.Context
import com.example.locus.R
import com.example.locus.data.*
import com.example.locus.utils.LoadJson
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class ItemRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loadJson: LoadJson
) {

    @Inject
    @Named("filename")
    lateinit var fileName: String

    suspend fun getQuestionList(): Resource<ArrayList<Item>> =
        withContext(Dispatchers.IO) {
            val list = ArrayList<Item>()

            try {
                val convertedObject: String? = loadJson.loadJSONFromAsset(fileName)
                convertedObject?.let {
                    val jarray = JSONArray(it)

                    for (i in 0 until jarray.length()) {
                        val jb = jarray[i] as JSONObject
                        val type = jb.getString("type")
                        val id = jb.getString("id")
                        val title = jb.getString("title")
                        val dataMap = jb.getString("dataMap")

                        val dataMapModel = Gson().fromJson(dataMap, DataMap::class.java)
                        list.add(
                            Item(
                                id,
                                type,
                                title,
                                dataMapModel,
                                PhotoObject(),
                                SingleChoiceObject(),
                                CommentObject()
                            )
                        )
                    }
                } ?: kotlin.run {
                    Resource.Failure(500, context.getString(R.string.file_does_not_exist))
                }

            } catch (e: IOException) {
                e.printStackTrace()
                Resource.Failure(500, context.getString(R.string.file_does_not_exist))
            } catch (e: JSONException) {
                e.printStackTrace()
                Resource.Failure(500, context.getString(R.string.file_does_not_exist))
            }

            Resource.Success(list)
        }
}

