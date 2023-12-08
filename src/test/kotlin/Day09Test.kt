package test.kotlin

import day09.part1
import day09.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day09Test {
    @ParameterizedTest
    @CsvSource(
        "Day09_test, 0", "Day09, 0"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day09_test, 0", "Day09, 0"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}