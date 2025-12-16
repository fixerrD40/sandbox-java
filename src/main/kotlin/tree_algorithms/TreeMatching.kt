fun main() {
//    val n = readln().toInt()
//    val edges = mutableListOf<Pair<Int, Int>>()
//    for (i in 1..n) {
//        val (from, to) = readln().split(" ").map { it.toInt() }
//        edges.add(from to to)
//    }

    val n = 5
    val edges = listOf(
        1 to 2,
        1 to 3,
        3 to 4,
        3 to 5
    )

    val answer = solution(n, edges)

    println(answer)
}

/**
 * adjacency grid condenses edges into connectivity
 * post-order traversal: process a node after all its children
 * by performing depth first search, we can construct the post-order traversal
 * and post-order traversal ensures processing of _leaves first_ from which optimal matches will originate
 * the recurrence is the idea that for an edge (parent, u) twofold:
 * omitting the edge (sumWithout) allows selection childrens' own bestWith/sumWithout--Sigma max(dp[child][0], dp[child][1])
 * including the edge (bestWith) means accepting all children's sumWithout--1 + Sigma dp[child][0]
 *
 * cleverly 1 + Sigma dp[child][0] is calculated 1 + dp[child][0] + (sumWithout - maxOf(dp[child][0], dp[child][1]))
 *   - sumWithout consists of all child edge omitted
 *   - bestWith consists of all but the least costly additional child edge omitted
 *   - so sumWithout can be translated to Sigma dp[child][0] by accepting largest
 *   - childSumWithout + (sumWithout - maxOf(childSumWithout, childSumWith)
 */
private fun solution(n: Int, edges: List<Pair<Int, Int>>): Int {
    val adj = Array(n + 1) { mutableListOf<Int>() }
    for ((u, v) in edges) {
        adj[u].add(v)
        adj[v].add(u)
    }

    val dp = Array(n + 1) { IntArray(2) }
    val stack = ArrayDeque<Pair<Int, Int>>()
    val visited = BooleanArray(n + 1)
    val postOrder = mutableListOf<Pair<Int, Int>>()

    stack.add(1 to 0)
    visited[1] = true

    // Iterative DFS to get post-order traversal
    while (stack.isNotEmpty()) {
        val (u, parent) = stack.removeLast()
        postOrder.add(u to parent)
        for (v in adj[u]) {
            if (!visited[v]) {
                visited[v] = true
                stack.add(v to u)
            }
        }
    }

    // Process nodes in post-order
    for ((u, parent) in postOrder.asReversed()) {
        var sumWithout = 0
        for (v in adj[u]) {
            if (v == parent) continue
            sumWithout += maxOf(dp[v][0], dp[v][1])
        }

        dp[u][0] = sumWithout

        var bestWith = 0
        for (v in adj[u]) {
            if (v == parent) continue
            val value = 1 + dp[v][0] + (sumWithout - maxOf(dp[v][0], dp[v][1]))
            if (value > bestWith) bestWith = value
        }

        dp[u][1] = bestWith
    }

    return maxOf(dp[1][0], dp[1][1])
}