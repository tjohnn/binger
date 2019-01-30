package ng.max.binger.adapters

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ng.max.binger.R
import ng.max.binger.data.TvShow
import ng.max.binger.utils.DateUtlils
import ng.max.binger.utils.GlideApp
import ng.max.binger.utils.TMDB.POSTERS_BASE_URL
import ng.max.binger.utils.TMDB.POSTERS_W_185
import java.util.*

class TvShowsAdapter(val acti: AppCompatActivity, val mListener: OnTvShowItemListener): RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private var tvShows: ArrayList<TvShow> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tv_show, parent, false)
        return ViewHolder(view)
    }

    fun updateData(tvShows: ArrayList<TvShow>){
        this.tvShows = tvShows
        notifyDataSetChanged()
        Log.d("LOG-TAG", "items are: " + tvShows.size)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }


        inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener {


            var videoPoster: ImageView = view.findViewById(R.id.videoPoster)
            var videoTitle: TextView = view.findViewById(R.id.videoTitle)
            var videoDescription: TextView = view.findViewById(R.id.videoDescription)
            var videoRating: TextView = view.findViewById(R.id.videoRating)
            var productionYear: TextView = view.findViewById(R.id.productionYear)
            var likeButton: ImageView = view.findViewById(R.id.likeButton)
            private lateinit var tvShow: TvShow

            init {
                view.setOnClickListener(this)
            }

            fun bind(tvShow: TvShow) {
                this.tvShow = tvShow
                videoTitle.text = tvShow.name
                videoDescription.text = tvShow.summary
                videoRating.text = tvShow.rating.toString()
                tvShow.firstAirDate?.run{
                    productionYear.text = DateUtlils.formatDateYear(this)
                }
                if(tvShow.isFavorite){
                    likeButton.setImageResource(R.drawable.ic_favorite)
                }
                GlideApp.with(acti)
                        .load(POSTERS_BASE_URL + POSTERS_W_185 + tvShow.posterPath)
                        .placeholder(R.drawable.paramount_logo)
                        .fitCenter()
                        .into(videoPoster)

                likeButton.setOnClickListener{
                    mListener.onItemLiked(tvShow)
                    if(tvShow.isFavorite){
                        likeButton.setImageResource(R.drawable.ic_favorite_holo)
                        tvShow.isFavorite = false
                    } else {
                        likeButton.setImageResource(R.drawable.ic_favorite)
                        tvShow.isFavorite = true
                    }
                }

            }

            override fun onClick(v: View?) {
                mListener.onItemClicked(tvShow)
            }
        }

        interface OnTvShowItemListener{
            fun onItemClicked(tvShow: TvShow)
            fun onItemLiked(tvShow: TvShow)
        }
}