package ng.max.binger.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowRepository
import ng.max.binger.utils.AppSchedulers
import ng.max.binger.utils.EventWrapper
import ng.max.binger.utils.Utils
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
        app: Application,
        private var tvShowRepository: TvShowRepository,
        private var appSchedulers: AppSchedulers
) : AndroidViewModel(app) {


    var favorites: MutableLiveData<List<TvShow>> = MutableLiveData()
    var snackBarMessage: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        compositeDisposable.add(
                tvShowRepository.getFavoriteShows()
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe({
                            val shows: ArrayList<TvShow> = arrayListOf()
                            it.forEach{
                                shows.add(TvShow(it))
                            }
                            favorites.postValue(shows)
                            Log.d("LOG_TAG", shows.toString())
                        },{
                            snackBarMessage.postValue(EventWrapper(it.message))
                        })
        )
    }

    fun unlikeMovie(movieId: Int){

        compositeDisposable.add(
                tvShowRepository.removeFavoriteShow(movieId)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe()
        )


    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}