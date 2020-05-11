package observer.v4_I

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Ignore
import org.junit.Test

class Test {

    val operationA: (eventError: EventError) -> Unit = mockk()

    inner class Consumer(private val channel: Observable<EventError>) {
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

    inner class Producer(private val channel: Publisher<Exception>) {
        fun produce(value: Exception) {
            channel.notify(value)
        }
    }

    @Test
    fun `given producer produces values, client should be notified only when subscribed`() {
        val channel = Subject()
        val client = Consumer(channel)
        val producer = Producer(channel)

        val exception = ServerException(404, "not found")
        val serverError = EventError.ServerError("not found", 404)

        every { operationA(serverError) } returns Unit

        client.startObserving()
        producer.produce(exception)
        client.stopObserving()
        producer.produce(exception)

        verify(exactly = 1) { operationA(serverError) }
    }

    @Test
    fun `given producer produces a server error event, client should be notified`() {
        val channel = Subject()
        val client = Consumer(channel)
        val producer = Producer(channel)

        val exception = ServerException(404, "not found")
        val serverError = EventError.ServerError("not found", 404)

        every { operationA(serverError) } returns Unit

        client.startObserving()
        producer.produce(exception)
        producer.produce(exception)

        verify(exactly = 2) { operationA(serverError) }
    }

    @Test
    fun `given producer produces a user error event, client should be notified`() {
        val channel = Subject()
        val client = Consumer(channel)
        val producer = Producer(channel)

        val exception = UserException("missing id")
        val userError = EventError.UserError("missing id")

        every { operationA(userError) } returns Unit

        client.startObserving()
        producer.produce(exception)

        verify { operationA(userError) }
    }

    @Test
    @Ignore
    fun `given producer produces an unknown error event, client should be notified`() {
        val channel = Subject()
        val client = Consumer(channel)
        val producer = Producer(channel)

        val exception = Exception("unknown")
        val unknownError = EventError.UnknownError("unknown", System.currentTimeMillis())

        every { operationA(unknownError) } returns Unit

        client.startObserving()
        producer.produce(exception)

        verify { operationA(unknownError) }
    }
}