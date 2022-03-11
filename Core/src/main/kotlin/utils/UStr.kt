/** String Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
*/
package utils

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

val HEX_ARRAY = "0123456789abcdef".toCharArray()

/**
 * Returns a string with backslashes added before characters that need to be escaped.
 */
fun String.addSlashes(): String {
    var result = ""
    for (i in 0 until this.length) {
        when (this[i]) {
            '\'', '\"', '\\', Character.MIN_VALUE -> result += '\\'
        }

        result += this[i]
    }

    return result
}

/**
 * Encrypt a character sequence with PBKDF2 crypting.
 */
fun String.encrypt(): String {
    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)

    val spec = PBEKeySpec(this.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

    return factory.generateSecret(spec).encoded.toString()
}

/**
 * Capitalize the first word of the character sequence.
 */
fun String.ucfirst(): String {
    return this.substring(0, 1).uppercase() + this.substring(1, this.length);
}

/**
 * Capitalize the first characters just after a delimiter. (Default " \t\r\n")
 */
fun String.ucwords(delimiter: String =" \t\r\n"): String {
    var result = ""
    var mustUpper = true

    for (i in 0 until this.length) {
        result += if(mustUpper) this[i].uppercase() else this[i]

        mustUpper = false
        for (j in delimiter.toCharArray()) {
            if (this[i] == j)
                mustUpper = true
        }
    }

    return result
}