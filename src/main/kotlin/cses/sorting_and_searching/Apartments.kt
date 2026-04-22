package cses.sorting_and_searching

fun main() {
//    val (n, m ,k) = readln().split(" ").map { it.toInt() }
//    val desiredApartments = readln().split(" ").map { it.toInt() }.sorted()
//    val apartments = readln().split(" ").map { it.toInt() }.sorted().toMutableList()
    val n = 4
    val m = 3
    val k = 5

    val desiredApartments = listOf(60, 45, 80, 60).sorted()
    val apartments = mutableListOf(30, 60, 75).sorted()

    val answer = if (n>m) solutionBinarySearch(n, k, apartments, desiredApartments)
    else solutionBinarySearch(m, k, desiredApartments, apartments)

    println(answer)
}

/**
 * Annoyingly enough my first solution was accepted after replacing the java translation's
 * Arrays.sort() call with Collection.sort().
 *
 * This solution appears slightly faster than the binary search one,
 * although I'm guessing either would be accepted after the massive performance impact of above.
 */
private fun solutionTwoPointers(numTenants: Int, numApartments: Int, tolerance: Int, desiredSizes: List<Int>, apartmentSizes: List<Int>): Int {
    var result = 0
    var i = 0
    var k = 0
    while (i < numTenants && k < numApartments) {
        if (apartmentSizes[k] >= desiredSizes[i] - tolerance && apartmentSizes[k] <= desiredSizes[i] + tolerance) {
            result++
            i++
            k++
        } else if (apartmentSizes[k] > desiredSizes[i] - tolerance) {
            i++
        } else {
            k++
        }
    }
    return result
}


private fun solutionBinarySearch(
    numLarger: Int,
    tolerance: Int,
    smaller: List<Int>,
    larger: List<Int>
): Int {
    var result = 0
    var apartmentStart = 0

    for (desired in smaller) {
        val lowerBound = desired - tolerance
        val upperBound = desired + tolerance

        if (apartmentStart >= numLarger || larger[numLarger - 1] < lowerBound) break

        if (larger[apartmentStart] > upperBound) continue

        val match = binarySearchMatch(larger, apartmentStart, numLarger - 1, lowerBound, upperBound)
        if (match != -1) {
            result++
            apartmentStart = match + 1
        }
    }

    return result
}

private fun binarySearchMatch(
    sorted: List<Int>,
    leftStart: Int,
    rightEnd: Int,
    lowerBound: Int,
    upperBound: Int
): Int {
    var left = leftStart
    var right = rightEnd
    var matchIndex = -1

    while (left <= right) {
        val mid = (left + right) / 2
        val apt = sorted[mid]

        when {
            apt < lowerBound -> left = mid + 1
            apt > upperBound -> right = mid - 1
            else -> {
                matchIndex = mid
                right = mid - 1
            }
        }
    }

    return matchIndex
}
