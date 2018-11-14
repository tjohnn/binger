package ng.max.binger.client

import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowDetail
import retrofit2.Call
import retrofit2.http.*

interface ClientEndPoint {

    @GET("tv/airing_today")
    fun getTvAiringToday(
            @Query("api_key") apiKey:String,
            @Query("page") page:Int
            ):Call<TvShow>

    @GET("tv/popular")
    fun getTvPopularShow(
            @Query("api_key") apiKey:String,
            @Query("page") page:Int
    ):Call<TvShow>

    @GET("tv/{tv_id}")
    fun getTvDetailById(
            @Path("tv_id") tvId:Int,
            @Query("api_key") apiKey:String
    ):Call<TvShowDetail>
}