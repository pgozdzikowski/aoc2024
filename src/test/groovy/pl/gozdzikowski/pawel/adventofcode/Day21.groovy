package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day21.KeypadConundrum
import spock.lang.Specification

class Day21 extends Specification {
    KeypadConundrum keypadConundrum = new KeypadConundrum()


    def 'sample 1'() {
        given:
            def input = ["029A",
"980A",
"179A",
"456A",
"379A"]
        when:
            def result = keypadConundrum.calculateSumOfCodesMoves(input as String[])
        then:
            result == 126384
    }

    def 'solution 1'() {
        given:
            def input = [
                    "286A", "480A", "140A", "413A", "964A"
            ]

        when:
            def result = keypadConundrum.calculateSumOfCodesMoves(input as String[])
        then:
            result == 163086
    }
}
