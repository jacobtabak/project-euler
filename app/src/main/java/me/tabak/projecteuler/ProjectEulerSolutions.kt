package me.tabak.projecteuler

import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.lang.Math.abs
import java.lang.Math.sqrt
import java.math.BigInteger

fun solution1() : Int {
    val multiplesOfThreeAndFive = HashSet<Int>()
    multiplesOfThreeAndFive += 0 until 1000 step 3
    multiplesOfThreeAndFive += 0 until 1000 step 5
    return multiplesOfThreeAndFive.sum()
}

fun solution2() : Int {
    var previous = 1
    var current = 1
    var sum = 0
    do {
        val next = current + previous
        if (next % 2 == 0) sum += next
        previous = current
        current = next
    } while (next < 4000000)
    return sum
}

fun solution3(n: Long) : Long? {
    val squareRoot = sqrt(n.toDouble()).toLong()
    return (squareRoot downTo 2)
            .find { n % it == 0L && it.isPrime() }
}

fun solution4(digits: Int) : Int? {
    var max = 0
    for (i in maxNumberForDigits(digits) downTo 1) {
        for (j in i downTo 1) {
            val product = i * j
            if (product > max && isPalindrome(product.toString())) {
                max = product
            }
        }
    }
    return max
}

fun solution5(n: Int) : Long {
    var product = 1L
    for (i in 2..n) {
        product *= i
    }
    println("Product of all numbers from 2 to $n is $product.")
    for (i in 2..n) {
        while (isDivisibleByRange(product / i, 2..n)) {
            val newProduct = product / i
            println("At $product. Dividing by $i. New result: $newProduct")
            product = newProduct
        }
    }
    return product
}

fun solution6(n: Int) : Long {
    return abs((1..n).sumOfSquares() - (1..n).squareOfSum())
}

fun solution7(n: Int) : Long {
    var count = 0
    var i = 0L
    while (true) {
        if (i.isPrime()) {
            count++
            println("Prime #$count is $i")
            if (count == n) {
                return i
            }
        }
        i++
    }
}

fun solution8(s: String, n: Int) : Long? {
    val input = s.replace(Regex("\\D"), "")
    val lastPosition = input.length - 1 - n
    return (0..lastPosition)
            .map { input.substring(it until it + n) }
            .map {
                var product = 1L
                it.forEach {
                    product *= it.toString().toInt()
                }
                product
            }
            .max()
}

fun solution9(sum: Int) : Long? {
    for (a in 1..sum) {
        for (b in a..sum) {
            for (c in b..sum) {
                if (a + b + c == sum) {
                    if (a * a + b * b == c * c) {
                        println("$a, $b, $c")
                        return 1L * a * b * c
                    }
                }
            }
        }
    }
    return null
}

fun solution10(n: Int) : Long {
    return (0L..n)
            .filter { it.isPrime() }
            .sum()
}

fun solution11(input: String, n: Int) : Long {
    // TODO: not very efficient, seems to calculate each product twice
    val grid = input
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.split(Regex("\\s+")) }
            .map { List(it.size) { index -> it[index].toInt() } }
    if (grid.isEmpty() || grid[0].isEmpty()) {
        throw IllegalArgumentException("Invalid grid")
    }
    val length = grid[0].size
    val height = grid.size
    var maxProduct = 0L

    val movementSet = HashSet<(Pair<Int, Int>) -> Pair<Int, Int>>()
    with (movementSet) {
        add { yx -> Pair(yx.first + 1, yx.second) }
        add { yx -> Pair(yx.first - 1, yx.second) }
        add { yx -> Pair(yx.first, yx.second + 1) }
        add { yx -> Pair(yx.first, yx.second - 1) }
        add { yx -> Pair(yx.first + 1, yx.second + 1)}
        add { yx -> Pair(yx.first - 1, yx.second - 1)}
        add { yx -> Pair(yx.first + 1, yx.second - 1)}
        add { yx -> Pair(yx.first - 1, yx.second + 1)}
    }

    for (y in 0 until height) {
        for (x in 0 until length) {
            movementSet.forEach {
                var coords = y to x
                var product = grid[y][x].toLong()
                val outputBuilder = StringBuilder(product.toString())
                try {
                    for (i in 1 until n) {
                        coords = it.invoke(coords)
                        val value = grid[coords.first][coords.second]
                        outputBuilder.append(" * $value")
                        product *= value
                    }
                    outputBuilder.append(" = $product")
                    if (product > maxProduct) {
                        maxProduct = product
                    }
                    println(outputBuilder.toString())
                } catch (e: IndexOutOfBoundsException) {
                    // Went out of bounds, discard result
                }
            }
        }
    }

    return maxProduct
}

fun solution12(n: Int) : Int {
    var factorCount = 0
    var triangleIndex = 1
    var triangle = triangleIndex
    while (factorCount < n) {
        triangleIndex++
        triangle += triangleIndex
        factorCount = triangle.factorCount()
    }
    return triangle
}

fun solution13(input: String) : String {
    val numbers = input
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { BigInteger(it) }
    var sum = BigInteger("0")
    numbers.forEach { sum += it }
    return sum.toString().substring(0, 10)
}

fun solution14() : Int {
    var startNumber = 0
    var maxTerms = 1
    for (i in 1..999999) {
        val terms = collatzSequenceCount(i.toLong())
        if (terms > maxTerms) {
            println("New best! $i has $terms terms")
            startNumber = i
            maxTerms = terms
        }
    }
    return startNumber
}

fun solution15(gridSize: Int) : Long {
    return solution15Helper(gridSize, 0 to 0, HashMap())
}

fun solution15Helper(gridSize: Int, coords: Pair<Int, Int>, cache: HashMap<Pair<Int, Int>, Long>) : Long {
    return when {
        coords == gridSize to gridSize -> 0
        coords.first == gridSize -> 1
        coords.second == gridSize -> 1
        else -> {
            val cachedValue = cache[coords]
            val totalMoves = if (cachedValue != null) {
                cachedValue
            } else {
                val rightMoves = solution15Helper(gridSize, coords.first + 1 to coords.second, cache)
                val downMoves = solution15Helper(gridSize, coords.first to coords.second + 1, cache)
                cache[coords] = rightMoves + downMoves
                rightMoves + downMoves
            }
            println("From $coords there are $totalMoves moves")
            totalMoves
        }
    }
}

fun solution16(exponent: Int) : Long {
    val bigInt = BigInteger("2").pow(exponent)
    var sum = 0L
    bigInt.toString().forEach { sum += it.toString().toLong() }
    return sum
}

fun solution17(n: Int) : Int {
    val output = buildString {
        for (i in 1..n) {
            val word = i.toWord()
            println("$i -> $word")
            append(word)
        }
    }
    return output.replace(Regex("\\s"), "").length
}

fun solution18(input: String) : Int {
    // TODO: Not using a clever solution
    val tree = Tree(input)
    return solution18Helper(tree.root)
}

fun solution18Helper(node: Tree.Node): Int {
    val left = node.left
    val right = node.right
    return if (left == null && right == null) {
        node.value
    } else if (left == null || right == null) {
        throw IllegalStateException("Tree was not balanced")
    } else {
        node.value + Math.max(solution18Helper(left), solution18Helper(right))
    }
}

fun solution19() : Int {
    var count = 0
    var localDate = LocalDate(1901, 1, 1)
    while (localDate.year < 2001) {
        if (localDate.dayOfWeek == DateTimeConstants.SUNDAY) {
            count++
        }
        localDate = localDate.plusMonths(1)
    }
    return count
}
