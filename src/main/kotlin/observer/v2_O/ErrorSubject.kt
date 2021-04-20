package observer.v2_O

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
        notifyAll(EventErrorFactory.get(exception))
    }

    private fun notifyAll(eventValue: EventError) {
        observers.forEach {
            it.update(eventValue)
        }
    }
}