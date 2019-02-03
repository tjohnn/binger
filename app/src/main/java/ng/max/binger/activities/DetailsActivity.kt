package ng.max.binger.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import ng.max.binger.R
import ng.max.binger.data.Genre
import ng.max.binger.data.ProductionCompany
import ng.max.binger.utils.DateUtlils
import ng.max.binger.utils.GlideApp
import ng.max.binger.utils.TMDB
import ng.max.binger.viewmodels.DetailsViewModel
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var mViewModel: DetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)



        // set support toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)

        intent?.extras?.run {
            mViewModel.loadMovieDetail(this.getInt(SHOW_ID_KEY))
            subscribeToViewModel()
        }

    }

    private fun subscribeToViewModel() {
        mViewModel.getTvShowDetail().observe(this, Observer {i ->
            i?.let {show ->
                show.let {

                    GlideApp.with(this)
                            .load(TMDB.POSTERS_BASE_URL + TMDB.POSTERS_W_342 + it.posterPath)
                            .placeholder(R.drawable.paramount_logo)
                            .fitCenter()
                            .into(moviePoster)

                    movieTitle.text = it.name
                    movieYear.text = DateUtlils.formatDateYear(it.firstAirDate)
                    movieSummary.text = it.summary
                    showStatus.text = it.status

                    addGenres(it.genres)
                    addCompanies(it.productionCompanies)
                }
            }
        })

        mViewModel.getSnackBarMessage().observe(this, Observer { it ->
            it?.getContentIfNotHandled()?.run {
                Snackbar.make(findViewById(android.R.id.content), this, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun addCompanies(productionCompanies: java.util.ArrayList<ProductionCompany>) {
        productionCompanies.forEach{item ->
            val v: View = layoutInflater.inflate(R.layout.list_item_production_company, genreCompanyWrapper, false)
            genreCompanyWrapper.addView(v)
            val imageView: ImageView = v.findViewById(R.id.companyLogo)
            GlideApp.with(this)
                    .load(TMDB.POSTERS_BASE_URL + TMDB.POSTERS_W_342 + item.logoPath)
                    .placeholder(R.drawable.paramount_logo)
                    .fitCenter()
                    .into(imageView)
        }
    }

    private fun addGenres(genres: ArrayList<Genre>) {
        genres.forEachIndexed{i, item ->
            val v: View = layoutInflater.inflate(R.layout.list_item_genre, genreCompanyWrapper, false)

            genreCompanyWrapper.addView(v, i+1)
            val textView: TextView = v.findViewById(R.id.genreName)
            textView.text = item.name
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val SHOW_ID_KEY = "SHOW_ID_KEY"
    }
}
