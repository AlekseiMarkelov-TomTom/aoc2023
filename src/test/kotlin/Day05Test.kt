package test.kotlin

import day05.parseInput
import day05.part1
import day05.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day05Test {
    @ParameterizedTest
    @CsvSource(
        "Day05_test, 35", "Day05, 551761867"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day05_test, 46", "Day05, 57451709"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}
