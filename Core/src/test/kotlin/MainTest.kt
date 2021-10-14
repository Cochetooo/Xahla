import utils.XH_LogLevel
import utils.XH_Logger
import utils.xh_ping_test
import utils.xh_tryCatch

fun main() {
    XH_Logger.logLevel = XH_LogLevel.ALL
    XH_Logger.log("${xh_ping_test("5.196.204.208")} ms")
}