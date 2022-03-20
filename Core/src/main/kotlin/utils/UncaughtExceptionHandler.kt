package utils

class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (e === null)
            logger().throwException("An unexpected error has occured.", Exception())

        if (e !is Exception)
            e.printStackTrace()

        logger().throwException(e?.message ?: "", (e ?: RuntimeException()) as Exception)
    }
}