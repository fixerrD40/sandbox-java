package other

fun main() {
    val obstacles = mutableListOf(mutableListOf(1, 1))
    val teleports = mutableListOf(mutableListOf(1, 0, 0, 0))
    val answer = solution(3, 4, obstacles, teleports)
    println(answer)
}

private fun solution(
    n: Int,
    m: Int,
    obstacles: MutableList<MutableList<Int>>,
    teleports: MutableList<MutableList<Int>>
): Int {
    var curN = 0
    var curM = 0
    val visited: MutableList<List<Int>> = mutableListOf()
    var result = 0

    while (curN != n - 1 || curM != m - 1) {
        val position = listOf(curN, curM)
        if (visited.contains(position)) return -2
        visited.add(position)

        val nextN = curN + 1
        val nextPosition = mutableListOf(nextN, curM)
        if (obstacles.contains(nextPosition) || nextN == n) {
            val nextM = curM + 1
            val backupNextPosition = mutableListOf(curN, nextM)

            if (obstacles.contains(backupNextPosition) || nextM == m) return -1
            curM = nextM
            result++
        } else {
            curN = nextN
            teleports.forEach { teleport ->
                if (teleport.subList(0, 2) == nextPosition) {
                    curN = teleport[2]
                    curM = teleport[3]
                }
            }
            result++
        }
    }
    return result
}