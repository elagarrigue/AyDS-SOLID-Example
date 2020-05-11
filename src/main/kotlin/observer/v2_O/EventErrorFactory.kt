package observer.v2_O

import java.lang.Exception

object EventErrorFactory {

    fun get(exception: Exception) : EventError =
        when (exception) {
            is ServerException ->
                EventError.ServerError(exception.message, exception.code)
            is UserException ->
                EventError.UserError(exception.message)
            else ->
                EventError.UnknownError(
                    exception.message ?: exception::class.java.toString(),
                    System.currentTimeMillis()
                )
        }
}