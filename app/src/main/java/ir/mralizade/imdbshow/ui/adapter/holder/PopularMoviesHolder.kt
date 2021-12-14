package ir.mralizade.imdbshow.ui.adapter.holder

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.mralizade.imdbshow.databinding.PopularMoviesRecyclerviewRowBinding

class PopularMoviesHolder(
    binding: PopularMoviesRecyclerviewRowBinding
): RecyclerView.ViewHolder(binding.root) {

    val poster: ImageView = binding.moviePoster
    val dataBackground: LinearLayout = binding.dataBackground
    val rate: TextView = binding.movieRate
    val year: TextView = binding.movieYear
    val title: TextView = binding.movieTitle
    val crew: TextView = binding.movieCrew

}