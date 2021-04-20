package observer.v4_I

import java.lang.Exception

class ErrorSubject : Observable<EventError>, Publisher<Exception> {

    private val observers: MutableList<Observer<EventError>> = ArrayList()

    override fun subscribe(observer: Observer<EventError>) {
        observers.add(observer)
    }

    override fun unSubscribe(observer: Observer<EventError>) {
        observers.remove(observer)
    }

    override fun notify(value: Exception) {
        notifyAll(EventErrorFactory.get(value))
    }

    private fun notifyAll(eventValue: EventError) {
        observers.forEach {
            it.update(eventValue)
        }
    }
}