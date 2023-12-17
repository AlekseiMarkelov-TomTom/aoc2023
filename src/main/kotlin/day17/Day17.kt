package day17

import println
import readInput
import java.util.*

enum class Direction {
    Up, Down, Left, Right
}

fun opposite(direction: Direction): Direction {
    return when (direction) {
        Direction.Up -> Direction.Down
        Direction.Down -> Direction.Up
        Direction.Left -> Direction.Right
        Direction.Right -> Direction.Left
    }
}

data class Label(val totalThermalLoss: Int, val x: Int, val y: Int, val direction: Direction, val straightSteps: Int) :
    Comparable<Label> {
    override fun compareTo(other: Label): Int {
        return totalThermalLoss.compareTo(other.totalThermalLoss)
    }

}

data class Grid(val data: List<List<Int>>) {
    val xsize = data.first().size
    val ysize = data.size
}

fun parseInput(input: List<String>): Grid {
    val data = input.map { it.map(Char::digitToInt) }
    return Grid(data)
}

fun nextLabel(label: Label, grid: Grid, direction: Direction): Label? {
    val straightSteps = if (label.direction == direction) label.straightSteps + 1 else 1
    val x = when (direction) {
        Direction.Left -> label.x - 1
        Direction.Right -> label.x + 1
        Direction.Up, Direction.Down -> label.x
    }
    val y = when (direction) {
        Direction.Up -> label.y - 1
        Direction.Down -> label.y + 1
        Direction.Left, Direction.Right -> label.y
    }
    if (x !in 0 until grid.xsize || y !in 0 until grid.ysize || straightSteps > 3) {
        return null
    }
    return Label(label.totalThermalLoss + grid.data[y][x], x, y, direction, straightSteps)
}

data class LabelKey(val x: Int, val y: Int, val direction: Direction, val straightSteps: Int)

fun part1(input: List<String>): Long {
    val queue = PriorityQueue<Label>()
    val grid = parseInput(input)
    queue.add(Label(0, 0, 0, Direction.Right, 0))
    val visitedTiles = mutableMapOf<LabelKey, Int>()
    while (queue.isNotEmpty()) {
        val currentLabel = queue.remove()
        queue.size.println()
        if (currentLabel.x == grid.xsize - 1 && currentLabel.y == grid.ysize - 1) {
            return currentLabel.totalThermalLoss.toLong()
        }
        queue.addAll(Direction.values().filterNot { it == opposite(currentLabel.direction) }
            .mapNotNull { nextLabel(currentLabel, grid, it) }.filter {
                val key = LabelKey(it.x, it.y, it.direction, it.straightSteps)
                val visitedThermalLoss = visitedTiles[key]
                if (visitedThermalLoss != null && visitedThermalLoss <= it.totalThermalLoss) {
                    false
                } else {
                    visitedTiles[key] = it.totalThermalLoss
                    true
                }
            })
    }
    return -1
}

fun part2(input: List<String>): Long {
    return input.size.toLong()
}

fun main() {
    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}