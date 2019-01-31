package ng.max.binger.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import dagger.android.DaggerIntentService
import io.reactivex.disposables.CompositeDisposable
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShowRepository
import javax.inject.Inject

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class SyncService : DaggerIntentService("SyncService") {

    @Inject
    lateinit var tvShowRepository: TvShowRepository

    private val compositeDisposable = CompositeDisposable()

    override fun onHandleIntent(intent: Intent?) {
        compositeDisposable.add(
                tvShowRepository.getFavoriteShowsSingle()
                        .toFlowable()
                        .flatMapIterable { it }
                        .subscribe( {
                            loadShowDetail(it)
                        }, {

                        })
        )
    }

    private fun loadShowDetail(tvShow: FavoriteShow){

        compositeDisposable.add(
                tvShowRepository.getShowById(tvShow.tvShowId)
                        .subscribe({
                            Log.d("LOG_TAG", "Show fetched: ${it.name}")
                            updateFavoriteDetail(FavoriteShow(it))
                        }, {}))

    }


    private fun updateFavoriteDetail(favoriteShow: FavoriteShow) {
        compositeDisposable.add(
                tvShowRepository.updateMovieDetail(favoriteShow.tvShowId, favoriteShow.latestSeason, favoriteShow.latestEpisode)
                        .subscribe()
        )

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}
