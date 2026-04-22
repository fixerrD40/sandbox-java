package cses.sorting_and_searching

fun main() {
    val n = readln().toInt()

    val ranges = MutableList(n) { i ->
        val (a, b) = readln().split(" ").map { it.toInt() }
        Range(a, b, i)
    }

    // --- Coordinate compression ---
    val ends = ranges.map { it.end }.distinct().sorted()
    val endToCompressed = ends.withIndex().associate { (i, v) -> v to i + 1 }
    ranges.forEach { it.compressedEnd = endToCompressed[it.end]!! }
    val maxCompressed = ends.size

    val contains = IntArray(n)
    val contained = IntArray(n)

    // --- Pass 1: Count how many ranges each range contains ---
    ranges.sortWith(compareBy({ it.start }, { -it.end }))
    val fenwick1 = FenwickTree(maxCompressed)

    for (r in ranges.asReversed()) {
        // Count how many ranges we've seen so far with end <= current end
        contains[r.index] = fenwick1.query(r.compressedEnd)
        fenwick1.update(r.compressedEnd, 1)
    }

    // --- Pass 2: Count how many ranges contain each range ---
    ranges.sortWith(compareBy({ it.start }, { it.end }))
    val fenwick2 = FenwickTree(maxCompressed)

    for (r in ranges) {
        contained[r.index] = fenwick2.query(maxCompressed) - fenwick2.query(r.compressedEnd - 1)
        fenwick2.update(r.compressedEnd, 1)
    }

    println(contains.joinToString(" "))
    println(contained.joinToString(" "))
}

data class Range(val start: Int, val end: Int, val index: Int, var compressedEnd: Int = 0)

// --- Fenwick Tree Implementation ---
class FenwickTree(size: Int) {
    private val tree = IntArray(size + 2) // +2 for safety

    fun update(i: Int, delta: Int) {
        var index = i
        while (index < tree.size) {
            tree[index] += delta
            index += index and -index
        }
    }

    fun query(i: Int): Int {
        var index = i
        var sum = 0
        while (index > 0) {
            sum += tree[index]
            index -= index and -index
        }
        return sum
    }
}
