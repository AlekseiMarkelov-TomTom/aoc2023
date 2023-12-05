package day05

import println
import readInput

data class SingleRange(val destinationStart: Long, val sourceRange: LongRange)
data class Mapping(val mappings: List<SingleRange>) {
    fun transform(input: Long): Long {
        for (range in mappings) {
            if (input in range.sourceRange) {
                return range.destinationStart + input - range.sourceRange.first
            }
        }
        return input
    }

    fun transform(inputRange: LongRange): Sequence<LongRange> {
        var input = inputRange
        return sequence {
            for (range in mappings) {
                if (input.isEmpty()) {
                    return@sequence
                }
                if (input.last < range.sourceRange.first) {
                    yield(input)
                    return@sequence
                }
                if (input.first > range.sourceRange.last) {
                    continue
                }
                if (input.first < range.sourceRange.first) {
                    yield(LongRange(input.first, range.sourceRange.first - 1))
                    input = LongRange(range.sourceRange.first, input.last)
                }
                val last = minOf(input.last, range.sourceRange.last)
                yield(
                    LongRange(
                        range.destinationStart + input.first - range.sourceRange.first,
                        range.destinationStart - range.sourceRange.first + last
                    )
                )
                input = LongRange(last + 1, input.last)
            }
            if (!input.isEmpty()) {
                yield(input)
            }
        }
    }
}

data class Almanac(
    val seeds: Sequence<Long>,
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
    return SingleRange(destinationStart, LongRange(sourceStart, sourceStart + length - 1))
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
    return Mapping(mappings.sortedBy { it.sourceRange.first })
}

fun parseInput(input: List<String>): Almanac {
    val iterator = input.iterator()
    val seeds = iterator.next().splitToSequence(':').drop(1).first().trim().splitToSequence(' ').map { it.toLong() }
    iterator.next()
    val seedToSoil = parseMapping(iterator)
    val soilToFertilizer = parseMapping(iterator)
    val fertilizerToWater = parseMapping(iterator)
    val waterToLight = parseMapping(iterator)
    val lightToTemperature = parseMapping(iterator)
    val temperatureToHumidity = parseMapping(iterator)
    val humidityToLocation = parseMapping(iterator)
    return Almanac(
        seeds,
        seedToSoil,
        soilToFertilizer,
        fertilizerToWater,
        waterToLight,
        lightToTemperature,
        temperatureToHumidity,
        humidityToLocation
    )
}

fun part1(input: List<String>): Long {
    val almanac = parseInput(input)
    return almanac.seeds.map { almanac.seedToSoil.transform(it) }.map { almanac.soilToFertilizer.transform(it) }
        .map { almanac.fertilizerToWater.transform(it) }.map { almanac.waterToLight.transform(it) }
        .map { almanac.lightToTemperature.transform(it) }.map { almanac.temperatureToHumidity.transform(it) }
        .minOfOrNull { almanac.humidityToLocation.transform(it) }!!
}

fun part2(input: List<String>): Long {
    val almanac = parseInput(input)
    return almanac.seeds.chunked(2) { (first, length) -> LongRange(first, first + length - 1) }
        .flatMap { almanac.seedToSoil.transform(it) }.flatMap { almanac.soilToFertilizer.transform(it) }
        .flatMap { almanac.fertilizerToWater.transform(it) }.flatMap { almanac.waterToLight.transform(it) }
        .flatMap { almanac.lightToTemperature.transform(it) }.flatMap { almanac.temperatureToHumidity.transform(it) }
        .flatMap { almanac.humidityToLocation.transform(it) }.minOfOrNull { it.first }!!
}

fun main() {
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
