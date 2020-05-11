package observer.v1_S

interface Observer<T> {
    fun update(value: T)
}