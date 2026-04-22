package cses.range_queries

fun main() {
//    val (n, q) = readln().split(" ").map { it.toInt() }
//    val numbers = readln().split(" ").map { it.toLong() }.toMutableList()
//
//    val queries: MutableList<List<Int>> = mutableListOf()
//    for (i in 1..q) {
//        queries.add(readln().split(" ").map { it.toInt() })
//    }

    val n = 8
    val q = 4
    val numbers = mutableListOf(3L, 2L, 4L, 5L, 1L, 1L, 5L, 3L)

    val queries = listOf(
        listOf(2, 1, 4),
        listOf(2, 5, 6),
        listOf(1, 3, 1),
        listOf(2, 1, 4)
    )

    val answer = solutionFenwick(n, numbers, queries)

    answer.forEach { println(it) }
}

/**
 * This worked in the static approach, init O(N) and query O(1)
 * but updates take O(N) which can be improved
 */
fun solution(n: Int, numbers: List<Int>, queries: List<List<Int>>): List<Int> {
    val prefix = IntArray(n+1)
    val result = mutableListOf<Int>()

    numbers.forEachIndexed { i, number ->
        prefix[i+1] = prefix[i] + number
    }

    queries.forEach { query ->
        when (query.first()) {
            1 -> {
                val index = query[1]
                val from = numbers[index-1]
                val to = query[2]
                val difference = from - to
                for (i in index..n) {
                    prefix[i] = prefix[i] - difference
                }
            }
            2 -> {
                val lower = query[1] - 1
                val upper = query[2]
                result.add(prefix[upper] - prefix[lower])
            }
        }
    }

    return result
}

/**
 * Fenwick tree, init O(NlogN), query O(logN), update O(logN)
 */
fun solutionFenwick(n: Int, numbers: MutableList<Long>, queries: List<List<Int>>): List<Long> {
    val fenwick = FenwickTree(n)
    val result = mutableListOf<Long>()

    numbers.forEachIndexed { i, number ->
        fenwick.update(i + 1, number)
    }

    queries.forEach { query ->
        when (query.first()) {
            1 -> {
                val index = query[1]
                val update = query[2].toLong()
                val delta = update - numbers[index - 1]
                numbers[index - 1] = update
                fenwick.update(index, delta)
            }
            2 -> {
                result.add(fenwick.rangeQuery(query[1], query[2]))
            }
        }
    }

    return result
}

// --- Fenwick Tree Implementation ---
class FenwickTree(size: Int) {
    private val tree = LongArray(size + 1)

    fun update(i: Int, delta: Long) {
        var index = i
        while (index < tree.size) {
            tree[index] += delta
            index += index and -index
        }
    }

    fun query(i: Int): Long {
        var index = i
        var sum = 0L
        while (index > 0) {
            sum += tree[index]
            index -= index and -index
        }
        return sum
    }

    fun rangeQuery(lower: Int, upper: Int): Long = query(upper) - query(lower - 1)
}
