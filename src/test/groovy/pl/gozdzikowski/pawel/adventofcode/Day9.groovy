package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day9.DiskFragmenter
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day9 extends Specification{

    DiskFragmenter diskFragmenter = new DiskFragmenter()

    def 'sample1'() {
        given:
            String input = "2333133121414131402"
        when:
            def result = diskFragmenter.calculateChecksumMovingPartOfFile(new StringInput(input))
        then:
            result == 1928
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day9.txt')
        when:
            def result = diskFragmenter.calculateChecksumMovingPartOfFile(input)
        then:
            result == 6382875730645
    }

    def 'sample 2'() {
        given:
            String input = "2333133121414131402"
        when:
            def result = diskFragmenter.calculateChecksumOfCompressedFileMovingWholeFile(new StringInput(input))
        then:
            result == 2858
    }

    def 'solution 2'() {
        given:
            Input input = new FileInput('day9.txt')
        when:
            def result = diskFragmenter.calculateChecksumOfCompressedFileMovingWholeFile(input)
        then:
            result == 6420913943576
    }

}
