package test.kotlin

import day06.part1
import day06.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day06Test {
    @ParameterizedTest
    @CsvSource(
        "Day06_test, 0", "Day06, 0"
    )
    fun testPart1(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day06_test, 0", "Day06, 0"
    )
    fun testPart2(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}
