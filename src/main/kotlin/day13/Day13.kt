package day13

import println
import readInput

data class Reflections(val x: Int, val y: Int)

fun getReflections(input: List<String>): Reflections
{
    return Reflections(0, 0)
}

fun parseInput(input: List<String>): Sequence<List<String>>
{
    return sequence {
        val iterator = input.iterator()
        val current = mutableListOf<String>()
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.isEmpty()) {
                yield(current)
                current.clear()
            }
        }
        if (current.isNotEmpty()) {
            yield(current)
        }
    }
}

fun part1(input: List<String>): Long {
    return parseInput(input).map { getReflections(it) }.sumOf { it.y * 100 + it.x }.toLong()
}

fun part2(input: List<String>): Long {
    return input.size.toLong()
}

fun main() {
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}