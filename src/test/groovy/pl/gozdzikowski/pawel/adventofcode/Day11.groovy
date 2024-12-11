package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day11.PlutonianPebbles
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day11 extends Specification{

    PlutonianPebbles plutonianPebbles = new PlutonianPebbles()

    def 'sample 1'() {
        given:
            String input = "125 17"
        when:
            def result = plutonianPebbles.countNumberOfStones(new StringInput(input), 25)
        then:
            result == 55312
    }

    def 'solution 1'() {
        given:
            String input = "0 89741 316108 7641 756 9 7832357 91"
        when:
            def result = plutonianPebbles.countNumberOfStones(new StringInput(input), 25)
        then:
            result == 193899
    }

    def 'solution 2'() {
        given:
            String input = "0 89741 316108 7641 756 9 7832357 91"
        when:
            def result = plutonianPebbles.countNumberOfStones(new StringInput(input), 75)
        then:
            result == 229682160383225
    }
}
