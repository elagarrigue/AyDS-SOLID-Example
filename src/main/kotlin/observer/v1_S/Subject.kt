package observer.v1_S

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
        notifyAll(getEventError(exception))
    }

    private fun getEventError(exception: Exception) =
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

    private fun notifyAll(eventValue: EventError) {
        observers.forEach {
            it.update(eventValue)
        }
    }
}