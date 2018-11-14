package ng.max.binger.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import kotlinx.android.synthetic.main.activity_details.*
import ng.max.binger.R
import ng.max.binger.adapters.GenreAdapter
import ng.max.binger.adapters.ProCompany
import ng.max.binger.databinding.ActivityDetailsBinding
import ng.max.binger.utils.DisplayUtils
import ng.max.binger.utils.TMDB
import ng.max.binger.viewmodel.DetailsActivityVM
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class DetailsActivity : AppCompatActivity() {

    private lateinit var viewmodel:DetailsActivityVM
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var genreAdapter:GenreAdapter
    private lateinit var proCompanyAdapter:ProCompany

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)

        //Initialize the Flexbox as the recyclerview linearlayoutmanager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START

        //Initialize the Flexbox as the recyclerview linearlayoutmanager
        val layoutManager2 = FlexboxLayoutManager(this)
        layoutManager2.flexDirection = FlexDirection.ROW
        layoutManager2.justifyContent = JustifyContent.FLEX_START

        //Assigning recyclerview to new variables
        val genreRecyclerview = binding.recyclerviewGenre
        val proCompanyRecyclerview = binding.recyclerviewCompanyLogo

        //Initializing recyclerview adapter
        genreAdapter = GenreAdapter()
        proCompanyAdapter = ProCompany(this)

        //Setting up the recyclerviews with adapter
        genreRecyclerview.adapter = genreAdapter
        proCompanyRecyclerview.adapter = proCompanyAdapter
        genreRecyclerview.layoutManager = layoutManager
        proCompanyRecyclerview.layoutManager = layoutManager2

        // set support toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewmodel = ViewModelProviders.of(this).get(DetailsActivityVM::class.java)

        //Getting the showId of the clickedShow
        val intent = intent.extras
        if (intent != null){
            getShowDetail(intent.getInt(TMDB.ID_KEY))
        }
    }

    /**
     * The method observe the Details viewmodel and setup the view accordingly
     * By binding the view the observed value
     */
    fun getShowDetail(showId:Int){
        viewmodel.getShowDetailsWithId(showId).observe(this, Observer {
            if (it != null){
                Glide.with(this).load(DisplayUtils.getImageUrl(imagePath = it.posterPath))
                        .into(binding.moviePoster)
                binding.movieTitle.text = it.name
                binding.movieYear.text = it.firstAirDate.toString()
                genreAdapter.addAll(it.genres)
                proCompanyAdapter.addAll(it.productionCompanies)
                binding.movieSummary.text = it.summary
                binding.showStatus.text = it.status

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
