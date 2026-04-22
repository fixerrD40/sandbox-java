package cses.sorting_and_searching

fun main() {
//    val (n, x) = readln().split(" ").map { it.toInt() }
//    val childrenWeights = readln().split(" ").map { it.toInt() }
    val n = 4
    val x = 10
    val childrenWeights = listOf(7, 2, 3, 9)

    val answer = solution(n, x, childrenWeights)

    println(answer)
}

private fun solution(n: Int, x: Int, weights: List<Int>): Int {
    val sortedWeights = weights.sorted()
    var i = 0
    var j = n-1
    var result = 0

    while (true) {
        if (i==j) {
            result++
            break
        }
        if (sortedWeights[j] + sortedWeights[i] <= x) i++
        j--
        result++
    }

    return result
}
