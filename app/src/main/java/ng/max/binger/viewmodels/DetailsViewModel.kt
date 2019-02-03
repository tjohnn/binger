package ng.max.binger.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowDetail
import ng.max.binger.data.TvShowRepository
import ng.max.binger.utils.AppSchedulers
import ng.max.binger.utils.EspressoIdlingResource
import ng.max.binger.utils.EventWrapper
import ng.max.binger.utils.Utils
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
        app: Application,
        private var tvShowRepository: TvShowRepository,
        private var appSchedulers: AppSchedulers
) : AndroidViewModel(app) {


    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var tvShowDetail: MutableLiveData<TvShowDetail> = MutableLiveData()
    private var snackBarMessage: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getSnackBarMessage(): LiveData<EventWrapper<String>> {
        return snackBarMessage
    }

    fun getTvShowDetail(): LiveData<TvShowDetail> {
        return tvShowDetail
    }



    fun loadMovieDetail(showId: Int){
        EspressoIdlingResource.increment()
        compositeDisposable.add(
                tvShowRepository.getShowById(showId)
                        .subscribeOn(appSchedulers.io())
                        .observeOn(appSchedulers.main())
                        .doOnSubscribe{isLoading.postValue(true)}
                        .doAfterSuccess{isLoading.postValue(false)}
                        .doOnError{isLoading.postValue(false)}
                        .subscribe({
                            tvShowDetail.value = it
                            EspressoIdlingResource.decrement()
                        }, {
                            snackBarMessage.value = EventWrapper(Utils.processNetworkError(it))
                            EspressoIdlingResource.decrement()
                        }))

    }




    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}