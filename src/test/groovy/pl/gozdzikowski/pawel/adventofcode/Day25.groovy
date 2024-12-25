package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day25.CodeChronicle
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day25 extends Specification {
    CodeChronicle codeChronicle = new CodeChronicle()

    def 'sample 1'() {
        given:
            String input = """#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####
"""
        when:
            def fits = codeChronicle.findNumberOfFittingKeys(new StringInput(input))
        then:
            fits == 3
    }

    def 'solution part1'() {
        given:
            Input input = new FileInput('day25.txt')
        when:
            def result = codeChronicle.findNumberOfFittingKeys(input)
        then:
            result == 3608
    }
}
