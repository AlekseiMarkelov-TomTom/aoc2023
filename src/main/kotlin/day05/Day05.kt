package day05

import println
import readInput

data class SingleRange(val destinationStart: Long, val sourceStart: Long, val length: Long)
data class Mapping(val mappings: List<SingleRange>) {
    fun transform(input: Long): Long {
        for (range in mappings) {
            if (input in range.sourceStart until range.sourceStart + range.length) {
                return range.destinationStart + input - range.sourceStart
            }
        }
        return input
    }
}

data class Almanac(
    val seeds: List<Long>,
    val seedToSoil: Mapping,
    val soilToFertilizer: Mapping,
    val fertilizerToWater: Mapping,
    val waterToLight: Mapping,
    val lightToTemperature: Mapping,
    val temperatureToHumidity: Mapping,
    val humidityToLocation: Mapping
)

fun parseSingleRange(input: String): SingleRange {
    val iterator = input.splitToSequence(' ').map { it.toLong() }.iterator()
    val destinationStart = iterator.next()
    val sourceStart = iterator.next()
    val length = iterator.next()
    return SingleRange(destinationStart, sourceStart, length)
}

fun parseMapping(input: Iterator<String>): Mapping {
    val mappings = mutableListOf<SingleRange>()
    input.next()
    do {
        val line = input.next()
        if (line.isEmpty()) {
            break
        }
        mappings.add(parseSingleRange(line))
    } while (input.hasNext())
    return Mapping(mappings)
}

fun parseInput(input: List<String>): Almanac {
    val iterator = input.iterator()
    val seeds = iterator.next().splitToSequence(':').drop(1).first().trim().splitToSequence(' ').map { it.toLong() }.toList()
    iterator.next()
    val seedToSoil = parseMapping(iterator)
    val soilToFertilizer = parseMapping(iterator)
    val fertilizerToWater = parseMapping(iterator)
    val waterToLight = parseMapping(iterator)
    val lightToTemperature = parseMapping(iterator)
    val temperatureToHumidity = parseMapping(iterator)
    val humidityToLocation = parseMapping(iterator)
    return Almanac(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation)
}

fun part1(input: List<String>): Long {
    val almanac = parseInput(input)
    return almanac.seeds.asSequence().map { almanac.seedToSoil.transform(it) }.map { almanac.soilToFertilizer.transform(it) }
        .map { almanac.fertilizerToWater.transform(it) }.map { almanac.waterToLight.transform(it) }
        .map { almanac.lightToTemperature.transform(it) }.map { almanac.temperatureToHumidity.transform(it) }
        .minOfOrNull { almanac.humidityToLocation.transform(it) }!!
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
