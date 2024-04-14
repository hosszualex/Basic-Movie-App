package com.example.basicmovieapp.domain.util

import com.google.gson.JsonSyntaxException

object ErrorUtil {
    const val ERROR_JSON_FILE_FORMAT = "The JSON file(s) are in the wrong format."
    const val ERROR_UNKNOWN = "An unknown error has occured."

    fun getParsedError(exception: Throwable): String {
        return when (exception) {
            is JsonSyntaxException -> ERROR_JSON_FILE_FORMAT
            else -> ERROR_UNKNOWN
        }
    }
}
