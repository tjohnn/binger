package ng.max.binger.data

import android.arch.persistence.room.Dao

// TODO: implement sql queries for Favorites below

@Dao
interface FavoriteShowDao {

    fun insertFavorite(favoriteShow: FavoriteShow)

    fun deleteFavorite(id: Int)

    fun getFavorites(): List<FavoriteShow>
}