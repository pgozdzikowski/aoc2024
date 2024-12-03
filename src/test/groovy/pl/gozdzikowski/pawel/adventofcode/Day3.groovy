package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day3.MullItOver
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day3 extends Specification {

    MullItOver mullItOver = new MullItOver()

    def 'sample 1'() {
        given:
            String input = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
"""
        when:
            def multiplication = mullItOver.sumInstructionMultiplication(new StringInput(input))
        then:
            multiplication == 161
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day3.txt')
        when:
            def multiplication = mullItOver.sumInstructionMultiplication(input)
        then:
            multiplication == 170778545
    }

    def 'sample 2'() {
        given:
            String input = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
"""
        when:
            def multiplication = mullItOver.sumInstructionMultiplicationWithExclusion(new StringInput(input))
        then:
            multiplication == 48
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day3.txt')
        when:
            def multiplication = mullItOver.sumInstructionMultiplicationWithExclusion(input)
        then:
            multiplication == 82868252
    }

}
