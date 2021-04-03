package com.rahat.test_retrofit.di

import com.rahat.test_retrofit.network.ApiService
import com.rahat.test_retrofit.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepository(apiService)
    }
}