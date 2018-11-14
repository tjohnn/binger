package ng.max.binger.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import ng.max.binger.R
import ng.max.binger.activities.DetailsActivity
import ng.max.binger.adapters.FavoriteTvShowsAdapter
import ng.max.binger.adapters.PaginationScrollListener
import ng.max.binger.adapters.TvShowsAdapter
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.utils.TMDB
import ng.max.binger.viewmodel.TvShowViewModel


/**
 * A simple [Fragment] subclass.
 */

class PopularShowsFragment: Fragment(),
        TvShowsAdapter.FavoriteBtnClickListener, TvShowsAdapter.ItemClickListener {

    private lateinit var adapter: TvShowsAdapter
    private lateinit var viewModel: TvShowViewModel
    private var PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 1
    private var currentPage = PAGE_START

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_popular_shows, container, false)

        //Settingup recyclerview
        val popularShowsRecycler = root.findViewById<RecyclerView>(R.id.popularTvShows)
        val layoutManager = LinearLayoutManager(activity!!)
        popularShowsRecycler.layoutManager = layoutManager
        adapter = TvShowsAdapter(context!!, this, this)
        popularShowsRecycler.adapter = adapter

        //Observing the viewmodel
        viewModel = ViewModelProviders.of(activity!!).get(TvShowViewModel::class.java)
        getPopularShows()


        //Adding pagination
        popularShowsRecycler.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                // mocking network delay for API call
                Handler().postDelayed( {
                    viewModel.getAiringTodayRepo(currentPage)
                }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        return root
    }

    companion object {
        fun newInstance() = PopularShowsFragment()
    }

    //Binidng data onValue from repository
    private fun getPopularShows(){
        viewModel.getPopularShow().observe(activity!!, Observer {
            if (it != null){
                TOTAL_PAGES = it.totalPages
                adapter.addAll(it.tvShowList!!)
            }else{
                Toast.makeText(context, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClickListener(showId: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(TMDB.ID_KEY, showId)
        startActivity(intent)
    }

    override fun onFavoriteBtnClickListener(view: ImageView, favouriteShow: TvShow) {
        var imageResource = view.drawable
        if (imageResource == ContextCompat.getDrawable(activity!!, R.drawable.ic_favorite)){
            view.setImageResource(R.drawable.ic_favorite_holo)
            viewModel.deleteShow(favouriteShow.id)
        }else{
            view.setImageResource(R.drawable.ic_favorite)
            viewModel.saveFavoriteShow(FavoriteShow(id =favouriteShow.id, tvShowId = favouriteShow.id,
                    name = favouriteShow.name, rating = favouriteShow.rating,
                    summary = favouriteShow.summary, voteCount = favouriteShow.voteCount,
                    posterPath = favouriteShow.posterPath, backdropPath = favouriteShow.backdropPath,
                    latestSeason = 0, latestEpisode = 0, firstAirDate = favouriteShow.firstAirDate))
        }
    }
}
