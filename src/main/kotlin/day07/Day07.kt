package day07

import println
import readInput

val cardValues = mapOf(
    'A' to 14,
    'K' to 13,
    'Q' to 12,
    'J' to 11,
    'T' to 10,
    '9' to 9,
    '8' to 8,
    '7' to 7,
    '6' to 6,
    '5' to 5,
    '4' to 4,
    '3' to 3,
    '2' to 2
)

val cardValuesWithJoker = mapOf(
    'A' to 14,
    'K' to 13,
    'Q' to 12,
    'T' to 10,
    '9' to 9,
    '8' to 8,
    '7' to 7,
    '6' to 6,
    '5' to 5,
    '4' to 4,
    '3' to 3,
    '2' to 2,
    'J' to 1,
)
enum class HandType(val value: Int) {
    FiveOfAKind(6), FourOfAKind(5), FullHouse(4), ThreeOfAKind(3), TwoPairs(2), OnePair(1), HighCard(0)

}

// Conversion function from Char to Card
fun cardValue(card: Char) = cardValues[card]!!

fun cardValueWithJoker(card: Char) = cardValuesWithJoker[card]!!


data class Hand(val cards: String, val bid: Long) : Comparable<Hand> {
    private val type: HandType
    init {
        type = getType()
    }
    private fun getType(): HandType {
        return when (cards.groupingBy { it }.eachCount().values.sortedDescending()) {
            listOf(5) -> HandType.FiveOfAKind
            listOf(4, 1) -> HandType.FourOfAKind
            listOf(3, 2) -> HandType.FullHouse
            listOf(3, 1, 1) -> HandType.ThreeOfAKind
            listOf(2, 2, 1) -> HandType.TwoPairs
            listOf(2, 1, 1, 1) -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

    override fun compareTo(other: Hand): Int {
        val typeComparison = type.value.compareTo(other.type.value)
        if (typeComparison != 0) {
            return typeComparison
        }
        for ((card1, card2) in cards.zip(other.cards)) {
            val comparison = cardValue(card1).compareTo(cardValue(card2))
            if (comparison != 0) {
                return comparison
            }
        }
        return 0
    }
}

data class HandWithJoker(val cards: String, val bid: Long) : Comparable<HandWithJoker> {
    private val type: HandType
    init {
        type = getType()
    }
    private fun getType(): HandType {
        val counts = cards.groupingBy { it }.eachCount()
        val jokersCount = counts.getOrDefault('J', 0)
        if (jokersCount == 5) {
            return HandType.FiveOfAKind
        }
        val countsWithoutJoker = counts.filterKeys { it != 'J' }.values.sortedDescending().toMutableList()
        countsWithoutJoker[0] += jokersCount
        return when (countsWithoutJoker) {
            listOf(5) -> HandType.FiveOfAKind
            listOf(4, 1) -> HandType.FourOfAKind
            listOf(3, 2) -> HandType.FullHouse
            listOf(3, 1, 1) -> HandType.ThreeOfAKind
            listOf(2, 2, 1) -> HandType.TwoPairs
            listOf(2, 1, 1, 1) -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

    override fun compareTo(other: HandWithJoker): Int {
        val typeComparison = type.value.compareTo(other.type.value)
        if (typeComparison != 0) {
            return typeComparison
        }
        for ((card1, card2) in cards.zip(other.cards)) {
            val comparison = cardValueWithJoker(card1).compareTo(cardValueWithJoker(card2))
            if (comparison != 0) {
                return comparison
            }
        }
        return 0
    }
}

fun parseInput(input: List<String>): List<Hand> {
    return input.asSequence().map { val (cards, bidString) = it.split(' '); Hand(cards, bidString.toLong()) }.toList()
}

fun part1(input: List<String>): Long {
    val sorted = parseInput(input).sorted()
    return sorted.asSequence().mapIndexed{ index, hand -> (index + 1) * hand.bid }.sum()
}

fun part2(input: List<String>): Long {
    val sorted = input.asSequence().map { val (cards, bidString) = it.split(' '); HandWithJoker(cards, bidString.toLong()) }.toList().sorted()
    return sorted.asSequence().mapIndexed{ index, hand -> (index + 1) * hand.bid }.sum()
}

fun main() {
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}