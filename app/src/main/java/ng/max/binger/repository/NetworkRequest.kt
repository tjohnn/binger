package ng.max.binger.repository

import android.arch.lifecycle.MutableLiveData
import ng.max.binger.client.ClientEndPoint
import ng.max.binger.data.TvShow
import ng.max.binger.data.TvShowDetail
import ng.max.binger.retrofit_service.NetworkFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * It is the datalayer or Repository for providing content AiringTodayFragment
 * @param apiKey
 * @param pageNumber
 * @return MutableLiveData<TvShow>
 */

class AiringTvToday {
    fun getAiringToday(apiKey: String, page: Int): MutableLiveData<TvShow> {
        var data = MutableLiveData<TvShow>()
        val factory = NetworkFactory().getNetworkInstance()!!.create(ClientEndPoint::class.java)
        val call = factory.getTvAiringToday(apiKey, page)
        call.enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                data.value = response.body()
            }
        })
        return data
    }
}

/**
 * It is the datalayer or Repository for providing content PopularShowsFragment
 * @param apiKey
 * @param pageNumber
 * @return MutableLiveData<TvShow>
 */

class PopularShow {
    fun getPopularShow(apiKey: String, page: Int): MutableLiveData<TvShow> {
        var data = MutableLiveData<TvShow>()
        val factory = NetworkFactory().getNetworkInstance()!!.create(ClientEndPoint::class.java)
        val call = factory.getTvPopularShow(apiKey, page)
        call.enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                data.value = response.body()
            }
        })
        return data
    }
}


/**
 * It is the datalayer or Repository for providing content DeatilsActivity
 * @param apiKey
 * @param showId
 * @return MutableLiveData<TvShowDetail>
 */
class ShowDetailsWithId {
    fun getShowDetailsWithId(apiKey: String, showId: Int): MutableLiveData<TvShowDetail> {
        val data = MutableLiveData<TvShowDetail>()
        val factory = NetworkFactory().getNetworkInstance()!!.create(ClientEndPoint::class.java)
        val call = factory.getTvDetailById(showId, apiKey)
        call.enqueue(object : Callback<TvShowDetail> {
            override fun onFailure(call: Call<TvShowDetail>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<TvShowDetail>, response: Response<TvShowDetail>) {
                data.value = response.body()
            }
        })
        return data
    }
}