package ir.mralizade.imdbshow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.databinding.PopularMovieListFragmentBinding
import ir.mralizade.imdbshow.ui.adapter.PopularMoviesRecyclerViewAdapter
import ir.mralizade.imdbshow.ui.adapter.clicklistener.OnPopularMoviesClickListener
import ir.mralizade.imdbshow.utils.*
import ir.mralizade.imdbshow.viewmodel.PopularMoviesViewModel

@AndroidEntryPoint
class PopularMoviesListFragment: Fragment(), OnPopularMoviesClickListener {

    private var _binding: PopularMovieListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val recyclerViewAdapter by lazy { PopularMoviesRecyclerViewAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getPopularMovies(0)
        _binding = PopularMovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            refreshPopularMovie.setOnRefreshListener {
                refreshPage()
                activeLoadingMode()
                getPopularMovies(0)
            }
        }

        initObservables()
    }

    private fun refreshPage() {
        viewModel.refreshPageData()
        recyclerViewAdapter.refreshList()
    }

    private fun getPopularMovies(startPoint: Int) {
        viewModel.getAllVideo(startPoint)
    }

    private fun openVideo(movieId: String) {
        log(MOVIES_LIST_FRAGMENT_TAG, "movie Id $movieId was Clicked")
        val bundle = bundleOf(MOVIE_ID to movieId)
        transactionFragment(SingleMovieFragment(), bundle)

    }

    private fun initRecyclerView(dataList: MutableList<PopularMovieEntity>) {
        recyclerViewAdapter.setDate(dataList)
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

    private fun initObservables() {
        viewModel.popularMoviesResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AppState.Success -> {
                    inActiveLoadingMode()
                    initRecyclerView(response.data!!)
                }
                is AppState.Error -> {
                    inActiveLoadingMode()
                    toast(response.message.toString())
                }
                is AppState.Loading -> activeLoadingMode()
                else -> log(MOVIES_LIST_FRAGMENT_TAG, "Exactly How O_o!!!")
            }
        }
    }

}