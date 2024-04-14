package com.example.basicmovieapp

import com.example.basicmovieapp.domain.repositories.MovieRepository
import com.example.basicmovieapp.domain.repositories.MovieRepositoryImpl
import com.example.basicmovieapp.domain.util.ErrorUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    private lateinit var repository: MovieRepository
    private lateinit var errorRepository: MovieRepository
    private val testScope = CoroutineScope(UnconfinedTestDispatcher())

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(MockOfflineDataClient(), testScope)
        errorRepository = MovieRepositoryImpl(MockErrorOfflineDataClient(), testScope)
    }

    @Test
    fun `Repository Initialization successful test`() =
        runTest {
            val movies = repository.movies.value
            assertTrue(movies.isNotEmpty())
        }

    @Test
    fun `Error, json file format test`() =
        runTest {
            val error = errorRepository.error.value
            assertEquals(ErrorUtil.ERROR_JSON_FILE_FORMAT, error)
        }
}
