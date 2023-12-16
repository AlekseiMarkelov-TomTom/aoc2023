package test.kotlin

import day13.part1
import day13.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day13Test {
    @ParameterizedTest
    @CsvSource(
        "Day13_test, 0", "Day13, 0"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day13_test, 0", "Day13, 0"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}