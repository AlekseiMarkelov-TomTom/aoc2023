package day15

import println
import readInput

fun hash(input: String): Int {
    return input.fold(0) { acc, c ->
        ((acc + c.code) * 17) % 256
    }
}

fun part1(input: List<String>): Long {
    return input.first().splitToSequence(',').sumOf { hash(it) }.toLong()
}

data class Lens(val label: String, var focalLength: Int)

fun part2(input: List<String>): Long {
    val boxes = Array(256) { mutableListOf<Lens>() }
    val regex = Regex("""([a-z]+)((?:=\d)?)(-?)""")
    for (command in input.first().splitToSequence(',')) {
        val result = regex.matchEntire(command)!!
        val label = result.groups[1]!!.value
        val assign = result.groups[2]!!.value
        val remove = result.groups[3]!!.value
        if (remove.isNotEmpty()) {
            boxes[hash(label)].removeIf() {
                it.label == label
            }
        } else if (assign.isNotEmpty()) {
            val focalLength = assign.drop(1).toInt()
            val h = hash(label)
            val lens = boxes[h].find() {
                it.label == label
            }
            if (lens != null) {
                lens.focalLength = focalLength
            } else {
                boxes[h].add(Lens(label, focalLength))
            }
        } else {
            assert(false)
        }
    }
    return boxes.asSequence().withIndex().flatMap { box ->
        box.value.asSequence().withIndex().map { (box.index + 1) * (it.index + 1) * it.value.focalLength }
    }.sum().toLong()
}

fun main() {
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}