package day11

import println
import readInput
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)

data class Universe(val stars: List<Point>, val expansionMapX: List<Int>, val expansionMapY: List<Int>) {
    fun expandedStars() = stars.map { Point(expansionMapX[it.x], expansionMapY[it.y]) }
}

fun parseInput(input: List<String>, expansionFactor: Int = 2): Universe {
    val stars = mutableListOf<Point>()
    val potentialExpansionsY = Array(input.size) { true }
    val potentialExpansionsX = Array(input[0].length) { true }
    for (line in input.withIndex()) {
        line.value.withIndex().filter { it.value == '#' }.forEach {
            potentialExpansionsX[it.index] = false
            potentialExpansionsY[line.index] = false
            stars.add(Point(it.index, line.index))
        }
    }
    var expansionsX = 0
    var expansionsY = 0
    val expansionMapX = potentialExpansionsX.mapIndexed() { index, value ->
        if (value) {
            expansionsX += expansionFactor - 1
        }
        index + expansionsX
    }
    val expansionMapY = potentialExpansionsY.mapIndexed() { index, value ->
        if (value) {
            expansionsY += expansionFactor - 1
        }
        index + expansionsY
    }
    return Universe(stars, expansionMapX, expansionMapY)
}

fun starPairs(universe: Universe): Sequence<Pair<Point, Point>> {
    return sequence {
        val expandedStars = universe.expandedStars()
        for (star1 in expandedStars.dropLast(1).asSequence().withIndex()) {
            for (star2 in expandedStars.drop(star1.index + 1)) {
                yield(Pair(star1.value, star2))
            }
        }
    }
}

fun distance(p1: Point, p2: Point): Long {
    return (p1.x - p2.x).absoluteValue.toLong() + (p1.y - p2.y).absoluteValue.toLong()
}

fun part1(input: List<String>): Long {
    val universe = parseInput(input)
    return starPairs(universe).sumOf { (p1, p2) ->
        val d = distance(p1, p2)
        println("[${p1.x},${p1.y}]x[${p2.x},${p2.y}]${d}")
        d
    }

}

fun part2(input: List<String>, expansionFactor: Int = 1000000): Long {
    val universe = parseInput(input, expansionFactor)
    return starPairs(universe).sumOf { (p1, p2) ->
        distance(p1, p2)
    }
}

fun main() {
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}