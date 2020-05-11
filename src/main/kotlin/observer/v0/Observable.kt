package observer.v0

interface Observer<T> {
    fun update(value: T)
}