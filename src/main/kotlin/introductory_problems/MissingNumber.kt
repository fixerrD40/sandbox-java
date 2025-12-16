import kotlin.math.ceil

fun main() {
//    val n = readln().toInt()
//    val numbers = readln().split(" ").map { it.toInt() }
    val n = 5
    val numbers = listOf(5, 2, 1, 3)

    val answer = solution(n, numbers)

    println(answer)
}

private fun solution(n: Int, numbers: List<Int>): Int {
    val expected = if (n % 2 == 1) {
        val median = ceil(n / 2.0).toInt()
        median * (n + 1) - median
    } else n / 2 * (n + 1)

    return expected - numbers.sum()
}