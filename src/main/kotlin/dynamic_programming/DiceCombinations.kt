package dynamic_programming

private const val MOD = 1_000_000_007

fun main() {
//    val n = readln().toInt()
    val n = 50

    val answer = solution(n)

    println(answer)
}

private fun solution(n: Int): Int {
    val dp = IntArray(n+1)

    dp[0] = 1

    for (i in 1..n) {
        for (j in 1..6) {
            if (i-j>=0) dp[i] = (dp[i] + dp[i - j]) % MOD
            else break
        }
    }

    return dp[n] % MOD
}