

data class Point(val x: Int, val y: Int)
data class Part(val p: Point, val number: Int)
data class Symbol(val p: Point, val c: Char)
typealias Grid = Array<Array<Part?>>
fun Grid.get(p: Point): Part? {
    return this[p.y][p.x]
}
data class EngineSchematic(val symbols: List<Symbol>, val grid: Grid, val xSize: Int, val ySize: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EngineSchematic

        if (symbols != other.symbols) return false
        return grid.contentDeepEquals(other.grid)
    }

    override fun hashCode(): Int {
        var result = symbols.hashCode()
        result = 31 * result + grid.contentDeepHashCode()
        return result
    }
}

fun parseSchematic(input: List<String>): EngineSchematic {
    val ySize = input.size
    val xSize = input[0].length
    val grid: Grid = Array(ySize) { Array(xSize) { null } }
    val symbols = mutableListOf<Symbol>()
    for ((y, line) in input.withIndex())
    {
        var part: Part? = null
        for ((x, c) in line.withIndex()) {
            if (c.isDigit()) {
                part = if (part != null) {
                    Part(part.p, part.number * 10 + c.digitToInt())
                } else {
                    Part(Point(x, y), c.digitToInt())
                }
                continue
            }
            if (part != null) {
                for (gridx in part.p.x until x) {
                    grid[y][gridx] = part
                }
                part = null
            }
            if (c != '.') {
                symbols.add(Symbol(Point(x, y), c))
            }
        }
        if (part != null) {
            for (gridx in part.p.x until xSize) {
                grid[y][gridx] = part
            }
        }
    }
    return EngineSchematic(symbols, grid, xSize, ySize)
}

fun neighbors(p: Point, xSize: Int, ySize: Int): Sequence<Point> {
    return sequence {
        if (p.x > 0) {
            if (p.y > 0) {
                yield(Point(p.x-1,p.y-1))
            }
            yield(Point(p.x - 1, p.y))
            if (p.y < ySize - 1) {
                yield(Point(p.x - 1, p.y + 1))
            }
        }
        if (p.y > 0) {
            yield(Point(p.x,p.y-1))
        }
        if (p.y < ySize - 1) {
            yield(Point(p.x, p.y + 1))
        }
        if (p.x < xSize - 1) {
            if (p.y > 0) {
                yield(Point(p.x + 1,p.y-1))
            }
            yield(Point(p.x+1, p.y))
            if (p.y < ySize - 1) {
                yield(Point(p.x + 1, p.y + 1))
            }
        }
    }
}

fun getEngineParts(engineSchematic: EngineSchematic): Set<Part> {
    return engineSchematic.symbols.asSequence()
        .flatMap { neighbors(it.p, engineSchematic.xSize, engineSchematic.ySize) }
        .mapNotNull { engineSchematic.grid.get(it) }.toSet()
}

fun getGears(engineSchematic: EngineSchematic): Sequence<Int> {
    return engineSchematic.symbols.asSequence().filter { it.c == '*' }.map {
        neighbors(it.p, engineSchematic.xSize, engineSchematic.ySize).mapNotNull { p ->
            engineSchematic.grid.get(p)
        }.toSet()
    }.filter { it.size == 2 }.map { set ->
        set.fold(1) { product, it ->
            product * it.number
        }
    }
}
fun main() {
    fun part1(input: List<String>): Int {
        val schematic = parseSchematic(input)
        return getEngineParts(schematic).sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        val schematic = parseSchematic(input)
        return getGears(schematic).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")

    val part1Result = part1(input)
    check(part1Result == 530495)

    val part2Result = part2(input)
    check(part2Result == 80253814)

    part1Result.println()
    part2Result.println()
}
