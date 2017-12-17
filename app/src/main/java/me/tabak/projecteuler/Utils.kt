package me.tabak.projecteuler

import java.lang.Math.*
import kotlin.coroutines.experimental.buildSequence

fun primes() = buildSequence {
    var i = 2
    while (true) {
        if (i.isPrime()) {
            yield(i)
        }
        i++
    }
}

fun Long.isPrime() : Boolean {
    when {
        this <= 1L -> return false
        this < 4 -> return true
        this % 2 == 0L -> return false
        this < 9 -> return true
        this % 3 == 0L -> return false
        else -> {
            val squareRoot = sqrt(this.toDouble()).toInt()
            var factor = 5
            while (factor <= squareRoot) {
                if (this % factor == 0L) return false
                if (this % (factor + 2) == 0L) return false
                factor += 6
            }
            return true
        }
    }
}

fun Int.isPrime() : Boolean {
    when {
        this <= 1 -> return false
        this < 4 -> return true
        this % 2 == 0 -> return false
        this < 9 -> return true
        this % 3 == 0 -> return false
        else -> {
            val squareRoot = sqrt(this.toDouble()).toInt()
            var factor = 5
            while (factor <= squareRoot) {
                if (this % factor == 0) return false
                if (this % (factor + 2) == 0) return false
                factor += 6
            }
            return true
        }
    }
}

fun Int.length() : Int {
    return log10(this.toDouble()).toInt() + 1
}

fun isPalindrome(s: String) : Boolean {
    return (0..(s.length / 2)).all { s[it] == s[s.length - 1 - it] }
}

fun maxNumberForDigits(digits: Int) : Int {
    if (digits <= 0) throw IllegalArgumentException("Parameter must be > 0")
    return (Math.pow(10.0, digits.toDouble()) - 1).toInt()
}

fun isDivisibleByRange(n: Long, r: IntRange) : Boolean {
    return r.all{ n % it == 0L }
}

fun IntRange.sumOfSquares() : Long {
    return this.map { it.toLong() * it.toLong() }.sum()
}

fun IntRange.squareOfSum() : Long {
    return pow(this.sum().toDouble(), 2.0).toLong()
}

fun Int.triangle() : Int {
    return (1..this).sum()
}

fun Int.factorize() : Set<Int> {
    val factors = HashSet<Int>()
    factors.add(1)
    factors.add(this)
    (2 until this / 2)
            .filter { this % it == 0 }
            .forEach {
                factors.add(it)
                factors.add(this / it)
            }
    return factors
}

fun Int.square() : Int {
    return this * this
}

fun Int.sqrt() : Int {
    return Math.sqrt(this.toDouble()).toInt()
}

fun Int.factorCount() : Int {
    var divisor = this
    var count = 1
    for (prime in PrimeSieve.sequence()) {
        if (prime.square() > divisor) {
            count *= 2
            break
        }

        var exponent = 0
        while (divisor % prime == 0) {
            exponent++
            divisor /= prime
        }

        if (exponent > 0) {
            count *= (exponent + 1)
        }

        if (divisor == 1) {
            break
        }
    }
    return count
}

fun collatzSequence(startNumber: Long) : Sequence<Long> = buildSequence {
    var n = startNumber
    yield(n)
    while (n != 1L) {
        n = nextCollatzNumber(n)
        yield(n)
    }
}

fun collatzSequenceCount(startNumber: Long) : Int {
    var n = startNumber
    var count = 1
    while (n != 1L) {
        val cachedList = CollatzSequenceCountCache.cache[n]
        if (cachedList != null) {
            // it's cached, append the count of the cached list minus 1
            count += cachedList - 1
            n = 1
        } else {
            n = nextCollatzNumber(n)
            count++
        }
    }
    CollatzSequenceCountCache.cache.put(startNumber, count)
    return count
}

fun nextCollatzNumber(n: Long) : Long {
    return if (n % 2 == 1L) {
        3 * n + 1
    } else {
        n / 2
    }
}

val DIGITS = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
val TEENS = arrayOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
val TENS = arrayOf("", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

fun Int.toWord() : String {
    val n = this

    if (n > 1000) {
        throw IllegalArgumentException("This method only works on numbers <= 1000")
    }

    if (n == 1000) {
        return "one thousand"
    }

    return buildString {
        val hundreds = n / 100
        if (hundreds > 0) {
            append(DIGITS[hundreds] + " hundred")
            if (n % 100 > 0) {
                append(" and ")
            } else {
                append(" ")
            }
        }

        when {
            n % 100 == 0 -> if (this.isEmpty()) append(DIGITS[0])
            n % 100 < 10 -> append(DIGITS[n % 100])
            n % 100 < 20 -> append(TEENS[n % 10])
            else -> {
                append(TENS[(n % 100) / 10])
                if (n % 10 > 0) append(" " + DIGITS[n % 10])
            }
        }
    }
}