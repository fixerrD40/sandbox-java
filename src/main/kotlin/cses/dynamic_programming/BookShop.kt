package cses.dynamic_programming

fun main() {
    val n = 4
    val x = 10

    val prices = listOf(4, 8, 5, 3)
    val pages = listOf(5, 12, 8, 1)

    val answer = solution(n, x, prices, pages)

    println(answer)
}

fun solution(numBooks: Int, budget: Int, booksPrices: List<Int>, booksPages: List<Int>): Int {
    val dp = IntArray(budget + 1)

    for (i in 0 until numBooks) {
        val price = booksPrices[i]
        val page = booksPages[i]

        // Go backward to ensure 0/1 Knapsack (each book used at most once)
        for (remainingBudget in budget downTo price) {
            dp[remainingBudget] = maxOf(dp[remainingBudget], dp[remainingBudget - price] + page)
        }
    }
    return dp[budget]
}
