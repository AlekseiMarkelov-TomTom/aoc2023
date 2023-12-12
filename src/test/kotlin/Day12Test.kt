package test.kotlin

import day12.part1
import day12.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day12Test {
    @ParameterizedTest
    @CsvSource(
        "Day12_test, 21", "Day12, 7361"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day12_test, 525152", "Day12, 0"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}