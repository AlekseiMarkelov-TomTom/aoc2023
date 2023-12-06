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
        "Day06_test, 288", "Day06, 32076"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day06_test, 71503", "Day06, 34278221"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}
