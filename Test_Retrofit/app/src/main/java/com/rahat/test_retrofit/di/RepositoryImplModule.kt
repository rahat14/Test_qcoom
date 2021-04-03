package com.rahat.test_retrofit.di

import com.rahat.test_retrofit.repository.NetworkRepository
import com.rahat.test_retrofit.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryImplModule {
    @Provides
    fun providesDataUseCase(networkRepository: NetworkRepository): RepositoryImpl {
        return RepositoryImpl(networkRepository)
    }
}