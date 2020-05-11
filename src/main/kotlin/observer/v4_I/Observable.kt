package observer.v4_I

interface Observer<T> {
    fun update(value: T)
}

interface Observable<T> {
    fun subscribe(observer: Observer<T>)
    fun unSubscribe(observer: Observer<T>)
}

interface Publisher<T> {
    fun notify(value: T)
}