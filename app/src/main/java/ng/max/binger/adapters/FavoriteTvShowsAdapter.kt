package ng.max.binger.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import ng.max.binger.R
import ng.max.binger.R.id.*
import ng.max.binger.utils.DisplayUtils
import ng.max.binger.activities.DetailsActivity
import ng.max.binger.data.FavoriteShow


class FavoriteTvShowsAdapter(var context: Context, var clickListener: FavoriteBtnClickListener,
                             var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<FavoriteTvShowsAdapter.ViewHolder>() {

    private var favouriteShows = arrayListOf<FavoriteShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = favouriteShows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favouriteShows[position])
        holder.favoriteBtn.setImageResource(R.drawable.ic_favorite)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(favouriteShows[position].tvShowId)
        }

        holder.favoriteBtn.setOnClickListener {
            clickListener.onFavoriteBtnClickListener(holder.adapterPosition, favouriteShows[position].tvShowId)
        }
    }

    fun addAll(list: List<FavoriteShow>) {
        for (result in list) {
            add(result)
        }
    }

    fun add(r: FavoriteShow) {
        favouriteShows.add(r)
        notifyItemInserted(favouriteShows.size - 1)
    }

    fun clear() {
        favouriteShows.clear()
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        favouriteShows.removeAt(position)
        notifyDataSetChanged()
    }

    interface FavoriteBtnClickListener {
        fun onFavoriteBtnClickListener(itemPosition: Int, showId: Int)
    }

    interface ItemClickListener {
        fun onItemClickListener(showId: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title = view.findViewById<TextView>(R.id.videoTitle)
        var description = view.findViewById<TextView>(R.id.videoDescription)
        var rating = view.findViewById<TextView>(R.id.videoRating)
        var year = view.findViewById<TextView>(R.id.productionYear)
        var poster = view.findViewById<ImageView>(R.id.videoPoster)
        var favoriteBtn = view.findViewById<ImageView>(R.id.likeButton)

        fun bind(favouriteShow: FavoriteShow) {
            Glide.with(context).load(DisplayUtils.getImageUrl(imagePath = favouriteShow.posterPath))
                    .into(poster)

            title.text = favouriteShow.name
            description.text = favouriteShow.summary
            rating.text = favouriteShow.rating.toString()
            year.text = DisplayUtils.getYear(favouriteShow.firstAirDate)
        }
    }
}