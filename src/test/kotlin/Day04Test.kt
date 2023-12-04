package test.kotlin

import day04.parseTicket
import day04.part1
import day04.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day04Test {
    @ParameterizedTest
    @CsvSource(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53, 4",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19, 2",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1, 2",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83, 1",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36, 0",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11, 0"
    )
    fun testScoring(input: String, expectedScore: Int) {
        assertEquals(expectedScore, parseTicket(input).wins())
    }

    @ParameterizedTest
    @CsvSource(
        "Day04_test, 13",
        "Day04, 25571"
    )
    fun testPart1(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @ParameterizedTest
    @CsvSource(
        "Day04_test, 30",
        "Day04, 8805731"
    )
    fun testPart2(inputFileName: String, expectedResult: Int) {
        assertEquals(expectedResult, part2(readInput(inputFileName)))
    }
}