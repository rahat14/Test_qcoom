package com.rahat.test_retrofit.repository

import com.rahat.test_retrofit.data.network.MovieResponse
import com.rahat.test_retrofit.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepositoriesList(page : Int): Response<MovieResponse> {
        return apiService.getMovieList(page)
    }
}