package ir.mralizade.imdbshow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.databinding.PopularMovieListFragmentBinding
import ir.mralizade.imdbshow.ui.adapter.PopularMoviesRecyclerViewAdapter
import ir.mralizade.imdbshow.ui.adapter.clicklistener.OnPopularMoviesClickListener
import ir.mralizade.imdbshow.utils.*
import ir.mralizade.imdbshow.viewmodel.PopularMoviesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesListFragment: BaseFragment<PopularMovieListFragmentBinding>(PopularMovieListFragmentBinding::inflate)
    , OnPopularMoviesClickListener {

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val recyclerViewAdapter by lazy { PopularMoviesRecyclerViewAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            refreshPopularMovie.setOnRefreshListener {
                refreshPage()
                activeLoadingMode()
                getPopularMovies(0)
            }
        }
    }

    private fun refreshPage() {
//        viewModel.refreshPageData()
        recyclerViewAdapter.refreshList()
    }

    private fun getPopularMovies(startPoint: Int) {
        context?.let { mContext ->
            viewModel.getPopularMovies(startPoint, mContext.hasInternetConnection())
        }

        flowLife(viewModel.popularMoviesResponseFlow) { response ->
            response.data?.let {
                inActiveLoadingMode()
                initRecyclerView(response.data)
            }

            response.errorMessage?.let {
                inActiveLoadingMode()
                toast(it)
            }
        }
    }

    private fun openVideo(movieId: String) {
        log(MOVIES_LIST_FRAGMENT_TAG, "movie Id $movieId was Clicked")
        val bundle = bundleOf(MOVIE_ID to movieId)
        transactionFragment(SingleMovieFragment(), bundle)

    }

    private fun initRecyclerView(dataList: List<PopularMovieEntity>) {
        recyclerViewAdapter.setDate(dataList.toMutableList())
        binding.moviesRecyclerView.adapter = recyclerViewAdapter
    }

    override fun onItemClickListener(movieId: String) {
        openVideo(movieId)
    }

    private fun activeLoadingMode() {
        binding.moviesRecyclerView.showShimmer()
    }

    private fun inActiveLoadingMode() {
        with(binding){
            moviesRecyclerView.hideShimmer()
            refreshPopularMovie.isRefreshing = false
        }
    }

}