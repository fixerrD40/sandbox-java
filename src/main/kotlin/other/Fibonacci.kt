package other

import kotlin.math.ceil

fun main() {
    val steps = 4
    val answer1 = solution1(steps)
    val answer2 = solution2(steps)
    println(answer1)
    println(answer2)
}

/**
 * Total moves taken: (steps - i)
 * Two-step moves: i
 * One-step moves: steps - 2i
 * The binomial     (Moves on path with i 2-steps)
 * series is        (Ways to take the i 2-steps)
 * "For all numbers of two-step moves, how many ways can you take two-step and one-step moves"
 */
private fun solution1(steps: Int): Int {
    val factorials = mutableListOf(1)
    for (i in 1..steps) {
        factorials.add(factorials[i-1] * i)
    }

    var paths = 1
    for (i in 1..ceil(steps / 2.0).toInt()) {
        paths += factorials[steps-i]/(factorials[steps-2*i]*factorials[i])
    }

    return paths
}

private fun solution2(steps: Int): Int {
    var a = 0
    var b = 1
    var cur = -1

    for (i in 2..steps+1) {
        cur = a + b
        a = b
        b = cur
    }

    return b
}