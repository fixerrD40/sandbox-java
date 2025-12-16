package other

import kotlin.math.abs

fun main() {
//    val n = readln().toInt()
//    val energies = readln().split(" ").map { it.toInt() }

    val n = 6
    val positions = listOf(30, 10, 60, 10, 60, 50)

    val answer = solution(n, positions)

    println(answer)
}

/**
 * recursive, not dp (memoization)
 * could memoize calls with shared (current, expent).
 */
private fun recursiveSolution(n: Int, positions: List<Int>, current: Int = 0, expent: Int = 0): Int {
    if (current==n-1) return expent
    val currentPosition = positions[current]
    if (current==n-2) return expent + abs(currentPosition - positions.last())
    val leftIndex = current+1
    val rightIndex = current+2

    val left = recursiveSolution(n, positions, leftIndex, expent + abs(currentPosition - positions[leftIndex]))
    val right = recursiveSolution(n, positions, rightIndex, expent + abs(currentPosition - positions[rightIndex]))

    return if (left<right) left
    else right
}

/**
 * iterative, dp (tabulation)
 * tabulates expent energy 0..i
 */
private fun iterativeSolution(n: Int, positions: List<Int>): Int {
    val dp = IntArray(n) { 0 }
    dp[1] = abs(positions[n-1] - positions[n-2])

    for (i in 2 until n) {
        val idx = n - 1 - i

        val a = if (idx==1) 0 else positions[idx]
        val b = positions[idx + 1]
        val c = positions[idx + 2]

        val leftEnergy  = abs(b - a) + dp[i - 1]
        val rightEnergy = abs(c - a) + dp[i - 2]

        dp[i] = minOf(leftEnergy, rightEnergy)
    }
    return dp.last()
}

/**
 * I identified greedy decision-making constituted a pitfall.
 *
 * I fallaciously thought to traverse [positions] backward in my naive attempt to achieve "lookahead".
 *
 * A recurrence is by definition a product of prior terms.
 *
 * recursion -> global
 * lookahead -> local
 */
private fun solution(n: Int, positions: List<Int>): Int {
    val dp = IntArray(n) { Int.MAX_VALUE }
    dp[0] = 0
    dp[1] = abs(positions[1] - positions[0])

    for (i in 2 until n) {
        val oneStep = dp[i-1] + abs(positions[i] - positions[i-1])
        val twoStep = dp[i-2] + abs(positions[i] - positions[i-2])
        dp[i] = minOf(oneStep, twoStep)
    }
    return dp[n-1]
}
