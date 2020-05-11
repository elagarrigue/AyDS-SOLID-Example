package observer.v5_D

interface CurrentTimeWrapper {
    fun getCurrentTimeMillis(): Long
}

class CurrentTimeWrapperImpl : CurrentTimeWrapper {
    override fun getCurrentTimeMillis() = System.currentTimeMillis()
}