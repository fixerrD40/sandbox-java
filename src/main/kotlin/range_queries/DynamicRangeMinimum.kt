import kotlin.math.min

fun main() {
//    val (n, q) = readln().split(" ").map { it.toInt() }
//    val numbers = readln().split(" ").map { it.toInt() }
//
//    val queries = mutableListOf<List<Int>>()
//    for (i in 1..q) {
//        val query = readln().split(" ").map { it.toInt() }
//        queries.add(query)
//    }
    val n = 8
    val q = 4
    val numbers = listOf(3, 2, 4, 5, 1, 1, 5, 3)
    val queries = listOf(
        listOf(2, 1, 4),
        listOf(2, 5, 6),
        listOf(1, 2, 3),
        listOf(2, 1 ,4)
    )

    val answer = solution2(n, q, numbers, queries)

    println(answer)
}

/**
 * Naively continued sparse table into dynamic version of the problem.
 * Updates are not competitive (nlogn). Why?
 * Recomputing overlapping segment values (rebuild) because data is not hierarchical
 * Ideally flatten the structure down to make updates logn.
 */
private fun solution1(n: Int, q: Int, numbers: List<Int>, queries: List<List<Int>>): List<Int> {
    val minimums: MutableList<MutableList<Int>> = mutableListOf()
    minimums.add(numbers.toMutableList())

    for (i in 1..msbInt(n)) {
        val prior = minimums[i-1]
        val current = mutableListOf<Int>()
        for (j in 0..n-(1 shl i)) {
            current.add(min(prior[j], prior[j + (1 shl (i - 1))]))
        }
        minimums.add(current)
    }

    val result = mutableListOf<Int>()

    queries.forEach { query ->
        val (op, a, b) = query

        when(op) {
            1 -> {
                val index = a - 1
                minimums[0][index] = b
                for (i in 1..msbInt(n)) {
                    val factor = 1 shl i
                    for (j in 0 until factor) {
                        if (j < minimums[i].size) minimums[i][j] = min(minimums[i-1][j], minimums[i-1][j+1])
                        else break
                    }
                }
            }
            2 -> {
                val len = b - a + 1
                val k = msbInt(len)
                result.add(minOf(minimums[k][a-1], minimums[k][b - (1 shl k)]))
            }
        }
    }

    return result
}

private fun msbInt(x: Int): Int = 31 - Integer.numberOfLeadingZeros(x)

/**
 * Segment Tree
 * build: O(nlogn)
 * update: O(logn)
 * query: O(logn)
 *
 * the tree is stored in array size 2n
 * contains values 2n-1
 * the last n values are the original numbers
 *
 */
fun solution2(n: Int, q: Int, numbers: List<Int>, queries: List<List<Int>>): List<Int> {
    val segmentTree = IntArray(2 * n) { Int.MAX_VALUE }

    for (i in 0 until n) segmentTree[n + i] = numbers[i]

    for (i in n - 1 downTo 1) {
        segmentTree[i] = minOf(segmentTree[i shl 1], segmentTree[i shl 1 or 1])
    }


    val result = mutableListOf<Int>()

    queries.forEach { query ->
        val (op, a, b) = query

        when (op) {
            1 -> {
                var index = a - 1 + n
                segmentTree[index] = b
                while (index > 1) {
                    index = index shr 1
                    segmentTree[index] = minOf(segmentTree[index * 2], segmentTree[index * 2 + 1])
                }
            }
            2 -> {
                var res = Int.MAX_VALUE
                var from = a - 1 + n
                var to = b - 1 + n
                while (from <= to) {
                    // rounding will eliminate my parent, check in min
                    if (from % 2 == 1) res = minOf(res, segmentTree[from++])
                    if (to % 2 == 0) res = minOf(res, segmentTree[to--])
                    from = from shr 1
                    to = to shr 1
                }
                result.add(res)
            }
        }
    }

    return result
}