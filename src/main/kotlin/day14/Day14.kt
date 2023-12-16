package day14

import println
import readInput

data class Platform(val data: List<List<Char>>) {
    val xsize = data.first().size
    val ysize = data.size
}

fun tiltNorth(platform: Platform): Platform {
    val newData = Array(platform.ysize) { mutableListOf<Char>() }
    for (x in 0 until platform.xsize) {
        var insertionIndex = 0
        for (currentIndex in 0 until platform.ysize) {
            when (platform.data[currentIndex][x]) {
                'O' -> {
                    newData[insertionIndex].add('O')
                    insertionIndex++
                }
                '.' -> {
                    /*Do nothing for now, we'll fill gaps at the end or on # */
                }
                '#' -> {
                    while (insertionIndex < currentIndex) {
                        newData[insertionIndex++].add('.')
                    }
                    newData[insertionIndex++].add('#')
                }
            }
        }
        while (insertionIndex < platform.ysize) {
            newData[insertionIndex++].add('.')
        }
    }
    return Platform(newData.toList())
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

fun load(platform: Platform): Int {
    return platform.data.asSequence().withIndex().sumOf { it.value.count() { c -> c == 'O' } * (platform.ysize - it.index) }
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
    return load(tiltNorth(Platform(input.map { it -> it.toList() }))).toLong()
}

fun part2(input: List<String>): Long {
    val cycleCount = 1000000000;
    var platform = Platform(input.map { it -> it.toList() })
    val cache = mutableMapOf<Platform, Int>(platform to 0)
    for (i in 1 .. cycleCount) {
        platform = spin(platform)
        val index = cache.getOrPut(platform) { i }
        if (index != i) {
            val cycleSize = i - index
            cycleSize.println()
            /*cycle stabilized around index to i*/
            break
        }
    }
    return 0
}

fun main() {
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}