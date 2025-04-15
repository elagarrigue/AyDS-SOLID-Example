package observer.v6_Db

import java.lang.Exception

interface EventErrorFactory {
    fun get(exception: Exception) : EventError
}

class EventErrorFactoryImpl(
    private val currentTimeWrapper: CurrentTimeWrapper
) : EventErrorFactory{

    override fun get(exception: Exception) : EventError =
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