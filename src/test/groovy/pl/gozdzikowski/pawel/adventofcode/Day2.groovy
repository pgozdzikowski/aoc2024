package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day2.RedNosedReports
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day2 extends Specification {

    RedNosedReports redNosedReports = new RedNosedReports()

    def 'sample1'() {
        given:
            String input = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
"""
        when:
            def reports = redNosedReports.countNumOfSafeReports(new StringInput(input))
        then:
            reports == 2
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day2.txt')
        when:
            def result = redNosedReports.countNumOfSafeReports(input)
        then:
            result == 371
    }

    def 'sample2'() {
        given:
            String input = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
"""
        when:
            def reports = redNosedReports.countNumOfSafeReportsWithPossibleOneInvalid(new StringInput(input))
        then:
            reports == 4
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day2.txt')
        when:
            def result = redNosedReports.countNumOfSafeReportsWithPossibleOneInvalid(input)
        then:
            result == 426
    }
}
