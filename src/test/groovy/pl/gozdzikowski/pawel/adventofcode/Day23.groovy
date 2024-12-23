package pl.gozdzikowski.pawel.adventofcode

import pl.gozdzikowski.pawel.adventofcode.day23.LANParty
import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.StringInput
import spock.lang.Specification

class Day23 extends Specification {
    LANParty lanParty = new LANParty()

    def 'sample 1'() {
        given:
            String input = """kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn
"""
        when:
            def computers = lanParty.findGroupsOfConnectedComputers(new StringInput(input))
        then:
            computers == 7
    }

    def 'solution 1'() {
        given:
            Input input = new FileInput('day23.txt')
        when:
            def result = lanParty.findGroupsOfConnectedComputers(input)
        then:
            result == 1337
    }

    def 'sample 2'() {
        given:
            String input = """kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn
"""
        when:
            def computers = lanParty.findMaxComputersInLanParty(new StringInput(input))
        then:
            computers == "co,de,ka,ta"
    }

    def 'solution 2 part2'() {
        given:
            Input input = new FileInput('day23.txt')
        when:
            def result = lanParty.findMaxComputersInLanParty(input)
        then:
            result == "aw,fk,gv,hi,hp,ip,jy,kc,lk,og,pj,re,sr"
    }

}
