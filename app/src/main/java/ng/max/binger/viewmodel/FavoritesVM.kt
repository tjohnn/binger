package ng.max.binger.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ng.max.binger.data.FavoriteShow
import ng.max.binger.data.TvShow
import ng.max.binger.repository.FavoriteShowRepo
import org.jetbrains.annotations.NotNull

class FavoritesVM(@NotNull application: Application) :AndroidViewModel(application) {
    private var repository:FavoriteShowRepo? = null

    init {
        repository = FavoriteShowRepo(application)
    }

    fun getFavorites():LiveData<List<FavoriteShow>>{
        return repository!!.getFavorites()
    }

    fun deletefavorite(id:Int){
        repository!!.deleteFavorite(id)
    }
}