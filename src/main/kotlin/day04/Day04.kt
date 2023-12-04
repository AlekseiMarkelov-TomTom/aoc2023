package day04

import println
import readInput
import kotlin.math.min

import kotlin.math.pow

data class Ticket(val winningNumbers: List<Int>, val numbers: List<Int>) {
    fun wins(): Int {
        val winningSet = winningNumbers.toSet()
        return numbers.count { it in winningSet }
    }
}

fun parseTicket(input: String): Ticket {
    val sequences = input.split(':')[1].splitToSequence('|').iterator()
    val winningsNumbers =
        sequences.next().splitToSequence(' ').filter { it.trim().isNotEmpty() }.map { it.trim().toInt() }.toList()
    val numbers =
        sequences.next().splitToSequence(' ').filter { it.trim().isNotEmpty() }.map { it.trim().toInt() }.toList()
    return Ticket(winningsNumbers, numbers)
}

fun countPoints(ticket: Ticket): Int {
    val wins = ticket.wins()
    return if (wins == 0) {
        0
    } else {
        var points = 1
        for (counter in 1 until wins) {
            points *= 2
        }
        points
    }
}

fun processTickets(tickets: List<Ticket>): Int {
    val ticketCounts = Array(tickets.size) { 1 }
    for (index in tickets.indices) {
        val wonCards = tickets[index].wins()
        for (j in index + 1 until min(tickets.size, index + 1 + wonCards)) {
            ticketCounts[j] += ticketCounts[index]
        }
    }
    return ticketCounts.sum()
}

fun part1(input: List<String>): Int {
    return input.map { parseTicket(it) }.sumOf { countPoints(it) }
}

fun part2(input: List<String>): Int {
    return processTickets(input.map { parseTicket(it) })
}

fun main() {

    val input = readInput("Day04")

    val part1Result = part1(input)
    check(part1Result == 25571)

    val part2Result = part2(input)
    check(part2Result == 8805731)

    part1Result.println()
    part2Result.println()
}
