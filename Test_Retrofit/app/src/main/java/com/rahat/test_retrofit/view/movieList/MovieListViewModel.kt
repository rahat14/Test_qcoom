package com.rahat.test_retrofit.view.movieList

import android.util.Log
import androidx.lifecycle.*
import com.rahat.test_retrofit.data.network.MovieResponse
import com.rahat.test_retrofit.network.ResultData
import com.rahat.test_retrofit.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    var totalPage = MutableLiveData<Int>()
    var currentPage = MutableLiveData<Int>()
    private val movieListViewModelEventChannel = Channel<MovieListEvent>()
    val movieEvent = movieListViewModelEventChannel.receiveAsFlow()

    fun loadDemoDataList(page: Int): LiveData<ResultData<MovieResponse>> {

//        viewModelScope.launch {
//            val data = withContext(Dispatchers.Default) {
//                repositoryImpl.getDemoBlogsList(page)
//            }
//            movieList.value = data
//            totalPage.value = data.total_pages
//            currentPage.value = data.page
//        }

       return flow {
            emit(ResultData.Loading())
            try {
                val data   = repositoryImpl.getMovieList(page)
                emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TAG", "loadDemoDataList:${e} ")
                emit(ResultData.Exception(e))
            }
        }.asLiveData(Dispatchers.IO)


    }

    fun decideToLoadMore() {
        if (totalPage.value!! > currentPage.value!!) {
            // increase the page number
            loadDemoDataList(totalPage.value!! + 1)

        } else {
            viewModelScope.launch {
                movieListViewModelEventChannel.send(MovieListEvent.ShowErrorMessage("You Are At The Last Page"))
            }

        }
    }

    sealed class MovieListEvent {
        data class ShowErrorMessage(val msg: String) : MovieListEvent()
    }

}