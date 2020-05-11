package observer.v3_L

import java.lang.Exception

class ServerException(val code: Int, override val message: String) : Exception(message)

class UserException(override val message: String) : Exception(message)