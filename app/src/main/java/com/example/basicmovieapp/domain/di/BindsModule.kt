package com.example.basicmovieapp.domain.di

import com.example.basicmovieapp.domain.repositories.MovieRepository
import com.example.basicmovieapp.domain.repositories.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindsMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
