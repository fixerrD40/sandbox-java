package other

import java.util.TreeMap

fun main() {
    val queries = listOf(
        listOf("Checkout", "Romance", "1"),

        listOf("Acquire", "Western", "5", "2"),
        listOf("Acquire", "Western", "3", "1"),

        listOf("Reclassify", "Western", "5", "2", "1"),
        listOf("Reclassify", "Western", "8", "6", "1"),
        listOf("Reclassify", "Western", "3", "7", "5"),

        listOf("Acquire", "Western", "2", "4"),
        listOf("Acquire", "Western", "2", "1"),
        listOf("Acquire", "Western", "10", "3"),

        listOf("Checkout", "Western", "6"),

        listOf("Acquire", "SciFi", "8", "2"),
        listOf("Acquire", "SciFi", "1", "3"),

        listOf("Checkout", "SciFi", "5"),

        listOf("Reclassify", "Western", "2", "9", "2"),
        listOf("Reclassify", "Western", "10", "1", "3"),

        listOf("Acquire", "Mystery", "4", "20"),
        listOf("Checkout", "Mystery", "50"),

        listOf("Reclassify", "Western", "9", "1", "1"),

        listOf("Checkout", "SciFi", "1")
    )

    val GENRES = setOf("Romance", "Western", "SciFi", "Mystery")

    val state = solution(queries)

    println("Library total value: ${state.getLibraryMonetaryValue()}\n")

    GENRES.forEach {
        println("Genre '$it' value = ${state.getGenreMonetaryValue(it)}")
    }
}

private fun solution(queries: List<List<String>>): BookStore {
    val store = BookStore()

    for (q in queries) {
        when (q[0]) {

            "Acquire" -> {
                val genre = q[1]
                val value = q[2].toInt()
                val count = q[3].toInt()
                store.acquire(genre, value, count)
            }

            "Checkout" -> {
                val genre = q[1]
                val count = q[2].toInt()
                val result = store.checkout(genre, count)
                if (result is OperationResult.Failure) {
                    println("Checkout failed: ${result.reason}")
                }
            }

            "Reclassify" -> {
                val genre = q[1]
                val oldValue = q[2].toInt()
                val newValue = q[3].toInt()
                val count = q[4].toInt()
                val result = store.reclassify(genre, oldValue, genre, newValue, count)
                if (result is OperationResult.Failure) {
                    println("Reclassify failed: ${result.reason}")
                }
            }
        }
    }

    return store
}

private sealed class OperationResult<out T> {
    data class Success<out T>(val data: T? = null) : OperationResult<T>()
    data class Failure(val reason: String) : OperationResult<Nothing>()
}

private class BookStore {

    private val genresBooks = TreeMap<String, TreeMap<Int, Int>>()

    fun acquire(genre: String, value: Int, count: Int = 1) {
        require(count > 0)
        val books = genresBooks.getOrPut(genre) { TreeMap() }
        books[value] = (books[value] ?: 0) + count
    }

    fun checkout(genre: String, count: Int): OperationResult<Map<Int, Int>> {
        require(count > 0)

        val books = genresBooks[genre]
            ?: return OperationResult.Failure("Genre '$genre' does not exist")

        val totalAvailable = books.values.sum()
        if (totalAvailable < count) {
            return OperationResult.Failure(
                "Genre '$genre' has only $totalAvailable books, cannot fulfill $count"
            )
        }

        var remaining = count
        val removed = mutableMapOf<Int, Int>()

        while (remaining > 0) {
            val (value, available) = books.firstEntry()
            val removeCount = minOf(available, remaining)
            removed[value] = removeCount

            if (removeCount == available) books.remove(value)
            else books[value] = available - removeCount

            remaining -= removeCount
        }

        if (books.isEmpty()) genresBooks.remove(genre)

        return OperationResult.Success(removed)
    }

    fun reclassify(
        oldGenre: String,
        oldValue: Int,
        newGenre: String,
        newValue: Int,
        count: Int = 1
    ): OperationResult<Unit> {
        require(count > 0)

        val oldSet = genresBooks[oldGenre]
            ?: return OperationResult.Failure("Old genre '$oldGenre' does not exist")

        val available = oldSet[oldValue]
            ?: return OperationResult.Failure("Value $oldValue not found in '$oldGenre'")

        if (available < count) {
            return OperationResult.Failure(
                "Only $available books at value $oldValue in '$oldGenre', cannot remove $count"
            )
        }

        if (available == count) oldSet.remove(oldValue)
        else oldSet[oldValue] = available - count

        if (oldSet.isEmpty()) genresBooks.remove(oldGenre)

        val newSet = genresBooks.getOrPut(newGenre) { TreeMap() }
        newSet[newValue] = (newSet[newValue] ?: 0) + count

        return OperationResult.Success(Unit)
    }

    fun getGenreMonetaryValue(genre: String): Int {
        val multiset = genresBooks[genre] ?: return 0
        return multiset.entries.sumOf { (value, count) -> value * count }
    }

    fun getLibraryMonetaryValue(): Int =
        genresBooks.values.sumOf { it.entries.sumOf { (value, count) -> value * count } }
}