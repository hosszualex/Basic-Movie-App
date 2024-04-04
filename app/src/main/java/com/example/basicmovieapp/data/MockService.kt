package com.example.basicmovieapp.data

import android.content.Context
import com.example.basicmovieapp.data.models.MovieResponse
import com.example.basicmovieapp.domain.util.GsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MockService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getMovies(): Flow<List<MovieResponse>> = flow {
        val jsonString = context.assets.open("movies.json").bufferedReader().use { it.readText() }
        val response: List<MovieResponse> = Gson().fromJson(jsonString, object : TypeToken<List<MovieResponse?>?>() {}.type)
        emit(response)
    }

    fun getStaffPicks(): Flow<List<MovieResponse>> = flow {
        val jsonString = context.assets.open("staff_picks.json").bufferedReader().use { it.readText() }
        val response: List<MovieResponse> = Gson().fromJson(jsonString, object : TypeToken<List<MovieResponse?>?>() {}.type)
        emit(response)
    }
}