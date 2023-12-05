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

    @Test
    fun testPart2SingleLocation() {
        val almanac = parseInput(readInput("Day05_test"))
        val inputRange = LongRange(82, 82)
        val soil = almanac.seedToSoil.transform(inputRange)
        assertEquals(84, soil.first().first)
        val fertilizer = soil.flatMap { almanac.soilToFertilizer.transform(it) }
        assertEquals(84, fertilizer.first().first)
        val water = fertilizer.flatMap { almanac.fertilizerToWater.transform(it) }
        assertEquals(84, water.first().first)
        val light = water.flatMap { almanac.waterToLight.transform(it) }
        assertEquals(77, light.first().first)
        val temperature = light.flatMap { almanac.lightToTemperature.transform(it) }
        assertEquals(45, temperature.first().first)
        val humidity = temperature.flatMap { almanac.temperatureToHumidity.transform(it) }
        assertEquals(46, humidity.first().first)
        val location = humidity.flatMap { almanac.humidityToLocation.transform(it) }
        assertEquals(46, location.first().first)
    }
}