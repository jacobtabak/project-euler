package me.tabak.projecteuler

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UtilTests {
    @Test
    fun testPrimes() {
        assertThat(1.isPrime(), equalTo(false))
        assertThat(2.isPrime(), equalTo(true))
        assertThat(3.isPrime(), equalTo(true))
        assertThat(4.isPrime(), equalTo(false))
        assertThat(5.isPrime(), equalTo(true))
    }

    @Test
    fun testIntLength() {
        assertThat(27.length(), equalTo(2))
        assertThat(421583.length(), equalTo(6))
    }

    @Test
    fun palindromeTests() {
        assertThat(isPalindrome("o"), equalTo(true))
        assertThat(isPalindrome("oo"), equalTo(true))
        assertThat(isPalindrome("oxo"), equalTo(true))
        assertThat(isPalindrome("oxxo"), equalTo(true))
        assertThat(isPalindrome("oxox"), equalTo(false))
    }

    @Test
    fun maxNumberForDigitsTest() {
        assertThat(maxNumberForDigits(1), equalTo(9))
        assertThat(maxNumberForDigits(2), equalTo(99))
        assertThat(maxNumberForDigits(3), equalTo(999))
    }

    @Test
    fun testSumOfSquares() {
        assertThat((1..10).sumOfSquares(), equalTo(385L))
    }

    @Test
    fun testSquareOfSum() {
        assertThat((1..10).squareOfSum(), equalTo(3025L))
    }

    @Test
    fun triangleTest() {
        assertThat(1.triangle(), equalTo(1))
        assertThat(2.triangle(), equalTo(3))
        assertThat(3.triangle(), equalTo(6))
        assertThat(4.triangle(), equalTo(10))
        assertThat(5.triangle(), equalTo(15))
        assertThat(6.triangle(), equalTo(21))
        assertThat(7.triangle(), equalTo(28))
        assertThat(8.triangle(), equalTo(36))
        assertThat(9.triangle(), equalTo(45))
        assertThat(10.triangle(), equalTo(55))
    }

    @Test
    fun testFactorize() {
        assertThat(1.factorize(), equalTo(setOf(1)))
        assertThat(3.factorize(), equalTo(setOf(1, 3)))
        assertThat(6.factorize(), equalTo(setOf(1, 2, 3, 6)))
        assertThat(10.factorize(), equalTo(setOf(1, 2, 5, 10)))
        assertThat(15.factorize(), equalTo(setOf(1, 3, 5, 15)))
        assertThat(21.factorize(), equalTo(setOf(1, 3, 7, 21)))
        assertThat(28.factorize(), equalTo(setOf(1, 2, 4, 7, 14, 28)))
    }

    @Test
    fun testCollatz() {
        assertThat(
                collatzSequence(13).toList(),
                equalTo(listOf(13L, 40L, 20L, 10L, 5L, 16L, 8L, 4L, 2L, 1L)))
    }

    @Test
    fun testCollatzCount() {
        assertThat(collatzSequenceCount(13), equalTo(10))
    }

    @Test
    fun testIntToWord() {
        assertThat(1.toWord(), equalTo("one"))
        assertThat(10.toWord(), equalTo("ten"))
        assertThat(13.toWord(), equalTo("thirteen"))
        assertThat(20.toWord(), equalTo("twenty"))
        assertThat(25.toWord(), equalTo("twenty five"))
        assertThat(101.toWord(), equalTo("one hundred and one"))
        assertThat(927.toWord(), equalTo("nine hundred and twenty seven"))
    }
}