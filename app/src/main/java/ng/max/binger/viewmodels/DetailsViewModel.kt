package ng.max.binger.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowDetail
import ng.max.binger.data.TvShowRepository
import ng.max.binger.utils.AppSchedulers
import ng.max.binger.utils.EventWrapper
import ng.max.binger.utils.Utils
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
        app: Application,
        private var tvShowRepository: TvShowRepository,
        private var appSchedulers: AppSchedulers
) : AndroidViewModel(app) {


    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var tvShowDetail: MutableLiveData<TvShowDetail> = MutableLiveData()
    var snackBarMessage: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()



    fun loadMovieDetail(showId: Int){

        compositeDisposable.add(
                tvShowRepository.getShowById(showId)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .doOnSubscribe{isLoading.postValue(true)}
                        .doAfterSuccess{isLoading.postValue(false)}
                        .doOnError{isLoading.postValue(false)}
                        .subscribe({
                            tvShowDetail.value = it
                        }, {
                            snackBarMessage.value = EventWrapper(Utils.processNetworkError(it))
                        }))

    }




    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}