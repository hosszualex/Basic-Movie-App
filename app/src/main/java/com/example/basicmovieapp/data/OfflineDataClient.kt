package com.example.basicmovieapp.data

import android.content.Context
import com.example.basicmovieapp.domain.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineDataClient
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val gson: Gson,
    ) : DataClient {
        override fun getMovies(): Flow<List<Movie>> =
            flow {
                val jsonString = context.assets.open("movies.json").bufferedReader().use { it.readText() }
                val response: List<Movie> = gson.fromJson(jsonString, object : TypeToken<List<Movie?>?>() {}.type)
                emit(response)
            }

        override fun getStaffPickedMovies(): Flow<List<Movie>> =
            flow {
                val jsonString = context.assets.open("staff_picks.json").bufferedReader().use { it.readText() }
                val response: List<Movie> = gson.fromJson(jsonString, object : TypeToken<List<Movie?>?>() {}.type)
                emit(response)
            }
    }
