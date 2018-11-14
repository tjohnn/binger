package ng.max.binger.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ng.max.binger.data.TvShowDetail
import ng.max.binger.repository.ShowDetailsWithId
import ng.max.binger.utils.TMDB

class DetailsActivityVM : ViewModel() {
    private val APIKEY = TMDB.API_KEY

    private var detailsRepo: ShowDetailsWithId? = null
    init {
        detailsRepo = ShowDetailsWithId()
    }

    fun getShowDetailsWithId(showId:Int):MutableLiveData<TvShowDetail>{
        return detailsRepo!!.getShowDetailsWithId(APIKEY, showId)
    }
}