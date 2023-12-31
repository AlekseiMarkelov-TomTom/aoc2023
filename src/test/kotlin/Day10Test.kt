package test.kotlin

import day10.part1
import day10.part2
import day10.Point
import day10.polygonArea
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day10Test {
    @ParameterizedTest
    @CsvSource(
        "Day10_test, 8", "Day10, 7173"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day10_test, 1", "Day10_test2, 4", "Day10_test3, 8", "Day10_test4, 10", "Day10, 291"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}