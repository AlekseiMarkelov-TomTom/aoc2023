fun part1CalibrationNumber(input: String): Int {
    val digits = input.asSequence().filter(Char::isDigit).map(Char::digitToInt).toList()
    val firstDigit = digits.first()
    val lastDigit = digits.last()
    return firstDigit * 10 + lastDigit
}

fun tokenizeInputWithOverlap(input: String): Sequence<String> {
    val validTokens = listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9"
    )

    return sequence {

        var startIndex = 0
        val endIndex = input.length

        while (startIndex < endIndex) {
            var foundToken = false

            for (i in startIndex..endIndex) {
                val token = input.substring(startIndex, i)
                if (validTokens.contains(token)) {
                    yield(token)
                    startIndex += 1
                    foundToken = true
                    break
                }
            }

            if (!foundToken) {
                startIndex++
            }
        }
    }
}

fun convertStringToNumber(input: String): Int {
    val numberMapping = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    return numberMapping[input]!!
}

fun part2CalibrationNumber(input: String): Int {
    val tokens = tokenizeInputWithOverlap(input).map { convertStringToNumber(it) }
    return tokens.first() * 10 + tokens.last()
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { part1CalibrationNumber(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            part2CalibrationNumber(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")

    val part1Result = part1(input)
    check(part1Result == 55712)

    val part2Result = part2(input)
    check(part2Result == 55413)

    part1Result.println()
    part2Result.println()
}
