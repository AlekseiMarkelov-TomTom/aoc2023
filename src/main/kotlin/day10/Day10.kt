package day10

import println
import readInput

enum class Direction {
    Up,
    Down,
    Left,
    Right
}

enum class PointType(val character: Char) {
    UD('|'),
    LR('-'),
    LD('7'),
    UR('L'),
    UL('J'),
    DR('F'),
    Ground('.'),
    StartingPosition('S')
}

val compatibleOrigins = mapOf(
    Direction.Left to setOf(PointType.StartingPosition, PointType.LR, PointType.LD, PointType.UL),
    Direction.Right to setOf(PointType.StartingPosition, PointType.LR, PointType.UR, PointType.DR),
    Direction.Up to setOf(PointType.StartingPosition, PointType.UD, PointType.UL, PointType.UR),
    Direction.Down to setOf(PointType.StartingPosition, PointType.UD, PointType.LD, PointType.DR),
)

fun complementaryDirection(direction: Direction): Direction {
    return when (direction) {
        Direction.Left -> Direction.Right
        Direction.Right -> Direction.Left
        Direction.Down -> Direction.Up
        Direction.Up -> Direction.Down
    }
}

val compatibleDestinations = mapOf(
    complementaryDirection(Direction.Left) to setOf(
        PointType.StartingPosition,
        PointType.LR,
        PointType.LD,
        PointType.UL
    ),
    complementaryDirection(Direction.Right) to setOf(
        PointType.StartingPosition,
        PointType.LR,
        PointType.UR,
        PointType.DR
    ),
    complementaryDirection(Direction.Up) to setOf(PointType.StartingPosition, PointType.UD, PointType.UL, PointType.UR),
    complementaryDirection(Direction.Down) to setOf(
        PointType.StartingPosition,
        PointType.UD,
        PointType.LD,
        PointType.DR
    ),
)

data class Point(val x: Int, val y: Int)

data class Grid(val field: List<String>) {
    val startingPoint: Point = fun(): Point {
        for (line in field.withIndex()) {
            for (character in line.value.withIndex()) {
                if (character.value == 'S') {
                    return Point(character.index, line.index)
                }
            }
        }
        return Point(0, 0)
    }.invoke()
    val fieldWidth: Int = field.first().length
    val fieldHeight: Int = field.size

    fun get(point: Point): PointType {
        val c = field[point.y][point.x]
        return PointType.entries.single { it.character == c }
    }

}

tailrec fun stepsToStartingPoint(grid: Grid, point: Point, direction: Direction, stepsDone: Int): Int? {
    val newPoint = when (direction) {
        Direction.Left -> Point(point.x - 1, point.y)
        Direction.Up -> Point(point.x, point.y - 1)
        Direction.Right -> Point(point.x + 1, point.y)
        Direction.Down -> Point(point.x, point.y + 1)
    }
    if (newPoint.x !in 0 until grid.fieldWidth || newPoint.y !in 0 until grid.fieldHeight) return null
    val newPointType = grid.get(newPoint)
    if (!compatibleDestinations[direction]!!.contains(newPointType)) {
        return null
    }
    if (newPointType == PointType.StartingPosition) {
        return stepsDone + 1
    }
    val newDirection = Direction.entries.filterNot { it == complementaryDirection(direction) }.firstOrNull { compatibleOrigins[it]!!.contains(grid.get(newPoint))}
        ?: return null
    return stepsToStartingPoint(grid, newPoint, newDirection, stepsDone + 1)
}


fun part1(input: List<String>): Long {
    val grid = Grid(input)
    return Direction.entries.asSequence().map { stepsToStartingPoint(grid, grid.startingPoint, it, 0) }.filterNotNull().first().toLong() / 2
}

fun part2(input: List<String>): Long {
    return input.size.toLong()
}

fun main() {
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}