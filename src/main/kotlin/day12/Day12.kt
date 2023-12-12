package day12

import println
import readInput


data class Record(val string: String, val checksum: List<Int>)

fun parseLine(input: String): Record {
    val iterator = input.splitToSequence(' ').iterator()
    val string = iterator.next()
    val checksum = iterator.next().splitToSequence(',').map { it.toInt() }.toList()
    return Record(string, checksum)
}

fun checkRecord(record: Record): Boolean {
    val list =
        record.string.splitToSequence('.').map { it.trim('.') }.filterNot { it.isEmpty() }.map { it.length }.toList()
    return list == record.checksum
}

fun arrangements(record: Record): Long {
    val firstQ = record.string.indexOf('?')
    if (firstQ < 0) {
        return if (checkRecord(record)) 1 else 0
    }
    if (firstQ > 1 && record.string[firstQ - 1] == '.') {
        //partial match
        val list =
            record.string.substring(0, firstQ).splitToSequence('.').map { it.trim('.') }.filterNot { it.isEmpty() }
                .map { it.length }.toList()
        if (list.size > record.checksum.size || record.checksum.subList(0, list.size) != list) {
            return 0
        }
    }
    return arrangements(
        Record(
            record.string.replaceFirst('?', '.'),
            record.checksum
        )
    ) + arrangements(Record(record.string.replaceFirst('?', '#'), record.checksum))
}

fun unfold(record: Record): Record {
    val string = sequence {
        yield(record.string)
        yield(record.string)
        yield(record.string)
        yield(record.string)
        yield(record.string)
    }.joinToString("?")
    val checksum = mutableListOf<Int>()
    for (i in 0 until 5) {
        checksum.addAll(record.checksum)
    }
    return Record(string, checksum)
}

fun part1(input: List<String>): Long {
    return input.map { parseLine(it) }.sumOf { arrangements(it) }
}

fun part2(input: List<String>): Long {
    return input.map { parseLine(it) }.map { unfold(it) }.sumOf { arrangements(it) }
}

fun main() {
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}