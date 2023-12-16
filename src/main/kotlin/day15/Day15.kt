package day15

import println
import readInput

fun hash(input: String): Long {
    return input.fold(0) {
        acc, c -> ((acc + c.code) * 17) % 256
    }
}

fun part1(input: List<String>): Long {
    return input.first().splitToSequence(',').sumOf { hash(it) }
}

fun part2(input: List<String>): Long {
    return input.size.toLong()
}

fun main() {
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}