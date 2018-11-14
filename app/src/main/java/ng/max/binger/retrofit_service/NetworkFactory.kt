package ng.max.binger.retrofit_service

import ng.max.binger.BuildConfig
import ng.max.binger.utils.DisplayUtils
import ng.max.binger.utils.TMDB
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class NetworkFactory {

    var networkRequestInstance: Retrofit? = null
    private val BASED_URL =TMDB.COMPLETE_URL

    fun getNetworkInstance(): Retrofit? {
        val okhttp = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            okhttp.addInterceptor(logging)
        }

        if (networkRequestInstance == null)
            networkRequestInstance = Retrofit.Builder()
                    .baseUrl(BASED_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    ).client(okhttp.build()).build()
        return networkRequestInstance
    }
}