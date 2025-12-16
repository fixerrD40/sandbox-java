fun main() {
//    val (n, m) = readln().split(" ").map { it.toInt() }
//    val blueprint = mutableListOf<String>()
//
//    for (i in 0..<n) {
//        blueprint.add(readln())
//    }

    val n = 5
    val m = 8
    val blueprint = listOf(
        "########",
        "#..#...#",
        "####.#.#",
        "#..#...#",
        "########"
    )

    val answer = solution(n, m, blueprint)

    println(answer)
}

fun solution(rows: Int, columns: Int, blueprint: List<String>): Int {
    var result = 0
    val visited = Array(rows) { BooleanArray(columns) { false } }
    for (y in 0 until rows) {
        for (x in 0 until columns) {
            if (!visited[y][x] && blueprint[y][x] == '.') {
                result++
                dfsIterative(rows, columns, blueprint, listOf(x, y), visited)
            }
        }
    }

    return result
}

fun dfs(rows: Int, columns: Int, blueprint: List<String>, start: List<Int>, visited: Array<BooleanArray>) {
    val (x, y) = start
    if (x < 0 || x == columns || y < 0 || y == rows) return
    if (visited[y][x]) return
    visited[y][x] = true
    if (blueprint[y][x] == '.') {
        dfs(rows, columns, blueprint, listOf(x, y + 1), visited)
        dfs(rows, columns, blueprint, listOf(x, y - 1), visited)
        dfs(rows, columns, blueprint, listOf(x + 1, y), visited)
        dfs(rows, columns, blueprint, listOf(x - 1, y), visited)
    }
}

fun dfsIterative(rows: Int, columns: Int, blueprint: List<String>, start: List<Int>, visited: Array<BooleanArray>) {
    val stack = ArrayDeque<List<Int>>()
    stack.addLast(start)

    while (stack.isNotEmpty()) {
        val (x, y) = stack.removeLast()

        if (x < 0 || x >= columns || y < 0 || y >= rows) continue
        if (visited[y][x]) continue
        if (blueprint[y][x] != '.') continue

        visited[y][x] = true

        // Add neighbors
        stack.addLast(listOf(x + 1, y))
        stack.addLast(listOf(x - 1, y))
        stack.addLast(listOf(x, y + 1))
        stack.addLast(listOf(x, y - 1))
    }
}