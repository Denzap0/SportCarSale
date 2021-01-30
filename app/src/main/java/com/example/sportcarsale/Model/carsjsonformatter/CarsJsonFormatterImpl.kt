package com.example.sportcarsale.Model.carsjsonformatter

import android.content.res.AssetManager
import android.content.res.Resources
import android.util.JsonReader
import android.util.Log
import com.example.sportcarsale.R
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.io.StringWriter

class CarsJsonFormatterImpl(private val jsonInputStream: InputStream) : CarsJsonFormatter {
    private val json : String = jsonInputStream.bufferedReader().use { it.readText() }
    private val jsonObject = JSONObject(json)
    override fun getCarBrands(): Single<List<String>> =
        Single.create<List<String>> { emitter ->
            val jList = jsonObject.getJSONObject("list").names()
            if(jList != null) {
                val brandsList = mutableListOf<String>()
                for (i in 0 until jList.length()) {
                    brandsList.add(jList.getString(i))
                }
                emitter.onSuccess(brandsList)
            }else{
                emitter.onError(Throwable("JSON array error"))
            }
        }.subscribeOn(Schedulers.computation())

    override fun getModelsByBrand(brand: String): Single<List<String>> =
        Single.create<List<String>>{ emitter ->
            val jModelsAr = jsonObject.getJSONObject("list").getJSONArray(brand)
            val modelsList = mutableListOf<String>()
            for(i in 0 until jModelsAr.length()){
                modelsList.add(jModelsAr.getString(i))
            }
            emitter.onSuccess(modelsList)
        }.subscribeOn(Schedulers.computation())
}