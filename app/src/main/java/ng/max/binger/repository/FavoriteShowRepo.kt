package ng.max.binger.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow

class FavoriteShowRepo(application: Application) {
    private var favorites: LiveData<List<FavoriteShow>>? = null
    private var db: AppDatabase? = null

    init {
        db = AppDatabase.getInstance(application)
        favorites = db!!.favoriteDao.getFavorites()
    }

    fun getFavorites(): LiveData<List<FavoriteShow>> {
        return favorites!!
    }

    fun setFavorites(favoriteShow: FavoriteShow) {
        db!!.favoriteDao.insertFavorite(favoriteShow)
    }

    fun deleteFavorite(id:Int){
        db!!.favoriteDao.deleteFavorite(id)
    }
}