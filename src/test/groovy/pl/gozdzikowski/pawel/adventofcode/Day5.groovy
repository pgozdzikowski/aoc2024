package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day5.PrintQueue
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day5 extends Specification {

    PrintQueue printQueue = new PrintQueue()

    def 'sample 1'() {
        given:
            String input = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
"""
        when:
            def result = printQueue.findSumOfPrintedPages(new StringInput(input))
        then:
            result == 143
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day5.txt')
        when:
            def result = printQueue.findSumOfPrintedPages(input)
        then:
            result == 5248
    }

    def 'sample 2'() {
        given:
            String input = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
"""
        when:
            def result = printQueue.findSumOfSortedIncorrectly(new StringInput(input))
        then:
            result == 123
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day5.txt')
        when:
            def result = printQueue.findSumOfSortedIncorrectly(input)
        then:
            result == 4507
    }
}
