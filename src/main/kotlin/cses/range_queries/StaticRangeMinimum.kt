package cses.range_queries

import kotlin.math.min

fun main() {
//    val (n, q) = readln().split(" ").map { it.toInt() }
//    val numbers = readln().split(" ").map { it.toInt() }
//
//    val queries = mutableListOf<Pair<Int, Int>>()
//    for (i in 1..q) {
//        val (from, to) = readln().split(" ").map { it.toInt() }
//        queries.add(from to to)
//    }
    val n = 8
    val q = 4
    val numbers = listOf(3, 2, 4, 5, 1, 1, 5, 3)
    val queries = listOf(
        2 to 4,
        5 to 6,
        1 to 8,
        3 to 3
    )

    val answer = solution1(n, q, numbers, queries)

    answer.forEach {
        println(it)
    }
}

/**
 * My naive solution inspired by the Fenwick
 * Precompute and space fine, queries log(n)--too slow
 *
 * returning to this, this is almost the segment tree optimal for the dynamic version,
 * but stored in 2 dimensions nxlogn
 * rather than 1 dimension 2n
 */
private fun solution1(n: Int, q: Int, numbers: List<Int>, queries: List<Pair<Int, Int>>): List<Int> {
    val minimums: MutableList<List<Int>> = mutableListOf()
    minimums.add(numbers)

    for (i in 1..msbInt(n)) {
        val prior = minimums.last()
        val current = mutableListOf<Int>()
        for (j in 0..<prior.size / 2) {
            current.add(min(prior[j * 2], prior[j * 2 + 1]))
        }
        minimums.add(current)
    }

    val result = mutableListOf<Int>()

    queries.forEach { (from, to) ->
        var l = from - 1
        val r = to - 1
        var minValue = Int.MAX_VALUE
        var remaining = r - l + 1

        var level = minimums.size - 1
        while (remaining > 0) {
            while ((1 shl level) > remaining) level--

            val index = l / (1 shl level)
            minValue = minOf(minValue, minimums[level][index])

            l += 1 shl level
            remaining = r - l + 1
        }

        result.add(minValue)
    }

    return result
}

/**
 * Employ sparse tree structure of overlapping ranges.
 * Most significant bit of query range can capture less significant bits with two overlapping ranges
 * ie range [1,7] min([1,4], [3,7])
 * cleverly making query o(1)
 */
private fun solution2(n: Int, q: Int, numbers: List<Int>, queries: List<Pair<Int, Int>>): List<Int> {
    val minimums: MutableList<List<Int>> = mutableListOf()
    minimums.add(numbers)

    for (i in 1..msbInt(n)) {
        val prior = minimums[i - 1]
        val current = mutableListOf<Int>()
        for (j in 0..n - (1 shl i)) {
            current.add(min(prior[j], prior[j + (1 shl (i - 1))]))
        }
        minimums.add(current)
    }

    val result = mutableListOf<Int>()

    queries.forEach { (from, to) ->
        val len = to - from + 1
        val k = msbInt(len)
        result.add(minOf(minimums[k][from - 1], minimums[k][to - (1 shl k)]))
    }

    return result
}

private fun msbInt(x: Int): Int = 31 - Integer.numberOfLeadingZeros(x)
