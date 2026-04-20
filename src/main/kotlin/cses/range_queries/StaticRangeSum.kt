fun main() {
//    val (n, q) = readln().split(" ").map { it.toInt() }
//    val numbers = readln().split(" ").map { it.toInt() }
//
//    val queries: MutableList<List<Int>> = mutableListOf()
//    for (i in 1..q) {
//        queries.add(readln().split(" ").map { it.toInt() })
//    }

    val n = 8
    val q = 4
    val numbers = listOf(3, 2, 4, 5, 1, 1, 5, 3)

    val queries = listOf(
        listOf(2, 4),
        listOf(5, 6),
        listOf(1, 8),
        listOf(3, 3)
    )

    val answer = solution(n, numbers, q, queries)

    answer.forEach { println(it) }
}

fun solution(n: Int, numbers: List<Int>, q: Int, queries: List<List<Int>>): List<Int> {
    val prefix = IntArray(n + 1)
    val result = IntArray(q)
    numbers.forEachIndexed { i, number ->
        prefix[i + 1] = prefix[i] + number
    }

    queries.forEachIndexed { i, query ->
        // Lower inclusive
        val lower = query.first() - 1
        val upper = query.last()
        result[i] = prefix[upper] - prefix[lower]
    }

    return result.toList()
}