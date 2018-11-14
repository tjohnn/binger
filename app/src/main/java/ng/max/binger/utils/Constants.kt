package ng.max.binger.utils

object TMDB {
    // api key
    private const val API_KEY_VALUE = "b3949470a8e2eb48fdded7ea54ea27df"
    const val  DEFAULT_PAGE_SIZE = 20 // default page_size for TMDB pagination
    const val API_KEY = API_KEY_VALUE //"api_key=$API_KEY_VALUE"
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_VERSION = 3
    const val COMPLETE_URL = "$BASE_URL$API_VERSION/"
    const val ID_KEY = "SHOW_ID"
}