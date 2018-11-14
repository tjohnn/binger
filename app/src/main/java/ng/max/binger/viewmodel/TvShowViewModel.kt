package ng.max.binger.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.repository.AiringTvToday
import ng.max.binger.repository.FavoriteShowRepo
import ng.max.binger.repository.PopularShow
import ng.max.binger.utils.TMDB
import org.jetbrains.annotations.NotNull

class TvShowViewModel(@NotNull application: Application) : AndroidViewModel(application) {
    private var airingTodayRepo:AiringTvToday? = null
    private var popularShowRepo:PopularShow? = null
    private var favoriteRepo:FavoriteShowRepo? = null
    private val APIKEY = TMDB.API_KEY

    init {
        airingTodayRepo = AiringTvToday()
        popularShowRepo = PopularShow()
        favoriteRepo = FavoriteShowRepo(application)
    }

    fun getAiringTodayRepo(pageNumber:Int = 1):MutableLiveData<TvShow>{
        return airingTodayRepo!!.getAiringToday(APIKEY, pageNumber)
    }

    fun getPopularShow(pageNumber:Int = 1):MutableLiveData<TvShow>{
        return popularShowRepo!!.getPopularShow(APIKEY, pageNumber)
    }

    fun deleteShow(showId:Int){
        favoriteRepo!!.deleteFavorite(showId)
    }

    fun saveFavoriteShow(favoriteShow: FavoriteShow){
        favoriteRepo!!.setFavorites(favoriteShow)
    }
}