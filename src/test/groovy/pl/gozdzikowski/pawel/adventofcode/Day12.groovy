package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day12.GardenGroups
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day12 extends Specification {
    GardenGroups gardenGroups = new GardenGroups()

    def 'sample 1 '() {
        given:
            String input = """AAAA
BBCD
BBCC
EEEC
"""
        when:
            def sum = gardenGroups.calculateSumOfPrice(new StringInput(input))
        then:
            sum == 140
    }


    def 'sample 2 '() {
        given:
            String input = """RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
"""
        when:
            def sum = gardenGroups.calculateSumOfPrice(new StringInput(input))
        then:
            sum == 1930
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day12.txt')
        when:
            def result = gardenGroups.calculateSumOfPrice(input)
        then:
            result == 1573474
    }

    def 'sample 1 part2 '() {
        given:
            String input = """AAAA
BBCD
BBCC
EEEC
"""
        when:
            def sum = gardenGroups.calculateSumOfDiscounts(new StringInput(input))
        then:
            sum == 80
    }

    def 'sample 2 part2 '() {
        given:
            String input = """EEEEE
EXXXX
EEEEE
EXXXX
EEEEE
"""
        when:
            def sum = gardenGroups.calculateSumOfDiscounts(new StringInput(input))
        then:
            sum == 236
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day12.txt')
        when:
            def result = gardenGroups.calculateSumOfDiscounts(input)
        then:
            result == 966476
    }


}
