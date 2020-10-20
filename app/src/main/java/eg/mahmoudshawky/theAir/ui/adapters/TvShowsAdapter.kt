package eg.mahmoudshawky.theAir.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.utils.RemoteConfig
import kotlinx.android.synthetic.main.item_tv_show.view.*
import kotlin.properties.Delegates

class TvShowsAdapter : RecyclerView.Adapter<TVShowViewHolder>() {

    private var tvList: List<TvShow> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<TvShow>) {
        this.tvList = newList
    }

    var onTvClickListener: OnTvClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_tv_show,
                parent,
                false
            )
        return TVShowViewHolder(view, onTvClickListener)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(tvList[position])
    }

    override fun getItemCount() = tvList.size
}

class TVShowViewHolder(view: View, private val onTvClickListener: OnTvClickListener?) :
    RecyclerView.ViewHolder(view) {
    private val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    fun bind(tvShow: TvShow) {
        itemView.run {
            tvTitle.text = tvShow.name
            tvFirstAirDate.text = tvShow.first_air_date
            tvVoteAverage.text = tvShow.vote_average.toString()
            tvShow.poster_path?.let {
                Glide
                    .with(this)
                    .load(RemoteConfig.getThumbnailsUrl(it))
                    .apply(requestOptions)
                    .fitCenter()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(ivPoster)
            }


            setOnClickListener { onTvClickListener?.onItemClicked(tvShow) }
        }
    }
}

interface OnTvClickListener {
    fun onItemClicked(item: TvShow)
    fun onFavClicked(item: TvShow)
}