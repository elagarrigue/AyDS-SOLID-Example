package observer.v4_I

enum class Severity { LOW, HIGH, CRITIC, NONE }

sealed class EventError(open val message: String) {

    open val severity: Severity = Severity.LOW

    data class UserError(override val message: String) : EventError(message) {
        override val severity = Severity.HIGH
    }

    data class ServerError(override val message: String, val code: Int) : EventError(message) {
        override val severity = Severity.CRITIC
    }

    data class UnknownError(override val message: String, val timeStamp: Long) : EventError(message) {
        override val severity = Severity.NONE
    }
}