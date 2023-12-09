package day09

import println
import readInput

fun nextValue(input: List<Long>): Long
{
    if (input.all { it == 0L }) return 0
    return nextValue(input.asSequence().windowed(2).map { it[1] - it[0] }.toList()) + input.last()
}

fun previousValue(input: List<Long>): Long
{
    if (input.all { it == 0L }) return 0
    return input.first() - previousValue(input.asSequence().windowed(2).map { it[1] - it[0] }.toList())

}

fun parseInput(input: List<String>): Sequence<List<Long>> {
    return input.asSequence().map { line -> line.splitToSequence(' ').map { it.toLong() }.toList() }
}

fun part1(input: List<String>): Long {
    return parseInput(input).map { nextValue(it) }.sum()
}

fun part2(input: List<String>): Long {
    return parseInput(input).map { previousValue(it) }.sum()
}

fun main() {
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}