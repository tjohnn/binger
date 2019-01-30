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

class PopularShowsViewModel @Inject constructor(
        app: Application,
        private var tvShowRepository: TvShowRepository,
        private var appSchedulers: AppSchedulers
) : AndroidViewModel(app) {


    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isLoadingMore: MutableLiveData<Boolean> = MutableLiveData()
    var tvShows: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
    var favorites: MutableLiveData<List<FavoriteShow>> = MutableLiveData()
    var snackBarMessage: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var currentPage: Int = 0
    private var totalPages: Int = 0

    init {
        loadFavorites()
        loadFirstPage()
    }

    private fun loadFavorites() {
        compositeDisposable.add(
                tvShowRepository.getFavoriteShows()
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe({
                            favorites.postValue(it)
                            Log.d("LOG_TAG", "favs: ${it.size}")
                        },{})
        )
    }


    private fun loadFirstPage(){
        tvShows.value = arrayListOf()

        currentPage = 1

        loadTvShows(currentPage)
    }

    fun loadNextPage(){
        currentPage++
        if(currentPage > totalPages) return
        // always refresh other pages
        loadTvShows(currentPage)

    }



    private fun loadTvShows(page: Int) {
        compositeDisposable.add(
                tvShowRepository.getTvShows(page)
                        .map {
                            totalPages = it.totalPages
                            it.results
                        }
                        .toFlowable()
                        .flatMapIterable { it }
                        .map { setIsTvShowLiked(it) }
                        .toList()
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .doOnSubscribe{when(page){
                            1 -> isLoading.value = true
                            else -> isLoadingMore.value = true
                        }}
                        .doAfterSuccess{when(page){
                            1 -> isLoading.value = false
                            else -> isLoadingMore.value = false
                        }}
                        .doOnError{when(page){
                            1 -> isLoading.value = false
                            else -> isLoadingMore.value = false
                        }}
                        .subscribe({
                            val list = tvShows.value ?: arrayListOf()
                            list.addAll(it)
                            tvShows.value = list

                        }, {
                            snackBarMessage.value = EventWrapper(Utils.processNetworkError(it))
                        }))

    }

    // check whether tvshow is among favorite and set accordingly
    private fun setIsTvShowLiked(tvShow: TvShow): TvShow{
        if(favorites.value?.isEmpty() == false){
            favorites.value?.forEach{
                if(it.tvShowId == tvShow.id){
                    tvShow.isFavorite = true
                    return tvShow
                }
            }
        }
        tvShow.isFavorite = false
        return tvShow

    }

    fun likeMovie(tvShow: TvShow){

        addFavoriteShow( FavoriteShow(tvShow))
        // attempt to get tv details and update it
        compositeDisposable.add(
                tvShowRepository.getShowById(tvShow.id)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe({
                            updateFavoriteDetail(FavoriteShow(it))
                        }, {}))

    }

    private fun addFavoriteShow(favoriteShow: FavoriteShow) {
        compositeDisposable.add(
                tvShowRepository.addFavoriteShow(favoriteShow)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe()
        )

    }

    private fun updateFavoriteDetail(favoriteShow: FavoriteShow) {
        compositeDisposable.add(
                tvShowRepository.updateMovieDetail(favoriteShow.tvShowId, favoriteShow.latestSeason, favoriteShow.latestEpisode)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .subscribe()
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