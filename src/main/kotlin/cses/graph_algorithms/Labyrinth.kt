package cses.graph_algorithms

fun main() {
//    val (n, m) = readln().split(" ").map { it.toInt() }
//    val board = mutableListOf<String>()
//    for (i in 1..n) {
//        board.add(readln())
//    }
    val n = 5
    val m = 8
    val board = mutableListOf(
        "########",
        "#.A#...#",
        "#.##.#B#",
        "#......#",
        "########"
    )


    val answer = solution(n, m, board)

    answer?.let {
        println("YES")
        println(answer.length)
        println(answer)
    } ?: println("NO")
}

private fun solution(n: Int, m: Int, board: MutableList<String>): String? {
    var start: Cell? = null
    var end: Cell? = null

    board.forEachIndexed { i, row ->
        if (row.contains('A')) start = Cell(row.indexOf('A'), i)
        if (row.contains('B')) end = Cell(row.indexOf('B'), i)
    }

    return if (start != null && end != null) {
        bfs(board.toList(), n, m, start!!, end!!)
//        dfs(board, n, m, start!!, end!!, "", mutableSetOf())
    } else {
        null
    }
}

private fun dfs(
    board: List<String>,
    n: Int,
    m: Int,
    curPosition: Cell,
    endPosition: Cell,
    path: String,
    seen: MutableSet<Cell>
): String? {
    if (curPosition == endPosition) return path
    if (!curPosition.isInBounds(n, m)) return null
    if (board[curPosition.y][curPosition.x] == '#') return null
    if (curPosition in seen) return null

    seen.add(curPosition)

    val paths = curPosition.neighbors().mapNotNull { (next, direction) ->
        dfs(board, n, m, next, endPosition, path + direction, seen)
    }

    return paths.minByOrNull { it.length }
}

private fun bfs(board: List<String>, n: Int, m: Int, start: Cell, end: Cell): String? {
    val visited = Array(n) { BooleanArray(m) }
    val parent = Array(n) { Array<Cell?>(m) { null } }
    val move = Array(n) { CharArray(m) }

    val queue = ArrayDeque<Cell>()
    queue.add(start)
    visited[start.y][start.x] = true

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        if (current == end) {
            val path = StringBuilder()
            var pos = end
            while (pos != start) {
                val direction = move[pos.y][pos.x]
                path.append(direction)
                pos = parent[pos.y][pos.x]!!
            }
            return path.reverse().toString()
        }

        for ((neighbor, dirChar) in current.neighbors()) {
            if (neighbor.isInBounds(n, m) &&
                board[neighbor.y][neighbor.x] != '#' &&
                !visited[neighbor.y][neighbor.x]
            ) {
                visited[neighbor.y][neighbor.x] = true
                parent[neighbor.y][neighbor.x] = current
                move[neighbor.y][neighbor.x] = dirChar
                queue.add(neighbor)
            }
        }
    }

    return null
}

private data class Cell(val x: Int, val y: Int) {
    companion object {
        val dx = intArrayOf(0, 0, -1, 1)
        val dy = intArrayOf(-1, 1, 0, 0)
        val dirs = charArrayOf('U', 'D', 'L', 'R')
    }

    fun neighbors(): List<Pair<Cell, Char>> {
        return dx.indices.map { i ->
            Cell(x + dx[i], y + dy[i]) to dirs[i]
        }
    }

    fun isInBounds(n: Int, m: Int): Boolean {
        return y in 0 until n && x in 0 until m
    }
}
