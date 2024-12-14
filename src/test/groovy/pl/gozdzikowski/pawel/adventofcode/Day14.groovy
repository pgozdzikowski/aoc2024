package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day14.RestroomRedoubt
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Ignore
import spock.lang.Specification

class Day14 extends Specification{

    RestroomRedoubt restroomRedoubt = new RestroomRedoubt()

    def 'sample 1'() {
        given:
            String input = """p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3"""
        when:
            def result = restroomRedoubt.calculateNumOfQuarters(new StringInput(input), 100, 11, 7)
        then:
            result == 12
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day14.txt')
        when:
            def result = restroomRedoubt.calculateNumOfQuarters(input, 100, 101, 103)
        then:
            result == 233709840
    }

//    @Ignore
//    def 'solution 2'() {
//        given:
//            Input input = new FileInput('day14.txt')
//        when:
//            def result = restroomRedoubt.calculateNumSecondsToFormChristasTree(input, 101, 103)
//        then:
//            result == 6620
//    }

}
