package observer.v0

import java.lang.Exception

class Subject {

    private val observers: MutableList<Observer<EventError>> = ArrayList()

    fun subscribe(observer: Observer<EventError>) {
        observers.add(observer)
    }

    fun unSubscribe(observer: Observer<EventError>) {
        observers.remove(observer)
    }

    fun notify(exception: Exception) {
        // map exception to event
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

        // notify all
        observers.forEach {
            it.update(eventValue)
        }
    }
}