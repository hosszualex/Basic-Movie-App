package com.example.basicmovieapp.domain.di

import android.content.Context
import com.example.basicmovieapp.data.DataClient
import com.example.basicmovieapp.data.OfflineDataClient
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun providesMockService(
        @ApplicationContext context: Context,
    ): DataClient = OfflineDataClient(context)

    @Provides
    fun providesCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

}
