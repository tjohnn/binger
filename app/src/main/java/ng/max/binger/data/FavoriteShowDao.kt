package ng.max.binger.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

// TODO: implement sql queries for Favorites below

@Dao
interface FavoriteShowDao {

    @Insert
    fun insertFavorite(favoriteShow: FavoriteShow)

    @Query("DELETE FROM favorite_show WHERE tv_show_id = :showId")
    fun deleteFavorite(showId: Int)

    @Query("UPDATE favorite_show SET latest_season = :currentSeason, latest_episode = :currentEpisode WHERE tv_show_id = :showId")
    fun updateMovieDetail(showId: Int, currentSeason: Int, currentEpisode: Int)

    @Update
    fun updateMovie(favoriteShow: FavoriteShow)

    @Query("SELECT * FROM favorite_show")
    fun getFavorites(): Flowable<List<FavoriteShow>>
}