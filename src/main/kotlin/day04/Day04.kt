package day04

import println
import readInput
import kotlin.math.min

import kotlin.math.pow

data class Ticket(val winningNumbers: List<Int>, val numbers: List<Int>)

fun parseTicket(input: String): Ticket
{
    val sequences = input.split(':')[1].splitToSequence('|').iterator()
    val winningsNumbers = sequences.next().splitToSequence(' ').filter { it.trim().isNotEmpty() }.map { it.trim().toInt() }.toList()
    val numbers = sequences.next().splitToSequence(' ').filter { it.trim().isNotEmpty() }.map { it.trim().toInt() }.toList()
    return Ticket(winningsNumbers, numbers)
}

fun countPoints(ticket: Ticket): Int
{
    val wins = ticket.winningNumbers.toSet().intersect(ticket.numbers.toSet()).size
    return if (wins == 0) {
        0
    } else {
        var points = 1;
        for (counter in 1 until wins) {
            points *= 2;
        }
        points;
    }
}

fun processTickets(tickets: List<Ticket>): Int
{
    val ticketCounts = Array(tickets.size) { 1 }
    for (index in tickets.indices) {
        val wonCards = tickets[index].winningNumbers.toSet().intersect(tickets[index].numbers.toSet()).size
        for (j in index + 1 until min(tickets.size, index + 1 + wonCards)) {
            ticketCounts[j] += ticketCounts[index]
        }
    }
    return ticketCounts.sum()
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { parseTicket(it) }.sumOf { countPoints(it) }
    }

    fun part2(input: List<String>): Int {
        return processTickets(input.map { parseTicket(it) })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")

    val part1Result = part1(input)
    //check(part1Result == 530495)

    val part2Result = part2(input)
    //check(part2Result == 80253814)

    part1Result.println()
    part2Result.println()
}
