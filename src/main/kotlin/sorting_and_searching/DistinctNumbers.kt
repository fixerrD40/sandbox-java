package org.example

fun main() {
    readln()
    println(readln().split(" ").map { it.toInt() }.toSet())
}