package com.example.basicmovieapp.domain.util

import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import java.lang.reflect.Type

class GsonUtil(
    private val gson: Gson
) {
    fun toJson(objectType: Any?): String {
        return gson.toJson(objectType) ?: ""
    }

    fun fromJsonObject(json: String?, classType: Type): Any? {
        try {
            if (json.isNullOrEmpty()) {
                return null
            }
            return gson.fromJson(json, classType)
        } catch (e: MalformedJsonException) {
            return null
        }
    }
// TODO using the TypeToken for arrays
//    fun fromJsonArrayObject(json: String): ArrayList<Any> {
//
//    }
}
