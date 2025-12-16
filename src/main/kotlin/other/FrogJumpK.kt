package other

import kotlin.math.abs

fun main() {
//    val n = readln().toInt()
//    val k = readln().toInt()
//    val positions = readln().split(" ").map { it.toInt() }

    val n = 6
    val k = 3
    val positions = listOf(3, 100, 4, 200, 5, 300)

    val answer = iterativeSolution(n, k, positions)

    println(answer)
}

private fun iterativeSolution(n: Int, k: Int, positions: List<Int>): Int {
    val dp = IntArray(n) { -1 }
    dp[0] = 0

    for (i in 1 until n) {
        val inner = minOf(i, k)
        var best = Int.MAX_VALUE

        for (jump in 1..inner) {
            val prev = i - jump
            val cost = dp[prev] + abs(positions[i] - positions[prev])
            best = minOf(best, cost)
        }

        dp[i] = best
    }

    return dp[n-1]
}

private fun recursiveSolution(n: Int, k: Int, heights: List<Int>): Int {
    val dp = IntArray(n) { -1 }

    fun solve(distance: Int): Int {
        if (distance == 0) return 0
        if (dp[distance] != -1) return dp[distance]

        var best = Int.MAX_VALUE
        for (jump in 1..minOf(distance, k)) {
            val prev = distance - jump
            val cost = solve(prev) + abs(heights[distance] - heights[prev])
            best = minOf(best, cost)
        }

        dp[distance] = best
        return best
    }

    // traverse positions
    return solve(n - 1)
}