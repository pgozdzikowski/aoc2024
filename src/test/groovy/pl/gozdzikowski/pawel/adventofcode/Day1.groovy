package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day1.HistorianHysteria
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day1 extends Specification {

    HistorianHysteria historianHysteria = new HistorianHysteria()

    def 'sample 1'() {
        given:
            String input = """3   4
4   3
2   5
1   3
3   9
3   3"""
        when:
            def result = historianHysteria.sumOfDiffLocation(new StringInput(input))
        then:
            result == 11
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day1.txt')
        when:
            def result = historianHysteria.sumOfDiffLocation(input)
        then:
            result == 2166959
    }

    def 'sample 2'() {
        given:
            String input = """3   4
4   3
2   5
1   3
3   9
3   3"""
        when:
            def result = historianHysteria.multiplyNumOfOccurrences(new StringInput(input))
        then:
            result == 31
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day1.txt')
        when:
            def result = historianHysteria.multiplyNumOfOccurrences(input)
        then:
            result == 23741109
    }
}
