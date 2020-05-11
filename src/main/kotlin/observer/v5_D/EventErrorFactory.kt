package observer.v5_D

import java.lang.Exception

class EventErrorFactory(private val currentTimeWrapper: CurrentTimeWrapper) {

    fun get(exception: Exception) : EventError =
        when (exception) {
            is ServerException ->
                EventError.ServerError(exception.message, exception.code)
            is UserException ->
                EventError.UserError(exception.message)
            else ->
                EventError.UnknownError(
                    exception.message ?: exception::class.java.toString(),
                    currentTimeWrapper.getCurrentTimeMillis()
                )
        }
}