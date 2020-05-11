package observer.v2_O

interface Observer<T> {
    fun update(value: T)
}