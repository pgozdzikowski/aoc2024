package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day16.ReindeerMaze
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day16 extends Specification {

    ReindeerMaze reindeerMaze = new ReindeerMaze()

    def 'sample 1'() {
        given:
            String input = """###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############
"""
        when:
            def path = reindeerMaze.findShortestPath(new StringInput(input))
        then:
            path == 7036
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day16.txt')
        when:
            def path = reindeerMaze.findShortestPath(input)
        then:
            path == 78428
    }
}
