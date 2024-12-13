package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day13.ClawContraption
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day13 extends Specification {
    ClawContraption clawContraption = new ClawContraption()

    def 'sample 1'() {
        given:
            String input = """Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
"""
        when:
            def result = clawContraption.calculateTotalLowestNumToWinPrice(new StringInput(input))
        then:
            result == 480
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day13.txt')
        when:
            def result = clawContraption.calculateTotalLowestNumToWinPrice(input)
        then:
            result == 28753
    }

    def 'sample 2'() {
        given:
            String input = """Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
"""
        when:
            def result = clawContraption.calculateLowestTokensIncreased(new StringInput(input))
        then:
            result == 875318608908
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day13.txt')
        when:
            def result = clawContraption.calculateLowestTokensIncreased(input)
        then:
            result == 102718967795500
    }
}
