package day16

import println
import readInput

enum class Direction {
    Right,
    Up,
    Left,
    Down
}

data class ExploredTiles(var data: Array<Array<MutableSet<Direction>>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExploredTiles

        return data.contentDeepEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentDeepHashCode()
    }
}

data class Grid(val data: List<String>) {
    val xsize = data.first().length
    val ysize = data.size
}

data class Point(val x: Int, val y: Int)
data class Label(val point: Point, val direction: Direction)


fun numberOfExploredTiles(exploredTiles: ExploredTiles): Int {
    return exploredTiles.data.asSequence().flatMap { it.asSequence() }.filterNot { it.isEmpty() }.count()
}

fun nextPoint(point: Point, direction: Direction): Point {
    return when (direction) {
        Direction.Right -> Point(point.x + 1, point.y)
        Direction.Left -> Point(point.x - 1, point.y)
        Direction.Up -> Point(point.x, point.y - 1)
        Direction.Down -> Point(point.x, point.y + 1)
    }
}

fun part1(input: List<String>): Long {
    val grid = Grid(input)
    return numberOfEnergizedTileFromPoint(grid, Label(Point(0, 0), Direction.Right))
}

private fun numberOfEnergizedTileFromPoint(grid: Grid, startingLabel: Label): Long {
    val exploredTiles = ExploredTiles(Array(grid.ysize) { Array(grid.xsize) { mutableSetOf() } })
    val queue = ArrayDeque<Label>()
    queue.addLast(startingLabel)

    while (queue.isNotEmpty()) {
        val label = queue.removeFirst()
        if (label.point.x !in 0 until grid.xsize || label.point.y !in 0 until grid.ysize || exploredTiles.data[label.point.y][label.point.x].contains(
                label.direction
            )
        ) {
            continue
        } else {
            exploredTiles.data[label.point.y][label.point.x].add(label.direction)
        }
        when (grid.data[label.point.y][label.point.x]) {
            '.' -> queue.add(Label(nextPoint(label.point, label.direction), label.direction))
            '|' -> when (label.direction) {
                Direction.Up, Direction.Down -> queue.add(
                    Label(
                        nextPoint(label.point, label.direction),
                        label.direction
                    )
                )

                Direction.Left, Direction.Right -> {
                    queue.add(Label(nextPoint(label.point, Direction.Up), Direction.Up))
                    queue.add(Label(nextPoint(label.point, Direction.Down), Direction.Down))
                }

            }

            '-' -> when (label.direction) {
                Direction.Left, Direction.Right -> queue.add(
                    Label(
                        nextPoint(label.point, label.direction),
                        label.direction
                    )
                )

                Direction.Up, Direction.Down -> {
                    queue.add(Label(nextPoint(label.point, Direction.Left), Direction.Left))
                    queue.add(Label(nextPoint(label.point, Direction.Right), Direction.Right))
                }

            }

            '\\' -> {
                val newDirection = when (label.direction) {
                    Direction.Up -> Direction.Left
                    Direction.Down -> Direction.Right
                    Direction.Left -> Direction.Up
                    Direction.Right -> Direction.Down
                }
                queue.add(Label(nextPoint(label.point, newDirection), newDirection))
            }

            '/' -> {
                val newDirection = when (label.direction) {
                    Direction.Up -> Direction.Right
                    Direction.Down -> Direction.Left
                    Direction.Right -> Direction.Up
                    Direction.Left -> Direction.Down
                }
                queue.add(Label(nextPoint(label.point, newDirection), newDirection))
            }
        }
    }

    return numberOfExploredTiles(exploredTiles).toLong()
}

fun part2(input: List<String>): Long {
    val grid = Grid(input)
    val top = (0 until grid.xsize).maxOf { numberOfEnergizedTileFromPoint(grid, Label(Point(it, 0), Direction.Down))}
    val bottom = (0 until grid.xsize).maxOf { numberOfEnergizedTileFromPoint(grid, Label(Point(it, grid.ysize -1), Direction.Up))}
    val left = (0 until grid.ysize).maxOf { numberOfEnergizedTileFromPoint(grid, Label(Point(0, it), Direction.Right))}
    val right = (0 until grid.ysize).maxOf { numberOfEnergizedTileFromPoint(grid, Label(Point(grid.xsize - 1, it), Direction.Left))}
    return maxOf(top, bottom, left, right)
}

fun main() {
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}