fun main() {
//    val (_, x) = readln().split(" ").map { it.toInt() }
//    val denoms = readln().split(" ").map { it.toInt() }
    val x = 11
    val denoms = listOf(1, 5, 7)

    val answer = solution2(x, denoms)

    println(answer)
}

private fun solution(x: Int, denoms: List<Int>): Int {
    val dp = IntArray(x + 1) { -1 }
    dp[0] = 0

    denoms.forEach { denom ->
        var steps = 0
        for (i in 0..x step denom) {
            dp[i] = steps
            for (j in 1..<denom) {
                val remainingSteps = dp[j]
                if (i + j <= x && remainingSteps != -1 && steps + remainingSteps < dp[i + j])
                    dp[i + j] = steps + remainingSteps
            }
            steps++
        }
    }

    return dp[x]
}

/**
 * This is the standard solution. I misimplemented it above in that the outer loop does not
 * need to visit all positions, only ones possible given our coin denominations. From there
 * all i coin..denom might utilize the coin in focus.
 */
private fun solution2(x: Int, denoms: List<Int>): Int {
    val INF = Int.MAX_VALUE / 2
    val dp = IntArray(x + 1) { INF }
    dp[0] = 0

    for (coin in denoms) {
        for (i in coin..x) {
            dp[i] = minOf(dp[i], dp[i - coin] + 1)
        }
    }

    return if (dp[x] >= INF) -1 else dp[x]
}