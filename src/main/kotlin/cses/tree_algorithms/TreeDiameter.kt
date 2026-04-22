package cses.tree_algorithms

fun main() {
//    val n = readln().toInt()
//    val edges = mutableListOf<Pair<Int, Int>>()
//    for (i in 1..n) {
//        val (u, v) = readln().split(" ").map { it.toInt() }
//        edges.add(u to v)
//    }

    val n = 5
    val edges = mutableListOf<Pair<Int, Int>>(
        1 to 2,
        1 to 3,
        3 to 4,
        3 to 5
    )

    val answer = solution2(n, edges)

    println(answer)
}

private fun solution(n: Int, edges: List<Pair<Int, Int>>): Int {
    val adj = Array(n + 1) { mutableListOf<Int>() }
    for ((u, v) in edges) {
        adj[u].add(v)
        adj[v].add(u)
    }

    var visited = BooleanArray(n + 1)
    val stack = ArrayDeque<Pair<Int, Int>>()

    visited[1] = true

    adj[1].forEach { stack.add(it to 1) }

    var furthest = 0
    while (stack.isNotEmpty()) {
        val (u, parent) = stack.removeFirst()
        visited[u] = true
        furthest = u

        for (v in adj[u]) {
            if (v == parent) continue
            if (!visited[v]) stack.add(v to u)
        }
    }

    visited = BooleanArray(n + 1)
    visited[furthest] = true
    var work = adj[furthest].map { it to furthest }
    var distance = 0

    while (true) {
        if (work.isEmpty()) break
        distance++

        val newWork = mutableListOf<Pair<Int, Int>>()

        work.forEach { (u, parent) ->
            visited[u] = true

            for (v in adj[u]) {
                if (v == parent) continue
                if (!visited[v]) newWork.add(v to u)
            }
        }

        work = newWork
    }

    return distance
}

/**
 * ChatGpt polished version
 */
private fun solution2(n: Int, edges: List<Pair<Int, Int>>): Int {
    val adj = Array(n + 1) { mutableListOf<Int>() }
    for ((u, v) in edges) {
        adj[u].add(v)
        adj[v].add(u)
    }

    fun bfs(start: Int): Pair<Int, Int> {
        val visited = BooleanArray(n + 1)
        // I was really wanting to track distance in data. Shoulda trusted it
        val queue = ArrayDeque<Pair<Int, Int>>()

        // this is neat, better base case
        queue.add(start to 0)
        visited[start] = true

        var farthest = start
        var maxDist = 0

        while (queue.isNotEmpty()) {
            val (u, dist) = queue.removeFirst()
            if (dist > maxDist) {
                maxDist = dist
                farthest = u
            }
            for (v in adj[u]) {
                // visited is already tracking that parent
                if (!visited[v]) {
                    visited[v] = true
                    queue.add(v to dist + 1)
                }
            }
        }
        return farthest to maxDist
    }

    val (endpoint, _) = bfs(1)
    val (_, diameter) = bfs(endpoint)
    return diameter
}
