package ng.max.binger.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_airing_today.*
import ng.max.binger.R
import ng.max.binger.activities.DetailsActivity
import ng.max.binger.adapters.TvShowsAdapter
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.utils.TMDB
import ng.max.binger.viewmodel.TvShowViewModel
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Handler
import android.util.Log
import ng.max.binger.adapters.PaginationScrollListener
import ng.max.binger.services.SyncService
import android.support.v4.os.HandlerCompat.postDelayed




/**
 * A simple [Fragment] subclass.
 */

class AiringTodayFragment : Fragment(),
        TvShowsAdapter.FavoriteBtnClickListener, TvShowsAdapter.ItemClickListener {

    private lateinit var adapter: TvShowsAdapter
    private val TAG = AiringTodayFragment::class.java.simpleName
    private lateinit var viewModel: TvShowViewModel
    private lateinit var alarmMgr: AlarmManager
    private var PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 1
    private var currentPage = PAGE_START

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_airing_today, container, false)

        //Initializing Alarmmanager for repeating work
        alarmMgr = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Setting up recyclerview
        val airingRecycler = root.findViewById<RecyclerView>(R.id.tvShowsToday)
        val layoutManager = LinearLayoutManager(activity!!)
        airingRecycler.layoutManager = layoutManager
        adapter = TvShowsAdapter(context!!, this, this)
        airingRecycler.adapter = adapter

        //Observing viewmodel
        viewModel = ViewModelProviders.of(activity!!).get(TvShowViewModel::class.java)
        getAiringToday()

        //Adding pagination
        airingRecycler.addOnScrollListener(object : PaginationScrollListener(layoutManager){
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

        startSyncService(context!!)

        return root
    }

    companion object {
        fun newInstance() = AiringTodayFragment()
    }

    private fun getAiringToday() {
        viewModel.getAiringTodayRepo().observe(activity!!, Observer {
            if (it != null) {
                TOTAL_PAGES = it.totalPages
                adapter.addAll(it.tvShowList!!)
            } else {
                Toast.makeText(context, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startSyncService(context: Context) {
        val intent = Intent(context, SyncService::class.java)
        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)

        val dailyInterval = AlarmManager.INTERVAL_DAY

        val triggerTime = System.currentTimeMillis() + dailyInterval

        // Configure Alarmmanager to trigger syncing daily
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, dailyInterval, pendingIntent)
    }

    override fun onItemClickListener(showId: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(TMDB.ID_KEY, showId)
        startActivity(intent)
    }

    override fun onFavoriteBtnClickListener(view: ImageView, favouriteShow: TvShow) {
        var imageResource = view.drawable
        if (imageResource == ContextCompat.getDrawable(activity!!, R.drawable.ic_favorite)) {
            view.setImageResource(R.drawable.ic_favorite_holo)
            viewModel.deleteShow(favouriteShow.id)
        } else {
            view.setImageResource(R.drawable.ic_favorite)
            viewModel.saveFavoriteShow(FavoriteShow(id = favouriteShow.id, tvShowId = favouriteShow.id,
                    name = favouriteShow.name, rating = favouriteShow.rating,
                    summary = favouriteShow.summary, voteCount = favouriteShow.voteCount,
                    posterPath = favouriteShow.posterPath, backdropPath = favouriteShow.backdropPath,
                    latestSeason = 0, latestEpisode = 0, firstAirDate = favouriteShow.firstAirDate))
        }
    }
}
