package ng.max.binger.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import retrofit2.http.DELETE

// TODO: implement sql queries for Favorites below

@Dao
interface FavoriteShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteShow: FavoriteShow)

    @Query("DELETE FROM favorite_show WHERE id =:id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM favorite_show")
    fun getFavorites(): LiveData<List<FavoriteShow>>

    @Query("SELECT * FROM favorite_show")
    fun getFavoritesNoLiveData(): List<FavoriteShow>
}