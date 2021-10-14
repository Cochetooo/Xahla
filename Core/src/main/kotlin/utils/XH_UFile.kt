/** File I/O Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import java.io.File

/**
 * @return The full content of a file as a String
 */
fun xh_file_read(path: String): String
    = File(path).bufferedReader().use { it.readText() }

/**
 * Write and create if needed to a file
 */
fun xh_file_write(path: String, text: String)
    = File(path).writeText(text)

/**
 * Append content to an existing file
 */
fun xh_file_append(path: String, text: String)
    = File(path).appendText(text)

/**
 * Delete a file if it exists
 * @return False if the file hasn't been found
 */
fun xh_file_delete(path: String): Boolean {
    val file = File(path)
    return if (file.exists()) {
        file.delete()
        true
    } else
        false
}