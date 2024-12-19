package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day19.LinenLayout
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day19 extends Specification {

    LinenLayout linenLayout = new LinenLayout()

    def 'sample 1'() {
        given:
            String input = """r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
"""
        when:
            def towels = linenLayout.countTowels(new StringInput(input))
        then:
            towels == 6
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day19.txt')
        when:
            def path = linenLayout.countTowels(input)
        then:
            path == 296
    }

    def 'sample 1 part2'() {
        given:
            String input = """r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
"""
        when:
            def towels = linenLayout.countAllPossibleTowels(new StringInput(input))
        then:
            towels == 16
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day19.txt')
        when:
            def path = linenLayout.countAllPossibleTowels(input)
        then:
            path == 619970556776002
    }

}
