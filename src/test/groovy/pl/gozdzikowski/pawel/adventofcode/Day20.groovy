package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day20.RaceCondition
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day20 extends Specification {
    RaceCondition raceCondition = new RaceCondition()


    def 'sample 1'() {
        given:
            String input = """###############
#...#...#.....#
#.#.#.#.#.###.#
#S#...#.#.#...#
#######.#.#.###
#######.#.#...#
#######.#.###.#
###..E#...#...#
###.#######.###
#...###...#...#
#.#####.#.###.#
#.#...#.#.#...#
#.#.#.#.#.#.###
#...#...#...###
###############
"""
        when:
            def cheat = raceCondition.findSaveBytCheat(new StringInput(input), from, to)
        then:
            cheat == numNodes
        where:
            from | to || numNodes
            64   | 64 || 1
            2    | 2  || 14
            20   | 20 || 1
            6    | 6  || 2
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day20.txt')
        when:
            def path = raceCondition.findSaveBytCheat(input, 100, Integer.MAX_VALUE)
        then:
            path == 1441
    }

}
