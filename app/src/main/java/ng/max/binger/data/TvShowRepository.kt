package ng.max.binger.data


import io.reactivex.Completable
import ng.max.binger.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRepository @Inject constructor(
        private val apiService: ApiService,
        private val favoriteShowDao: FavoriteShowDao
) {

    fun getTvShows(page: Int)
            = apiService.loadTvShows(page)

    fun getTvShowsAiringToday(page: Int) = apiService.loadAiringToday(page)

    fun getShowById(movieId: Int) = apiService.getShowById(movieId)

    fun addFavoriteShow(favoriteShow: FavoriteShow) =
            Completable.fromAction{favoriteShowDao.insertFavorite(favoriteShow)}

    fun updateMovieDetail(showId: Int, currentSeason: Int, currentEpisode: Int) =
            Completable.fromAction{favoriteShowDao.updateMovieDetail(showId, currentSeason, currentEpisode)}

    fun updateMovie(favoriteShow: FavoriteShow) =
            Completable.fromAction{favoriteShowDao.updateMovie(favoriteShow)}

    fun removeFavoriteShow(showId: Int) =
            Completable.fromAction{favoriteShowDao.deleteFavorite(showId)}

    fun getFavoriteShows() = favoriteShowDao.getFavorites()

    fun getFavoriteShowsSingle() = favoriteShowDao.getFavoriteShowsSingle()

}
