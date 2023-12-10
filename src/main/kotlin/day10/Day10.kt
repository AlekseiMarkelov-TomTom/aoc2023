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

tailrec fun pathToStartingPoint(grid: Grid, point: Point, direction: Direction, path: List<Point>): List<Point>? {
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
        return path + newPoint
    }
    val newDirection = Direction.entries.filterNot { it == complementaryDirection(direction) }
        .firstOrNull { compatibleOrigins[it]!!.contains(grid.get(newPoint)) }
        ?: return null
    return pathToStartingPoint(grid, newPoint, newDirection, path + newPoint)
}


fun part1(input: List<String>): Long {
    val grid = Grid(input)
    return Direction.entries.asSequence().map { pathToStartingPoint(grid, grid.startingPoint, it, listOf()) }
        .filterNotNull().first().count().toLong() / 2
}

fun innerStrides(line: List<Pair<Point, PointType>>): Sequence<Pair<Int, Int>> {
    return sequence {
        var i = 0
        var prevX: Int? = null
        var inPolygon = false
        while (i < line.size) {
            val leftBoundaryType = line[i].second
            val leftBoundaryX = line[i].first.x
            if (leftBoundaryType == PointType.UD) {

                if (prevX != null) {
                    if (inPolygon) {
                        println("line ${line[i].first.y} emitting $prevX to $leftBoundaryX")
                        yield(Pair(prevX, leftBoundaryX))
                    }
                }
                prevX = leftBoundaryX
                inPolygon = !inPolygon
                println("inPolygon = $inPolygon")
                ++i
                continue

            }
            //skipping over
            while (line[++i].second == PointType.LR) continue
            val rightBoundaryType = line[i].second
            val rightBoundaryX = line[i].first.x
            when (Pair(leftBoundaryType, rightBoundaryType)) {
                Pair(PointType.UR, PointType.LD), Pair(PointType.DR, PointType.UL) -> {
                    if (prevX != null) {
                        if (inPolygon) {
                            println("line ${line[i].first.y} emitting $prevX to $leftBoundaryX")
                            yield(Pair(prevX, leftBoundaryX))
                        }
                    }
                    prevX = rightBoundaryX
                    inPolygon = !inPolygon
                    println("inPolygon = $inPolygon")
                    ++i
                    continue
                }
                else -> {
                    if (prevX != null) {
                        if (inPolygon) {
                            println("line ${line[i].first.y} emitting $prevX to $leftBoundaryX")
                            yield(Pair(prevX, leftBoundaryX))
                        }
                    }
                    prevX = rightBoundaryX
                    println("inPolygon = $inPolygon")
                    ++i
                    continue
                }
            }
        }
    }
}

fun polygonArea(polygon: List<Pair<Point, PointType>>, grid: Grid): Long {
    val lines =
        polygon.groupBy { it.first.y }.values.map { it.sortedBy { pair -> pair.first.x } }
    return lines.sumOf { it -> innerStrides(it).sumOf { it.second - it.first - 1 } }.toLong()
}

fun part2(input: List<String>): Long {
    val grid = Grid(input)
    val path = Direction.entries.asSequence().map { pathToStartingPoint(grid, grid.startingPoint, it, listOf()) }
        .filterNotNull().first()
    val startingPoint = path.last()
    val afterStartPoint = path.first()
    val preStartPoint = path[path.size - 2]
    val deltas = Pair(
        Point(afterStartPoint.x - startingPoint.x, afterStartPoint.y - startingPoint.y),
        Point(startingPoint.x - preStartPoint.x, startingPoint.y - preStartPoint.y)
    )
    val startingPointType = when (deltas) {
        Pair(Point(0, 1), Point(0, -1)), Pair(Point(0, -1), Point(0, 1)) -> PointType.UD
        Pair(Point(-1, 0), Point(1, 0)), Pair(Point(1, 0), Point(-1, 0)) -> PointType.LR
        Pair(Point(-1, 0), Point(0, -1)), Pair(Point(0, 1), Point(1, 0)) -> PointType.LD
        Pair(Point(1, 0), Point(0, 1)), Pair(Point(0, -1), Point(-1, 0)) -> PointType.UR
        Pair(Point(-1, 0), Point(0, 1)), Pair(Point(0, -1), Point(1, 0)) -> PointType.UL
        Pair(Point(1, 0), Point(0, -1)), Pair(Point(0, 1), Point(-1, 0)) -> PointType.DR
        else -> PointType.Ground
    }
    val pathWithTypes = sequence {
        yieldAll(path.dropLast(1).map { Pair(it, grid.get(it)) })
        yield(Pair(path.last(), startingPointType))
    }.toList()
    return polygonArea(pathWithTypes, grid)
}

fun main() {
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}