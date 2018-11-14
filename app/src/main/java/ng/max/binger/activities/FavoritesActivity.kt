package ng.max.binger.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_favorites.*
import ng.max.binger.R
import ng.max.binger.adapters.FavoriteTvShowsAdapter
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShow
import ng.max.binger.utils.TMDB
import ng.max.binger.viewmodel.FavoritesVM

class FavoritesActivity : AppCompatActivity(),
        FavoriteTvShowsAdapter.FavoriteBtnClickListener, FavoriteTvShowsAdapter.ItemClickListener {

    private lateinit var viewmodel: FavoritesVM
    private lateinit var adapter: FavoriteTvShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        //Settingup favorites list adapter
        adapter = FavoriteTvShowsAdapter(this,this, this)
        val recyclerView = findViewById<RecyclerView>(R.id.favorites_show)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // set support toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /**
         * Observing the favorites viewmodel
         */
        viewmodel = ViewModelProviders.of(this).get(FavoritesVM::class.java)
        viewmodel.getFavorites().observe(this, Observer {
            if (it != null) {
                adapter.addAll(it)
            }
        })
    }

    //Controlling the upButton to avoid page reloading
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home  ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClickListener(showId: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(TMDB.ID_KEY, showId)
        startActivity(intent)
    }

    override fun onFavoriteBtnClickListener(itemPosition: Int, showId: Int) {
        viewmodel.deletefavorite(showId)
        adapter.remove(itemPosition)
    }

}
