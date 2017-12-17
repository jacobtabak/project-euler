package me.tabak.projecteuler

import kotlin.coroutines.experimental.buildSequence

object PrimeSieve {
    private val primeArray = BooleanArray(Int.MAX_VALUE.sqrt(), { i -> i > 1 })

    init {
        // mark non-primes <= n using Sieve of Eratosthenes
        var factor = 2
        while (factor * factor < primeArray.size) {
            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (primeArray[factor]) {
                var j = factor
                while (factor * j < primeArray.size) {
                    primeArray[factor * j] = false
                    j++
                }
            }
            factor++
        }
    }

    fun isPrime(n: Int) : Boolean {
        return primeArray[n]
    }

    fun sequence() = buildSequence {
        for ((key, value) in primeArray.withIndex()) {
            if (value) {
                yield(key)
            }
        }
    }
}
