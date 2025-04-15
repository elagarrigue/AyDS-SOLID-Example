package observer.v6_Db

interface CurrentTimeWrapper {
    fun getCurrentTimeMillis(): Long
}

class CurrentTimeWrapperImpl : CurrentTimeWrapper {
    override fun getCurrentTimeMillis() = System.currentTimeMillis()
}