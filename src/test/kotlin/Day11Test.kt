package test.kotlin

import day11.part1
import day11.part2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readInput

class Day11Test {
    @ParameterizedTest
    @CsvSource(
        "Day11_test, 374", "Day11, 0"
    )
    fun testPart1(inputFileName: String, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part1(readInput(inputFileName)))
    }

    @Test
    fun testDistance() {
        val stars = day11.parseInput(readInput("Day11_test")).expandedStars()
        Assertions.assertEquals(9, day11.distance(stars[4], stars[8]))
        Assertions.assertEquals(15, day11.distance(stars[0], stars[6]))
        Assertions.assertEquals(17, day11.distance(stars[2], stars[5]))
        Assertions.assertEquals(5, day11.distance(stars[7], stars[8]))
    }

    @ParameterizedTest
    @CsvSource(
        "Day11_test, 10, 1030", "Day11_test, 100, 8410", "Day11, 1000000, 622120986954"
    )
    fun testPart2(inputFileName: String, expansionFactor:Int, expectedResult: Long) {
        Assertions.assertEquals(expectedResult, part2(readInput(inputFileName), expansionFactor))
    }
}