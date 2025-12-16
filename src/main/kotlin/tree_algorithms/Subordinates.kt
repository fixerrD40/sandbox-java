fun main() {
    //val n = readln().toInt()
    //val directReports = readln().split(" ").map { it.toInt() }
    val n = 5
    val directReports = listOf(1, 1, 2, 3)

    val answer = solution2(n, directReports)

    println(answer.joinToString(" "))
}

private fun solution(numEmployees: Int, employeesDirectReports: List<Int>): List<Int> {
    val employees = (1..numEmployees).associateWith { Node(it) }

    employeesDirectReports.forEachIndexed { i, employeeDirectReport ->
        // employeesDirectReports starts with employee 2
        val employee = employees[i + 2]!!
        val directReport = employees[employeeDirectReport]!!
        directReport.subordinates.add(employee)
    }

    return employees.values.map { it.numSubordinates() }
}

private data class Node(val employee: Int) {
    val subordinates: MutableList<Node> = mutableListOf()

    fun numSubordinates(): Int {
        var sum = subordinates.size
        for (sub in subordinates) {
            sum += sub.numSubordinates()
        }
        return sum
    }
}

/**\
 * The main difference is in building subCount using dfs
 * luckily everyone is below the ceo so a single top-level dfs call
 */
private fun solution2(numEmployees: Int, employeesDirectReports: List<Int>): List<Int> {
    val tree = Array(numEmployees + 1) { mutableListOf<Int>() }
    val subCount = IntArray(numEmployees + 1)

    for (i in employeesDirectReports.indices) {
        val directReport = employeesDirectReports[i]
        // employeesDirectReports starts with employee 2
        val employee = i + 2
        tree[directReport].add(employee)
    }

    fun dfs(current: Int) {
        for (child in tree[current]) {
            dfs(child)
            subCount[current] += 1 + subCount[child]
        }
    }

    dfs(1)

    return subCount.slice(1..numEmployees)
}