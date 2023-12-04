package test.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readInput

class Day05Test {
    @Test
    fun part1() {
        val testInput = readInput("Day05_test")
        Assertions.assertEquals(0, day05.part1(testInput))
    }

    @Test
    fun part2() {
        val testInput = readInput("Day05_test")
        Assertions.assertEquals(0, day05.part2(testInput))
    }
}