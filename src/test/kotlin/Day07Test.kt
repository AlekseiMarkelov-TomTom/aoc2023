package test.kotlin

import day07.part1
import day07.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput
import java.nio.channels.AsynchronousServerSocketChannel

class Day07Test {
    @ParameterizedTest
    @CsvSource(
        "Day07_test, 6440", "Day07, 250453939"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day07_test, 5905", "Day07, 0"
    )
    fun testPart2(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "AAT4J, OnePair"
    )
    fun testTyping(input: String, expectedType: String) {
        Assertions.assertEquals(expectedType, day07.Hand(input, 1).type().name)
    }
}