package observer.v3_L

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Test {

    val operationA: (eventError: EventError) -> Unit = mockk()

    inner class Consumer(private val channel: ErrorSubject) {
        private val observer = object : Observer<EventError> {
            override fun update(value: EventError) {
                operationA(value)
            }
        }

        fun startObserving() {
            channel.subscribe(observer)
        }

        fun stopObserving() {
            channel.unSubscribe(observer)
        }
    }

    inner class Producer(private val channel: ErrorSubject) {
        fun produce(value: Exception) {
            channel.notify(value)
        }
    }

    @BeforeEach
    fun setup() {
        every { operationA(any()) } returns Unit
    }

    private val channel = ErrorSubject()
    private val client = Consumer(channel)
    private val producer = Producer(channel)

    private val serverException = ServerException(404, "not found")
    private val userException = UserException("missing id")
    private val unknownException = Exception("unknown")

    @Test
    fun `given producer produces values, client should be notified only when subscribed`() {
        client.startObserving()
        producer.produce(serverException)
        client.stopObserving()
        producer.produce(serverException)

        verify(exactly = 1) { operationA(
            EventError.ServerError("not found", 404)
        ) }
    }

    @Test
    fun `given producer produces a server error event, client should be notified`() {
        client.startObserving()
        producer.produce(serverException)
        producer.produce(serverException)

        verify(exactly = 2) { operationA(
            EventError.ServerError("not found", 404)
        ) }
    }

    @Test
    fun `given producer produces a user error event, client should be notified`() {
        client.startObserving()
        producer.produce(userException)

        verify { operationA(
            EventError.UserError("missing id")
        ) }
    }

    @Test
    fun `given producer produces an unknown error event, client should be notified`() {
        client.startObserving()
        producer.produce(unknownException)

        verify { operationA(
            EventError.UnknownError("unknown", System.currentTimeMillis())
        ) }
    }
}