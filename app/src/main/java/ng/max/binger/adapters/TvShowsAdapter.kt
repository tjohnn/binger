package ng.max.binger.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ng.max.binger.R
import ng.max.binger.data.TvShow

class TvShowsAdapter: RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private var tvShows: ArrayList<TvShow> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tvShows[position])


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShow) {
            // TODO: setup view's texts and click listeners with tvShow
        }
    }
}