package test.kotlin

import day08.part1
import day08.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day08Test {
    @ParameterizedTest
    @CsvSource(
        "Day08_test, 2", "Day08_test2, 6", "Day08, 17287"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day08_test3, 6", "Day08, 18625484023687"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}