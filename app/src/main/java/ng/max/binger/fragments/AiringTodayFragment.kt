package ng.max.binger.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import ng.max.binger.R
import ng.max.binger.activities.DetailsActivity
import ng.max.binger.adapters.TvShowsAdapter
import ng.max.binger.data.TvShow
import ng.max.binger.viewmodels.AiringTodayViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */

class AiringTodayFragment : DaggerFragment(), TvShowsAdapter.OnTvShowItemListener {

    private lateinit var adapter: TvShowsAdapter

    private lateinit var mViewModel: AiringTodayViewModel

    private lateinit var tvShowsToday: RecyclerView

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, factory).get(AiringTodayViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_airing_today, container, false)

        tvShowsToday = root.findViewById(R.id.tvShowsToday)

        setupRecyclerView()

        subscribeToViewModel()


        return root
    }

    private fun setupRecyclerView() {
        adapter = TvShowsAdapter(mActivity, this)
        tvShowsToday.setHasFixedSize(true)
        tvShowsToday.layoutManager = LinearLayoutManager(mActivity)
        tvShowsToday.adapter = adapter

        tvShowsToday.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (mViewModel.isLoading.value != true && !recyclerView.canScrollVertically(1)) {
                    mViewModel.loadNextPage()
                }
            }
        })

    }

    private fun subscribeToViewModel() {
        mViewModel.tvShows.observe(this, Observer{ shows ->
            adapter.updateData(shows as ArrayList<TvShow>)
        })

        mViewModel.snackBarMessage.observe(this, Observer { it ->
            it?.getContentIfNotHandled()?.run {
                Snackbar.make(mActivity.findViewById(android.R.id.content), this, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClicked(tvShow: TvShow) {
        Log.d("LOG_TAG", "TvShow clicked")
        val intent = Intent(mActivity, DetailsActivity::class.java)

        intent.putExtra(DetailsActivity.SHOW_ID_KEY, tvShow.id)
        startActivity(intent)
    }

    override fun onItemLiked(tvShow: TvShow) {
        if(tvShow.isFavorite) {
            mViewModel.unlikeMovie(tvShow.id)
        } else {
            mViewModel.likeMovie(tvShow)
        }
    }

    companion object {
        fun newInstance() = AiringTodayFragment()
    }

}
