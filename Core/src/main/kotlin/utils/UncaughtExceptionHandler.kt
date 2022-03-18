package utils

class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        logger().throwException(e?.message ?: "", (e ?: RuntimeException()) as Exception)
    }
}