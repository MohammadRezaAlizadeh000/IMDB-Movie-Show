package ir.mralizade.imdbshow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.databinding.SingleMovieFragmentBinding
import ir.mralizade.imdbshow.utils.*
import ir.mralizade.imdbshow.viewmodel.SingleMovieViewModel

@AndroidEntryPoint
class SingleMovieFragment: Fragment() {

    private var _binding: SingleMovieFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SingleMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.getMovieData(getMovieIdArgument())

        _binding = SingleMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
    }

    private fun getMovieIdArgument() = arguments?.getString(MOVIE_ID)!!

    private fun setPageData(pageData: SingleMoviesEntity) {
        with(binding) {
            Glide.with(requireContext())
                .load(pageData.posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.mainPoster)

            movieTitle.text = pageData.name
            movieRate.text = createRateString(pageData.imDbRate, pageData.imDbRateCount)
            movieReleaseDate.text = pageData.year
            movieLanguages.text = pageData.languages
            movieCountries.text = pageData.countries
            movieGenres.text = pageData.genres
            movieCrew.text = createMovieCrew(pageData.directors, pageData.writers)
        }
    }

    private fun createRateString(rate: String, rateNumber: String) = "($rateNumber) $rate"

    private fun createMovieCrew(directors: String, writers: String) = "$directors $writers"

    private fun activeLoadingPage() {
        with(binding){
            singlePageShimmer.visibility = View.VISIBLE
            singlePageShimmer.startShimmer()
            singlePageScroll.visibility = View.GONE
        }
    }

    private fun inActiveLoadingPage() {
        with(binding) {
            singlePageShimmer.stopShimmer()
            singlePageShimmer.visibility = View.GONE
            singlePageScroll.visibility = View.VISIBLE
        }
    }

    private fun initObservables() {
        viewModel.singleMovieResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AppState.Success -> {
                    setPageData(response.data!!)
                    inActiveLoadingPage()
                }
                is AppState.Error -> {
                    inActiveLoadingPage()
                    toast(response.message.toString())
                }
                is AppState.Loading -> activeLoadingPage()
                else -> log(SINGLE_MOVIE_FRAGMENT_TAG, "Something goes wrong in states")
            }
        }
    }
}