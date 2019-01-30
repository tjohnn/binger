package ng.max.binger.data

import com.google.gson.annotations.SerializedName
import java.util.*


class PagedList<T> {
    var page = 0

    var results: List<T> = mutableListOf()

    var totalResults = 0

    var totalPages = 0
}

open class TvShow() {

    constructor(fav: FavoriteShow): this() {
        id = fav.tvShowId

        name = fav.name
        summary = fav.summary
        firstAirDate = fav.firstAirDate
        rating = fav.rating
        posterPath = fav.posterPath
        isFavorite = true
    }

    var id = 0

    var name = ""

    @SerializedName("vote_average")
    var rating = 0f

    @SerializedName("overview")
    var summary = ""

    var voteCount = ""

    var posterPath = ""

    var backdropPath = ""

    var firstAirDate: Date? = Date()

    var isFavorite: Boolean = false
    override fun toString(): String {
        return "TvShow(id=$id, name='$name', rating=$rating, summary='$summary', voteCount='$voteCount', posterPath='$posterPath', backdropPath='$backdropPath', firstAirDate=$firstAirDate, isFavorite=$isFavorite)"
    }


}

open class Genre {
    var id = 0

    var name = ""
}

class Producer: Genre()


class ProductionCompany {
    var id = 0

    var name = ""

    var logoPath: String? = ""

    var originCountry = ""
}


class Season {
    var id = 0

    var name = ""

    var summary = ""

    var airDate = Date()

    var episodeCount = 0

    var posterPath = ""

    var seasonNumber = 0
}

class TvShowDetail: TvShow() {

    var producers = ArrayList<Producer>()

    var episodeCount = ArrayList<Int>()

    var genres = ArrayList<Genre>()

    var inProduction = false

    var numberOfEpisodes = 0

    var numberOfSeasons = 0

    var productionCompanies = ArrayList<ProductionCompany>()

    var seasons = ArrayList<Season>()

    var status = ""

}