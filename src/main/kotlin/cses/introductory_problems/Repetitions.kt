package cses.introductory_problems

fun main() {
//    val dnaStrand = readln()
    val dnaStrand = "ATTCGGGA"

    val answer = solution(dnaStrand)

    println(answer)
}

private fun solution(dnaStrand: String): Int {
    var previous: Char? = null
    var current = 0
    var max = 0

    dnaStrand.forEach { block ->
        if (previous != null && previous == block) current++
        else current = 1
        if (current > max) max = current
        previous = block
    }

    return max
}
