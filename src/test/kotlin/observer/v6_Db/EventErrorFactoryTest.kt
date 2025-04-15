package observer.v6_Db

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class EventErrorFactoryTest {

    private val currentTimeWrapper: CurrentTimeWrapper = mockk()
    private val eventErrorFactory = EventErrorFactoryImpl(currentTimeWrapper)

    private val serverException = ServerException(404, "not found")
    private val userException = UserException("missing id")
    private val unknownException = Exception("unknown")


    @Test
    fun `given server exception should map to Server Error`() {
        val result = eventErrorFactory.get(serverException)

        assert(result == EventError.ServerError("not found", 404))
    }

    @Test
    fun `given user exception should map to User Error`() {
        val result = eventErrorFactory.get(userException)

        assert(result == EventError.UserError("missing id"))
    }

    @Test
    fun `given producer produces an unknown error event, client should be notified`() {
        every { currentTimeWrapper.getCurrentTimeMillis() } returns 1000

        val result = eventErrorFactory.get(unknownException)

        assert(result == EventError.UnknownError("unknown", 1000))
    }
}