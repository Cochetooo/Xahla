import utils.XH_Logger.eLog
import utils.XH_Logger.log
import utils.xh_tryCatch
import java.nio.file.Paths

fun main() {
    xh_tryCatch {
        val x = 5 / 0
    }
}