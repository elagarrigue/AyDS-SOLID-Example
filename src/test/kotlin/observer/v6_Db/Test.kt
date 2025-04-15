package observer.v6_Db

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Test {

    private val operationA: (eventError: EventError) -> Unit = mockk()

    private val eventErrorFactory: EventErrorFactory = mockk()

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

    private val channel = ErrorSubject(eventErrorFactory)
    private val client = Consumer(channel)
    private val producer = Producer(channel)

    private val exception: Exception = mockk()
    private val eventError: EventError = mockk()

    @BeforeEach
    fun setup() {
        every { operationA(any()) } returns Unit
        every { eventErrorFactory.get(exception) } returns eventError
    }

    @Test
    fun `given producer produces values, client should be notified only when subscribed`() {
        client.startObserving()
        producer.produce(exception)
        client.stopObserving()
        producer.produce(exception)

        verify(exactly = 1) {
            operationA(eventError)
        }
    }

    @Test
    fun `given producer produces a server error event, client should be notified`() {
        client.startObserving()
        producer.produce(exception)
        producer.produce(exception)

        verify(exactly = 2) {
            operationA(eventError)
        }
    }
}