package ng.max.binger.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.fragment_airing_today.*
import ng.max.binger.R
import ng.max.binger.adapters.TvShowsAdapter
import ng.max.binger.data.TvShow
import ng.max.binger.viewmodels.DetailsViewModel
import ng.max.binger.viewmodels.FavoritesViewModel
import javax.inject.Inject

class FavoritesActivity : DaggerAppCompatActivity() , TvShowsAdapter.OnTvShowItemListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var mViewModel: FavoritesViewModel

    private lateinit var adapter: TvShowsAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // set support toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel::class.java)

        setupRecyclerView()
        subscribeToViewModel()
    }


    private fun setupRecyclerView() {
        adapter = TvShowsAdapter(this, this)
        favoriteTvShows.setHasFixedSize(true)
        favoriteTvShows.layoutManager = LinearLayoutManager(this)
        favoriteTvShows.adapter = adapter


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeToViewModel() {
        mViewModel.getFavorites().observe(this, Observer{ shows ->
            adapter.updateData(shows as ArrayList<TvShow>)
        })

        mViewModel.getSnackBarMessage().observe(this, Observer { it ->
            it?.getContentIfNotHandled()?.run {
                Snackbar.make(findViewById(android.R.id.content), this, Snackbar.LENGTH_LONG).show()
            }
        })

    }

    override fun onItemClicked(tvShow: TvShow) {
        Log.d("LOG_TAG", "TvShow clicked")
        val intent = Intent(this, DetailsActivity::class.java)

        intent.putExtra(DetailsActivity.SHOW_ID_KEY, tvShow.id)
        startActivity(intent)
    }

    override fun onItemLiked(tvShow: TvShow) {
        Log.d("LOG_TAG", "I see Ya")
        mViewModel.unlikeMovie(tvShow.id)

    }

}
