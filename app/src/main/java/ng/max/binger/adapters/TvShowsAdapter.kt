package ng.max.binger.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ng.max.binger.R
import ng.max.binger.R.id.*
import ng.max.binger.data.TvShow
import ng.max.binger.utils.DisplayUtils


class TvShowsAdapter (var context:Context, var clickListener:FavoriteBtnClickListener, var itemClickListener:ItemClickListener)
    : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private var tvShows = arrayListOf<TvShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(tvShows[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(tvShows[position].id)
        }

        holder.favoriteBtn.setOnClickListener {
            clickListener.onFavoriteBtnClickListener(holder.favoriteBtn, tvShows[position])
        }
    }

    fun addAll(list: List<TvShow>) {
        for (result in list) {
            add(result)
        }
    }

    fun add(r: TvShow) {
        tvShows.add(r)
        notifyItemInserted(tvShows.size - 1)
    }

    fun clear() {
        tvShows.clear()
        notifyDataSetChanged()
    }

     interface FavoriteBtnClickListener{
       fun onFavoriteBtnClickListener(view:ImageView, favouriteShow:TvShow)
    }

    interface ItemClickListener{
        fun onItemClickListener(showId:Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var title = view.findViewById<TextView>(R.id.videoTitle)
        var description = view.findViewById<TextView>(R.id.videoDescription)
        var rating = view.findViewById<TextView>(R.id.videoRating)
        var year = view.findViewById<TextView>(R.id.productionYear)
        var poster = view.findViewById<ImageView>(R.id.videoPoster)
        var favoriteBtn = view.findViewById<ImageView>(R.id.likeButton)

        fun bind(tvShow: TvShow) {
            Glide.with(context).load(DisplayUtils.getImageUrl(imagePath = tvShow.posterPath))
                    .into(poster)

            title.text = tvShow.name
            description.text = tvShow.summary
            rating.text = tvShow.rating.toString()
            year.text = DisplayUtils.getYear(tvShow.firstAirDate)
        }
    }
}