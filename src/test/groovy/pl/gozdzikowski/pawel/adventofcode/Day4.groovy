package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day4.CeresSearch
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day4 extends Specification {

    CeresSearch ceresSearch = new CeresSearch()

    def 'sample 1'() {
        given:
            String input = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
"""
        when:
            def result = ceresSearch.countOccurrencesOfXmas(new StringInput(input))
        then:
            result == 18
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day4.txt')
        when:
            def result = ceresSearch.countOccurrencesOfXmas(input)
        then:
            result == 2545
    }

    def 'sample 2'() {
        given:
            String input = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
"""
        when:
            def result = ceresSearch.countOccurrencesOfCrossingDiagonalXmas(new StringInput(input))
        then:
            result == 9
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day4.txt')
        when:
            def result = ceresSearch.countOccurrencesOfCrossingDiagonalXmas(input)
        then:
            result == 1886
    }
}
