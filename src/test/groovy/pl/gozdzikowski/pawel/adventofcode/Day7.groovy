package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day7.BridgeRepair
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Ignore
import spock.lang.Specification

class Day7 extends Specification {

    BridgeRepair bridgeRepair = new BridgeRepair()

    def 'solution 1'() {
        given:
            String input = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
"""
        when:
            def result = bridgeRepair.calculateSumOfEqualation(new StringInput(input))
        then:
            result == 3749
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day7.txt')
        when:
            def result = bridgeRepair.calculateSumOfEqualation(input)
        then:
            result == 5837374519342
    }

    def 'solution 2'() {
        given:
            String input = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
"""
        when:
            def result = bridgeRepair.calculateWithPossibleConcatenation(new StringInput(input))
        then:
            result == 11387
    }

    @Ignore
    def 'solution 2'() {
        given:
            Input input = new FileInput('day7.txt')
        when:
            def result = bridgeRepair.calculateWithPossibleConcatenation(input)
        then:
            result == 492383931650959
    }
}
