package com.rahat.test_retrofit.repository


import com.rahat.test_retrofit.data.network.MovieResponse
import com.rahat.test_retrofit.network.ResultData
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataRepository: NetworkRepository
) {
    suspend fun getMovieList(page: Int): ResultData<MovieResponse> {

        val movieResponsesResponse = dataRepository.getRepositoriesList(page)
        // check for network  error
        return if (movieResponsesResponse.code() == 200) { // as this api only return 200 {
            ResultData.Success(movieResponsesResponse.body())
        } else {
            ResultData.Failed(
                movieResponsesResponse.code().toString(),
                "Network Error With Wrong Code -> ${movieResponsesResponse.code()}"
            )

        }

    }
}

