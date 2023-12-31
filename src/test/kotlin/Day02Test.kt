package test.kotlin

import day02.part1
import day02.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day02Test {
    @ParameterizedTest
    @CsvSource(
        "Day02_test, 8", "Day02, 2727"
    )
    fun testPart1(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day02_test, 2286", "Day02, 56580"
    )
    fun testPart2(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}