package day14

import println
import readInput

data class Platform(val data: List<String>) {
    val xsize = data.first().length
    val ysize = data.size
}

fun tiltNorth(platform: Platform): Platform {
    return platform
}

fun tiltWest(platform: Platform): Platform {
    return platform
}

fun tiltSouth(platform: Platform): Platform {
    return platform
}

fun tiltEast(platform: Platform): Platform {
    return platform
}

fun spin(platform: Platform): Platform {
    return tiltEast(tiltSouth(tiltWest(tiltNorth(platform))))
}

fun load(input: List<Char>): Int {
    var potentialPower = input.size
    var power = 0
    for (row in input.withIndex()) {
        when (row.value) {
            'O' -> {
                power += potentialPower
                potentialPower--
            }
            '.' -> {
                continue
            }
            '#' -> {
                potentialPower = input.size - row.index - 1
            }
            else -> {
                assert(false)
            }
        }
    }
    return power
}

fun part1(input: List<String>): Long {
    return (0 until input.first().length).map { x -> input.asSequence().map { it[x] }.toList() }.sumOf { load(it) }.toLong()
}

fun part2(input: List<String>): Long {
    val cycleCount = 1000000000;
    var platform = Platform(input)
    val cache = mutableMapOf<Platform, Int>(platform to 0)
    for (i in 1 .. cycleCount) {
        platform = spin(platform)
        val index = cache.getOrPut(platform) { i }
        if (index != i) {
            val cycleSize = i - index
            cycleSize.println()
            /*cycle stabilized around index..i*/
            break
        }
    }
}

fun main() {
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}