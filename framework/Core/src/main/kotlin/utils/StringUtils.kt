/** String Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
*/
package utils

/**
 *
 */
enum class CryptFormat {
    MD5, SHA128
}

/**
 * Encrypt a character sequence depending on a format.
 * (Accepted: MD5, SHA128)
 */
fun encryptTo(format: CryptFormat): String {
    TODO()
}

/**
 * Capitalize the first word of the character sequence.
 */
fun String.ucfirst(): String {
    return this.substring(0, 1).uppercase() + this.substring(1, this.length - 1);
}