package day06

import println
import readInput

data class Game(val time: Int, val distance: Int)

fun parseInput(input: List<String>): Sequence<Game> {
    val (times, distances) = input.map { line ->
        line.splitToSequence(':').drop(1).first().splitToSequence(' ').filter { it.isNotEmpty() }
            .map { it.trim().toInt() }
    }
    return times.zip(distances).map { (time, distance) -> Game(time, distance) }
}

fun waysToWin(game: Game): Int {
    return (0..game.time).count { it * (game.time - it) > game.distance }
}

fun part1(input: List<String>): Int {
    return parseInput(input).map { waysToWin(it) }.fold(1) { acc, i -> acc * i }
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
