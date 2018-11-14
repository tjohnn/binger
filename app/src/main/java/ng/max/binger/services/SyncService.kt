package ng.max.binger.services

import android.app.IntentService
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.Context
import android.util.Log
import ng.max.binger.client.ClientEndPoint
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShowDetail
import ng.max.binger.repository.FavoriteShowRepo
import ng.max.binger.repository.ShowDetailsWithId
import ng.max.binger.retrofit_service.NetworkFactory
import ng.max.binger.utils.TMDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class SyncService : IntentService("SyncService") {

    private val APIKEY = TMDB.API_KEY
    private val TAG = SyncService::class.java.simpleName

    override fun onHandleIntent(intent: Intent?) {
        //Initializing the AppDatabase
        val db = AppDatabase.getInstance(applicationContext)
        var favorites = db.favoriteDao.getFavoritesNoLiveData()
        //checking for empty before proceeding with synchronization
        if (favorites.isNotEmpty()) {
            //Check for update for all id in the database and resave the data
            for (all in favorites) {
                val service = NetworkFactory().getNetworkInstance()!!.create(ClientEndPoint::class.java)
                val call = service.getTvDetailById(all.tvShowId, APIKEY)
                call.enqueue(object : Callback<TvShowDetail> {
                    override fun onFailure(call: Call<TvShowDetail>, t: Throwable) {
                        Log.v(TAG, "error syncing with server")
                    }

                    override fun onResponse(call: Call<TvShowDetail>, response: Response<TvShowDetail>) {
                        val r = response.body()
                        val favoritesUpdate = FavoriteShow(id = r!!.id, tvShowId = r.id, name = r.name, rating = r.rating,
                                summary = r.summary, voteCount = r.voteCount, posterPath = r.posterPath,
                                backdropPath = r.backdropPath, latestSeason = r.numberOfSeasons, latestEpisode = r.numberOfEpisodes,
                                firstAirDate = r.firstAirDate)

                        //Replacing the new value with the existing ones
                        db.favoriteDao.insertFavorite(favoritesUpdate)
                    }
                })
            }
        }
    }
}
