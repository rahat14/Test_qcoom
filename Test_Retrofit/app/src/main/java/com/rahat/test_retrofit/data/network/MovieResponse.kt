package com.rahat.test_retrofit.data.network

data class MovieResponse(
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)