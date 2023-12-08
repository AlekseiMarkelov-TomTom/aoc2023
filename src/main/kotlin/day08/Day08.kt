package day08

import println
import readInput
import kotlin.math.pow

const val ORIGIN = "AAA"
const val DESTINATION = "ZZZ"

typealias Connections = Map<String, Pair<String, String>>

data class Task(val instructions: String, val connections: Connections)

fun parseInput(input: List<String>): Task {
    val iterator = input.iterator()
    val instructions = iterator.next()
    iterator.next()
    val regex = Regex("""(\w+) = \((\w+), (\w+)\)""")
    val connections = iterator.asSequence()
        .map { str -> regex.find(str)!!.let { it.groupValues[1] to Pair(it.groupValues[2], it.groupValues[3]) } }
        .toMap()
    return Task(instructions, connections)
}

fun nextNode(source: String, direction: Char, connections: Connections): String {
    val edges = connections[source]!!
    return when (direction) {
        'L' -> edges.first
        else -> edges.second
    }
}

fun stepsInTask(task: Task, origin: String, destinationPredicate: (String) -> Boolean): Long {
    var steps = 0L;
    var currentNode = origin;
    for (direction in generateSequence { task.instructions.asSequence() }.flatten()) {
        if (destinationPredicate(currentNode)) {
            return steps
        }
        ++steps;
        currentNode = nextNode(currentNode, direction, task.connections)
    }
    return 0
}

fun part1(input: List<String>): Long {
    return stepsInTask(parseInput(input), "AAA") { it == "ZZZ" }
}

typealias FactorsMap = Map<Long, Int>
fun factorize(input: Long): FactorsMap {
    return sequence {
        var n = input
        for (i in 2L until n / 2) {
            if (n % i == 0L) {
                yield(i)
                n /= i
                if (i > n / 2) {
                    break
                }
            }
        }
        if (n == input) {
            yield(n)
        }
    }.groupingBy { it }.eachCount().toMap()
}

fun maxFactors(acc: FactorsMap, it: FactorsMap): FactorsMap
{
    return acc.keys.union(it.keys).associateWith { key -> maxOf(acc.getOrDefault(key, 0), it.getOrDefault(key, 0)) }
}

fun part2(input: List<String>): Long {
    val task = parseInput(input)
    val steps =
        task.connections.keys.filter { it.endsWith('A') }.map { stepsInTask(task, it) { node -> node.endsWith('Z') } }
    return steps.map { factorize(it) }.reduce{acc, it -> maxFactors(acc, it)}.entries.fold(1) { acc, it -> acc * it.key.toDouble().pow(it.value).toLong() }
}

fun main() {
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}