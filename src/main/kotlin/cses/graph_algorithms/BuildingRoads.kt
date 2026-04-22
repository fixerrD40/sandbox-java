package cses.graph_algorithms

fun main() {
//    val (n, m) = readln().split(" ").map { it.toInt() }
//    val roads = mutableListOf<Pair<Int, Int>>()
//    repeat(m) {
//        val (u, v) = readln().split(" ").map { it.toInt() }
//        roads.add(u to v)
//    }
    val n = 4
    val roads = mutableListOf(
        1 to 2,
        3 to 4
    )

    solution3(n, roads)
}

private fun solution(numCities: Int, roads: List<Pair<Int, Int>>) {
    val grid = Array(numCities+1) { mutableSetOf<Int>() }
    val visited = mutableSetOf<Int>()

    for (i in 1..numCities) {
        if (visited.contains(i)) continue
        visited.add(i)
        val stack = ArrayDeque<Int>()
        roads.forEach {
            if (it.first == i) {
                stack.add(it.second)
                visited.add(it.second)
            }
            if (it.second == i) {
                stack.add(it.first)
                visited.add(it.first)
            }
        }

        while (stack.isNotEmpty()) {
            val connectedCity = stack.removeLast()
            grid[i].add(connectedCity)
            roads.forEach {
                if (it.first == connectedCity && !visited.contains(it.second)) {
                    stack.add(it.second)
                    visited.add(it.second)
                }
                if (it.second == connectedCity && !visited.contains(it.first)) {
                    stack.add(it.first)
                    visited.add(it.first)
                }
            }
        }
    }

    val segments = mutableListOf<Int>()
    grid.forEachIndexed { i, connections ->
        if (connections.isNotEmpty()) segments.add(i)
    }

    println(segments.size)
    for (i in 1..<segments.size) println("${segments[0]} ${segments[i]}")
}

/**
 * This is my solution cleaned up. I could improve by
 * replacing visited: Set<Int> -> BooleanArray
 * connectivity grid could be condensed down to just the ultimate list of components that materialized before
 *   - the key was creating intermediary adjacency list in place of the list of roads.
 */
private fun solution2(numCities: Int, roads: List<Pair<Int, Int>>) {
    // Build adjacency list
    val adj = Array(numCities + 1) { mutableListOf<Int>() }
    for ((u, v) in roads) {
        adj[u].add(v)
        adj[v].add(u)
    }

    val visited = BooleanArray(numCities + 1)
    val components = mutableListOf<List<Int>>()

    for (i in 1..numCities) {
        if (!visited[i]) {
            val comp = mutableListOf<Int>()
            val stack = ArrayDeque<Int>()
            stack.add(i)
            visited[i] = true

            while (stack.isNotEmpty()) {
                val u = stack.removeLast()
                comp.add(u)
                for (v in adj[u]) {
                    if (!visited[v]) {
                        visited[v] = true
                        stack.add(v)
                    }
                }
            }

            components.add(comp)
        }
    }

    // Output number of new roads needed
    println(components.size - 1)

    // Connect components sequentially
    for (i in 0 until components.size - 1) {
        println("${components[i].first()} ${components[i + 1].first()}")
    }
}

/**
 * solution2 TLE. Traversal is not actually necessary despite the graph algorithm theme and can lead to deep recursion
 * By employing Union Find, the paths are dynamically compressed making find operation much simpler.
 */
fun solution3(numCities: Int, roads: List<Pair<Int, Int>>) {
    val uf = UnionFind(numCities)

    for ((u, v) in roads) {
        uf.union(u, v)
    }

    val seen = BooleanArray(numCities + 1)
    val reps = mutableListOf<Int>()
    for (i in 1..numCities) {
        val root = uf.find(i)
        if (!seen[root]) {
            seen[root] = true
            reps.add(root)
        }
    }

    println(reps.size - 1)
    for (i in 0 until reps.size - 1) {
        println("${reps[i]} ${reps[i + 1]}")
    }
}

private class UnionFind(size: Int) {
    private val parent = IntArray(size + 1) { it }

    /**
     * Find root of a segment
     * the root of a segment points at itself
     */
    fun find(x: Int): Int {
        if (parent[x] != x) parent[x] = find(parent[x])
        return parent[x]
    }

    /**
     * Merge segments a and b by setting a's root's parent to b's root
     * the node at the root of a points to the node at the root of b,
     * effectively compressing b's path into a's
     */
    fun union(a: Int, b: Int) {
        val pa = find(a)
        val pb = find(b)
        if (pa != pb) parent[pa] = pb
    }
}
