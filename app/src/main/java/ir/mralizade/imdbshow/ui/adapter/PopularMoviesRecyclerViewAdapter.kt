package ir.mralizade.imdbshow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.databinding.PopularMoviesRecyclerviewRowBinding
import ir.mralizade.imdbshow.ui.adapter.clicklistener.OnPopularMoviesClickListener
import ir.mralizade.imdbshow.ui.adapter.holder.PopularMoviesHolder

class PopularMoviesRecyclerViewAdapter(
    private val clickListener: OnPopularMoviesClickListener
) : RecyclerView.Adapter<PopularMoviesHolder>() {

    private val dataList =  mutableListOf<PopularMovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PopularMoviesHolder(
            PopularMoviesRecyclerviewRowBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PopularMoviesHolder, position: Int) {
        val model = dataList[position]

        with(holder) {
            Glide.with(poster.context).load(model.posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.poster)

            title.text = model.title
            rate.text = createRateString(model.imDbRate, model.imDbRateCount)
            year.text = model.year
            crew.text = model.crew

            dataBackground.setOnClickListener {
                crew.visibility = if (crew.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            itemView.setOnClickListener { clickListener.onItemClickListener(model.movieId) }
        }
    }

    private fun createRateString(rate: String, rateCount: String) = "($rateCount) $rate"

    override fun getItemCount() = dataList.size

    fun setDate(data: MutableList<PopularMovieEntity>) {
        dataList.clear()
        dataList.addAll(data)
    }

    fun refreshList() {
        val listSize = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, listSize)
    }
}