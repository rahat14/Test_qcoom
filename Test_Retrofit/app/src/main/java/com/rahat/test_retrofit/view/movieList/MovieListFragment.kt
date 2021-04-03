package com.rahat.test_retrofit.view.movieList

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahat.test_retrofit.R
import com.rahat.test_retrofit.data.network.MovieModel
import com.rahat.test_retrofit.databinding.FragmentBlogListBinding
import com.rahat.test_retrofit.network.ResultData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_blog_list), MovieListAdapter.Interaction {
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentBlogListBinding
    private lateinit var manager: LinearLayoutManager
    private lateinit var movieAdapter: MovieListAdapter
    var isScrolling = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrollOutItems: Int = 0
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view binding
        binding = FragmentBlogListBinding.bind(view)
        manager = LinearLayoutManager(requireContext())
        movieAdapter = MovieListAdapter(this@MovieListFragment)



        binding.movieList.apply {
            adapter = movieAdapter
            layoutManager = manager
        }


        initScrollListener()

        // listen for the the event
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movieEvent.collect { event ->
                when (event) {
                    is MovieListViewModel.MovieListEvent.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), event.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // setUPObserver
        setUpObserver()
    }

    private fun setUpObserver() {
        // initial loading 1st page
        val liveData = viewModel.loadDemoDataList(1)

        // and observing for future change
        liveData.observe(viewLifecycleOwner,
            { resultData ->
                when (resultData) {
                    is ResultData.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultData.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val MovieModelList = resultData.data
                        if (MovieModelList != null) {
                            viewModel.currentPage.value = MovieModelList.page
                            viewModel.totalPage.value = MovieModelList.total_pages

                            val mainList: ArrayList<MovieModel> = ArrayList(movieAdapter.getList())
                            //add the receive list
                            mainList.addAll(MovieModelList.results)
                            movieAdapter.submitList(mainList)
                        }
                    }
                    is ResultData.Failed -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "${resultData.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ResultData.Exception -> {
                        Toast.makeText(
                            requireContext(),
                            "${resultData.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        )
    }

    override fun onItemSelected(position: Int, item: MovieModel) {

    }

    // for paging
    // can be replaced by paging library
    private fun initScrollListener() {
        binding.movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { // scroll down
                    currentItems = manager.childCount
                    totalItems = manager.itemCount
                    scrollOutItems = manager.findFirstVisibleItemPosition()
                    if (isScrolling && currentItems + scrollOutItems == totalItems) {
                        isScrolling = false
                        viewModel.decideToLoadMore()
                    }
                }
            }
        })


    }
}