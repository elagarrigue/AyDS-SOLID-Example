package observer.v3_L

interface Observer<T> {
    fun update(value: T)
}