package observer.v6_Db

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