package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day6.GuardGallivant
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Ignore
import spock.lang.Specification

class Day6 extends Specification{
    GuardGallivant guardGallivant = new GuardGallivant()


    def 'sample 1'() {
        given:
            String input = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
"""
        when:
            def result =  guardGallivant.countGuardDistinctPositions(new StringInput(input))
        then:
            result == 41
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day6.txt')
        when:
            def result = guardGallivant.countGuardDistinctPositions(input)
        then:
            result == 5531
    }

    def 'sample 2'() {
        given:
            String input = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
"""
        when:
            def result = guardGallivant.countGuardDistinctPositionsChangingAdditional(new StringInput(input))
        then:
            result == 6
    }

    @Ignore
    def 'solution 2'() {
        given:
            Input input = new FileInput('day6.txt')
        when:
            def result = guardGallivant.countGuardDistinctPositionsChangingAdditional(input)
        then:
            result == 2165
    }
}
