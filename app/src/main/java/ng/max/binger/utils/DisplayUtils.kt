package ng.max.binger.utils

import ng.max.binger.utils.TMDB.API_KEY
import java.util.*

object DisplayUtils {

    // function to generate image url for tv_show's poster images
    fun getImageUrl(imagePath: String?, imageSize: String = "w500"): String? {
        val imageBaseUrl = "https://image.tmdb.org/t/p"
        return if (imagePath == null) null
        else "$imageBaseUrl/$imageSize$imagePath?$API_KEY"
    }

    // function to get year value of the images
    fun getYear(date: Date?): String {
        return if (date == null) ""
        else {
            val c = Calendar.getInstance()
            c.time = date
            val year = c.get(Calendar.YEAR)
            "$year"
        }
    }
}