package observer.v0

import java.lang.Exception

class ErrorSubject {

    private val observers: MutableList<Observer<EventError>> = ArrayList()

    fun subscribe(observer: Observer<EventError>) {
        observers.add(observer)
    }

    fun unSubscribe(observer: Observer<EventError>) {
        observers.remove(observer)
    }

    fun notify(exception: Exception) {
        val eventValue: EventError =
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

        notifyAll(eventValue)
    }
    private fun notifyAll(event: EventError) {
        observers.forEach {
            it.update(event)
        }
    }
}