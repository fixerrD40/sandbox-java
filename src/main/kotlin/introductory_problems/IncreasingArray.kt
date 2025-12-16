package introductory_problems

fun main() {
//    val n = readln().toInt()
//    val numbers = readln().split(" ").map { it.toInt() }

    val n = 5
    val numbers = listOf(3, 2, 5, 1, 7)

    val answer = solution(n, numbers)

    println(answer)
}

fun solution(n: Int, numbers: List<Int>): Int {
    var result = 0
    var previous = numbers.first()

    for (i in 1 until n) {
        val number = numbers[i]
        if (number < previous) {
            result += previous - number
        } else if (number > previous) previous = number
    }

    return result
}