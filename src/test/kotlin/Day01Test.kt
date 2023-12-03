package test.kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import part1CalibrationNumber
import part2CalibrationNumber

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
}
