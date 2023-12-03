package test.kotlin

import day02.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

class Day02Test {
    @Test
    fun part1() {
        val testInput = readInput("Day02_test")
        assertEquals(8, day02.part1(testInput))
    }

    @Test
    fun part2() {
        val testInput = readInput("Day02_test")
        assertEquals(2286, day02.part2(testInput))
    }
}