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

fun checkRecord(record: Record): Boolean
{
    val list = record.string.splitToSequence('.').map { it.trim('.') }.filterNot { it.isEmpty() }.map { it.length }.toList()
    return list == record.checksum
}

fun arrangements(record: Record): Long
{
    val firstQ = record.string.indexOf('?')
    if (firstQ < 0) {
        return if (checkRecord(record)) 1 else 0
    }
    return arrangements(Record(record.string.replaceFirst('?', '.'), record.checksum)) + arrangements(Record(record.string.replaceFirst('?', '#'), record.checksum))
}
fun part1(input: List<String>): Long {
    return input.map { parseLine(it) }.sumOf { arrangements(it) }
}

fun part2(input: List<String>): Long {
    return input.size.toLong()
}

fun main() {
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}