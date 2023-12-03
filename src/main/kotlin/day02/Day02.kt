package day02

import println
import readInput
import kotlin.math.max

data class StoneSet(val red: Int, val green: Int, val blue: Int)

data class Game(val id: Int, val stones: Sequence<StoneSet>)

fun parseGame(input: String): Game {
    val gameInfo = input.splitToSequence(":").map { it.trim() }.iterator()
    val gameId = gameInfo.next().replace("Game ", "").toInt()

    val lines = gameInfo.next().splitToSequence(";").map { it.trim() }
    val stoneSets = lines.map { line ->
        val stoneSetValues = line.splitToSequence(",").map { it.trim() }
        val colors = stoneSetValues.associate { stoneSet ->
            val (count, color) = stoneSet.split(" ")
            color to count.toInt()
        }

        val red = colors["red"] ?: 0
        val green = colors["green"] ?: 0
        val blue = colors["blue"] ?: 0

        StoneSet(red, green, blue)
    }

    return Game(gameId, stoneSets)
}

fun isGamePossible(game: Game, set: StoneSet): Boolean {
    return !game.stones.any { it.blue > set.blue || it.green > set.green || it.red > set.red }
}

fun powerSet(game: Game): Int {
    val it = game.stones.reduce { left, right ->
        StoneSet(max(left.red, right.red), max(left.green, right.green), max(left.blue, right.blue))
    }
    return it.red * it.green * it.blue
}

fun part1(input: List<String>): Int {
    return input.map { parseGame(it) }.filter { isGamePossible(it, StoneSet(12, 13, 14)) }.sumOf(Game::id)
}

fun part2(input: List<String>): Int {
    return input.map { parseGame(it) }.sumOf { powerSet(it) }
}

fun main() {
    val input = readInput("Day02")

    val part1Result = part1(input)
    check(part1Result == 2727)

    val part2Result = part2(input)
    check(part2Result == 56580)

    part1Result.println()
    part2Result.println()
}
