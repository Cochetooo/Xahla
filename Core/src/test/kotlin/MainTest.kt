import utils.addSlashes
import utils.ucwords

fun main() {
    val text = "\"hello world\'!"
    println(text)
    println(text.addSlashes())
    println(text.ucwords(" \r\n\t\""))
}