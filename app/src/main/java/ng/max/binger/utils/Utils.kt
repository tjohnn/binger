package ng.max.binger.utils

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object Utils {

    fun processNetworkError(throwable: Throwable): String? {
        return when (throwable) {
            is HttpException -> "Server error!! Please try later."
            is SocketTimeoutException -> "Network timeout! Ensure a better connection and retry."
            is IOException -> "Network error. Ensure you are connected to internet"
            else -> throwable.message
        }
    }

}
