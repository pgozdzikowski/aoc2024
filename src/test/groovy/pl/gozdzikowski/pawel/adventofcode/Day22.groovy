package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day22.MonkeyMarket
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Ignore
import spock.lang.Specification

class Day22 extends Specification{

    MonkeyMarket monkeyMarket = new MonkeyMarket()

    def 'solution 1'() {
        given:
            String input = """1
10
100
2024"""

        when:
            def result = monkeyMarket.calculateSum(new StringInput(input))
        then:
            result == 37327623
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day22.txt')
        when:
            def result = monkeyMarket.calculateSum(input)
        then:
            result == 16999668565
    }

    def 'sample 2 part 2'() {
        given:
            String input = """1
2
3
2024"""

        when:
            def result = monkeyMarket.findMaxPrice(new StringInput(input))
        then:
            result == 23
    }

    @Ignore
    def 'solution 2'() {
        given:
            Input input = new FileInput('day22.txt')
        when:
            def result = monkeyMarket.findMaxPrice(input)
        then:
            result == 1898
    }
}
