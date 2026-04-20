package introductory_problems

fun main() {
    val k = 8

    val answer = solution(k)
    answer.forEach { println(it) }
}

fun solution(k: Int): List<Long> {
    val result = mutableListOf<Long>()
    for (i in 1..k) {
        val positions = i * i
        val combinationsPositions = positions * (positions - 1) / 2
        var attackPositions = 0L

        if (i > 2) {
            // vertical and horizontal
            val knightBoxes = 2 * ((i - 2) * (i - 1))
            attackPositions = knightBoxes * 2L
        }

        result.add(combinationsPositions - attackPositions)
    }
    return result
}