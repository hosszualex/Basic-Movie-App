package com.example.basicmovieapp

import com.example.basicmovieapp.data.DataClient
import com.example.basicmovieapp.domain.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockOfflineDataClient : DataClient {
    private val gson = Gson()

    override fun getMovies(): Flow<List<Movie>> =
        flow {
            val jsonString = MockOfflineDataClient::class.java.getResource("/movies.json")?.readText()
            val response: List<Movie> =
                gson.fromJson(jsonString, object : TypeToken<List<Movie?>?>() {}.type)
            emit(response)
        }

    override fun getStaffPickedMovies(): Flow<List<Movie>> =
        flow {
            val jsonString = MockOfflineDataClient::class.java.getResource("/staff_picks.json")?.readText()
            val response: List<Movie> =
                gson.fromJson(jsonString, object : TypeToken<List<Movie?>?>() {}.type)
            emit(response)
        }
}
