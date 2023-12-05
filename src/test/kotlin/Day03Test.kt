package test.kotlin

import day03.part1
import day03.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day03Test {
    @ParameterizedTest
    @CsvSource(
        "Day03_test, 4361", "Day03, 530495"
    )
    fun testPart1(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day03_test, 467835", "Day03, 80253814"
    )
    fun testPart2(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}