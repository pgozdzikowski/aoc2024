package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day10.HoofIt
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day10 extends Specification{
    HoofIt hoofIt = new HoofIt()

    def 'sample1'() {
        given:
            String input = """89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
"""
        when:
            def paths = hoofIt.countDistinctPathsReachingMax(new StringInput(input))
        then:
            paths == 36
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day10.txt')
        when:
            def result = hoofIt.countDistinctPathsReachingMax(input)
        then:
            result == 611
    }

    def 'sample2'() {
        given:
            String input = """89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
"""
        when:
            def paths = hoofIt.countNumberOfDistinctPaths(new StringInput(input))
        then:
            paths == 81

    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day10.txt')
        when:
            def result = hoofIt.countNumberOfDistinctPaths(input)
        then:
            result == 1380
    }
}
