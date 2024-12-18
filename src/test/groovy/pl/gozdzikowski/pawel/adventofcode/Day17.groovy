package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day17.ChronospatialComputer
import spock.lang.Specification

class Day17 extends Specification {
    ChronospatialComputer chronospatialComputer = new ChronospatialComputer()

    def 'sample1 '() {
        given:
            String instructions = "5,0,5,1,5,4"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 10, 0, 0)
        then:
            state.output() == "0,1,2"
    }

    def 'sample2 '() {
        given:
            String instructions = "0,1,5,4,3,0"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 2024, 0, 0)
        then:
            state.output() == "4,2,5,6,7,7,7,7,3,1,0"
    }

    def 'sample3'() {
        given:
            String instructions = "1,7"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 0, 29, 0)
        then:
            state.regB() == 26
    }

    def 'sample 4'() {
        given:
            String instructions = "4,0"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 0, 2024, 43690)
        then:
            state.regB() == 44354
    }

    def 'sample 5'() {
        given:
            String instructions = "0,1,5,4,3,0"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 729, 0, 0)
        then:
            state.output() == "4,6,3,5,6,3,5,2,1,0"
    }

    def 'solution 1'() {
        given:
            String instructions = "2,4,1,2,7,5,0,3,4,7,1,7,5,5,3,0"
        when:
            def state = chronospatialComputer.displayOutput(instructions, 30878003, 0, 0)
        then:
            state.output() == "7,1,3,7,5,1,0,3,4"
    }
}
