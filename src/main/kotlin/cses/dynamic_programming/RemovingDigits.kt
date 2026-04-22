package cses.dynamic_programming

fun main() {
//    val n = readln().toInt()
    val n = 27

    val answer = solution(n)

    println(answer)
}

private fun solution(n: Int): Int {
    var curN = n
    val result = mutableListOf<Int>()

    while (curN > 0) {
        val digits = getDigits(10, curN)

        curN -= digits.max()
        result.add(curN)
    }

    return result.size
}

private fun getDigits(base: Int, number: Int): List<Int> {
    val digits = mutableListOf<Int>()
    var prevPlaceValue = 1
    var placeValue = base
    var prev = 0
    while (true) {
        val mod = number % placeValue
        val digit = (mod - prev) / prevPlaceValue
        digits.add(digit)
        if (placeValue > number) break
        prev = mod
        prevPlaceValue = placeValue
        placeValue *= base
    }
    return digits.reversed()
}
