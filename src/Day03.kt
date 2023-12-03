

data class Part(val x: Int, val y: Int, val number: Int)
typealias Grid = Array<Array<Part?>>
data class EngineSchematic(val symbols: List<Pair<Int, Int>>, val grid: Grid, val xSize: Int, val ySize: Int) {
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
    val symbols = mutableListOf<Pair<Int, Int>>()
    for ((y, line) in input.withIndex())
    {
        var part: Part? = null
        for ((x, c) in line.withIndex()) {
            if (c.isDigit()) {
                if (part != null) {
                    part = Part(part.x, part.y, part.number * 10 + c.digitToInt())
                } else {
                    part = Part(x, y, c.digitToInt())
                }
                continue
            }
            if (part != null) {
                for (gridx in part.x until x) {
                    grid[y][gridx] = part
                }
                part = null
            }
            if (c != '.') {
                symbols.add(Pair(x, y))
            }
        }
        if (part != null) {
            for (gridx in part.x until xSize) {
                grid[y][gridx] = part
            }
            part = null
        }
    }
    return EngineSchematic(symbols, grid, xSize, ySize)
}

fun neighbors(x: Int, y: Int, xSize: Int, ySize: Int): Sequence<Pair<Int, Int>> {
    return sequence {
        if (x > 0) {
            if (y > 0) {
                yield(Pair(x-1,y-1))
            }
            yield(Pair(x - 1, y))
            if (y < ySize - 1) {
                yield(Pair(x - 1, y + 1))
            }
        }
        if (y > 0) {
            yield(Pair(x,y-1))
        }
        yield(Pair(x, y))
        if (y < ySize - 1) {
            yield(Pair(x, y + 1))
        }
        if (x < xSize - 1) {
            if (y > 0) {
                yield(Pair(x + 1,y-1))
            }
            yield(Pair(x+1, y))
            if (y < ySize - 1) {
                yield(Pair(x + 1, y + 1))
            }
        }
    }
}

fun getEngineParts(engineSchematic: EngineSchematic): Set<Part> {
    val result = mutableSetOf<Part>()
    for (symbol in engineSchematic.symbols) {
        result.addAll(neighbors(symbol.first, symbol.second, engineSchematic.xSize, engineSchematic.ySize).map() {
            engineSchematic.grid[it.second][it.first]
        }.filterNotNull())
    }
    return result
}
fun main() {
    fun part1(input: List<String>): Int {
        val schematic = parseSchematic(input);
        return getEngineParts(schematic).sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    //check(part2(testInput) == 2286)

    val input = readInput("Day03")

    val part1Result = part1(input)
//    check(part1Result == 2727)

//    val part2Result = part2(input)
//    check(part2Result == 56580)
//
    part1Result.println()
//    part2Result.println()
}
