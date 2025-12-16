package other

import java.util.concurrent.ConcurrentHashMap

fun main() {
    val m = 3
    val n = 3
    val memo = ConcurrentHashMap<Pair<Int, Int>, Int>()
    val answer1 = solution1(m, n, memo)
    val answer2 = solution2(m, n)
    println(answer1)
    println(answer2)

    val o = 2
    val bonus1 = thirdDimension(m, n, 1)
    val bonus2 = thirdDimension(m, n, o)
    println(bonus1)
    println(bonus2)
}

private fun solution1(m: Int, n: Int, seen: MutableMap<Pair<Int, Int>, Int>): Int {

    if (m==1) return 1
    if (n==1) return 1

    val key = Pair(m, n)

    if (key !in seen) {
        seen[key] = solution1(m - 1, n, seen) + solution1(m, n - 1, seen)
    }

    return seen[key]!!
}

/**
 * Total moves: m + n - 2 = (m - 1) + (n - 1)
 * Right moves: m-1
 * Down moves: n-1
 * The binomial     (Total moves)
 * Coefficient is   (right moves or down moves)
 * "Of total moves, how many ways can you take the (right/down) moves"
 */
private fun solution2(m: Int, n: Int): Int {
    val factorials = mutableListOf(1)
    for (i in 1..(m + n - 2)) {
        factorials.add(factorials.last() * i)
    }

    return (factorials[m + n - 2] / (factorials[m - 1] * factorials[n - 1])).toInt()
}

private fun thirdDimension(m: Int, n: Int, o: Int): Int {
    val factorials = mutableListOf(1)
    for (i in 1..(m + n + o - 3)) {
        factorials.add(factorials.last() * i)
    }

    return factorials[m + n + o - 3] / (factorials[m - 1] * factorials[n - 1] * factorials[o - 1])
}