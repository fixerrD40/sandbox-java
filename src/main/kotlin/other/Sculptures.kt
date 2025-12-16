package other

import kotlin.time.measureTime

fun main() {
    var result: MutableList<Int>?
    val time = measureTime {
        val heights = mutableListOf(1, 6, 2, 4, 6, 5)
        result = solution2(heights)
    }
    println("result: " + result.toString())
    println("time: $time")
}

private fun solution1(heights: MutableList<Int>): MutableList<Int> {
    while (true) {
        var bestIndex = -1
        var bestValue = Int.MIN_VALUE

        for (i in heights.indices) {
            val num = heights[i]
            if ((i == 0 || num > heights[i - 1]) && (i == heights.lastIndex || num > heights[i + 1])) {
                if (num > bestValue) {
                    bestValue = num
                    bestIndex = i
                }
            }
        }

        if (bestIndex == -1) return heights

        heights.removeAt(bestIndex)
        heights.add(bestValue)
    }
}

private fun solution2(heights: MutableList<Int>): MutableList<Int> {
    while (true) {
        var i = 0
        val highlights: MutableList<Pair<Int, Int>> = mutableListOf()

        while (heights.size > i) {
            val num = heights[i]
            if (i == 0 || num > heights[i - 1]) {
                if (i == heights.size - 1 || num > heights[i + 1]) {
                    highlights.add(i to num)
                }
            }
            i++
        }

        if (highlights.isEmpty()) return heights

        val selected = highlights.maxBy { it.second }
        heights.removeAt(selected.first)
        heights.add(selected.second)
    }
}