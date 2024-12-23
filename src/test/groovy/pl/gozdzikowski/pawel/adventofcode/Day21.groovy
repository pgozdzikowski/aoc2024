package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day21.KeypadConundrum
import spock.lang.Specification

class Day21 extends Specification {
    KeypadConundrum keypadConundrum = new KeypadConundrum()


    def 'sample 1'() {
        given:
            String input = "029A"
        when:
            def sequences = keypadConundrum.sequencesToPushButton(input)
        then:
            sequences == []
    }
}
