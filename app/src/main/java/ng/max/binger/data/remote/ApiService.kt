package ng.max.binger.data.remote


import io.reactivex.Single
import ng.max.binger.data.PagedList
import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tv/popular")
    fun loadTvShows(@Query("page") page: Int): Single<PagedList<TvShow>>

    @GET("tv/airing_today")
    fun loadAiringToday(@Query("page") page: Int): Single<PagedList<TvShow>>

    @GET("tv/{showId}")
    fun getShowById(@Path("showId") showId: Int): Single<TvShowDetail>

}
