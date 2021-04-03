package com.rahat.test_retrofit.network

import com.rahat.test_retrofit.data.network.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/favorites")
    suspend fun getMovieList(
        @Query("page") page: Int
    ): Response<MovieResponse>
}