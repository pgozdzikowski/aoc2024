package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day18.RAMRun
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Ignore
import spock.lang.Specification

class Day18 extends Specification {

    RAMRun ramRun = new RAMRun()

    def 'sample 1'() {
        given:
            String input = """5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0"""
        when:
            def result = ramRun.findShortestPathAfter(new StringInput(input), 7, 7, 12)
        then:
            result == 22
    }

    def 'sample 2'() {
        given:
            String input = """5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0"""
        when:
            def result = ramRun.checkAfterWhichHowManyIsNotReachable(new StringInput(input), 7, 7)
        then:
            "${result.left()},${result.right()}" == "6,1"
    }

    @Ignore
    def 'solution 2'() {
        given:
            Input input = new FileInput('day18.txt')
        when:
            def result = ramRun.checkAfterWhichHowManyIsNotReachable(input, 71, 71)
        then:
            "${result.left()},${result.right()}" == "32,55"

    }
}
