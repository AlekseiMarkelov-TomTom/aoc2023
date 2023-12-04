package day05

import day04.countPoints
import day04.parseTicket
import day04.processTickets
import println
import readInput

fun part1(input: List<String>): Int {
    return input.size
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {

    val input = readInput("Day05")

    val part1Result = part1(input)
    //check(part1Result == 0)

    val part2Result = part2(input)
    //check(part2Result == 0)

    part1Result.println()
    part2Result.println()
}
