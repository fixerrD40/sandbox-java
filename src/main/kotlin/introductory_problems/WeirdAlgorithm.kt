package org.example

fun main() {
    val n = readln().toInt()
    require(n in 1..1_000_000)

    var cur = n
    val result = StringBuilder()
    result.append(cur)

    while (cur != 1) {
        cur = if (cur % 2 == 0) cur / 2 else 3 * cur + 1
        result.append(" ").append(cur)
    }

    println(result)
}