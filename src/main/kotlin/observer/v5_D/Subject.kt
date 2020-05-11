package observer.v5_D

import java.lang.Exception

class Subject(private val eventErrorFactory: EventErrorFactory) : Observable<EventError>, Publisher<Exception> {

    private val observers: MutableList<Observer<EventError>> = ArrayList()

    override fun subscribe(observer: Observer<EventError>) {
        observers.add(observer)
    }

    override fun unSubscribe(observer: Observer<EventError>) {
        observers.remove(observer)
    }

    override fun notify(value: Exception) {
        notifyAll(eventErrorFactory.get(value))
    }

    private fun notifyAll(eventValue: EventError) {
        observers.forEach {
            it.update(eventValue)
        }
    }
}