package test.kotlin

import day01.part1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import day01.part1CalibrationNumber
import day01.part2
import day01.part2CalibrationNumber
import org.junit.jupiter.api.Test
import readInput

class Day01Test {
    @ParameterizedTest
    @CsvSource(
        "1abc2, 12", "pqr3stu8vwx, 38", "a1b2c3d4e5f, 15", "treb7uchet, 77"
    )
    fun testPart1CalibrationNumber(input: String, expectedResult: Int) {
        assertEquals(expectedResult, part1CalibrationNumber(input))
    }

    @ParameterizedTest
    @CsvSource(
        "two1nine, 29",
        "eightwothree, 83",
        "abcone2threexyz, 13",
        "xtwone3four, 24",
        "4nineeightseven2, 42",
        "zoneight234, 14",
        "7pqrstsixteen, 76"
    )
    fun testPart2CalibrationNumber(input: String, expectedResult: Int) {
        assertEquals(expectedResult, part2CalibrationNumber(input))
    }

    @Test
    fun testPart1() {
        val testInput = readInput("Day01_test")
        assertEquals(142, part1(testInput))
    }

    @Test
    fun testPart2() {
        val testInput2 = readInput("Day01_test2")
        assertEquals(281, part2(testInput2))
    }
}
