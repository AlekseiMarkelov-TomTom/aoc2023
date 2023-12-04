package day04

import println
import readInput
import kotlin.math.min

data class Ticket(val winningNumbers: List<Int>, val numbers: List<Int>) {
    fun wins(): Int {
        val winningSet = winningNumbers.toSet()
        return numbers.count { it in winningSet }
    }
}

fun parseTicket(input: String): Ticket {
    val sequences = input.splitToSequence(':').drop(1).first().splitToSequence('|').map { numbers ->
        numbers.splitToSequence(' ').map { it.trim() }.filter { it.isNotEmpty() }.map { it.toInt() }.toList()
    }.iterator()
    val winningsNumbers = sequences.next()
    val numbers = sequences.next()
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
    for ((index, ticket) in tickets.withIndex()) {
        val wonCards = ticket.wins()
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
    part1(input).println()
    part2(input).println()
}
