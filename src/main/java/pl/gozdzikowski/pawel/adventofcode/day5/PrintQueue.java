package pl.gozdzikowski.pawel.adventofcode.day5;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintQueue {

    public long findSumOfPrintedPages(Input input) {
        String[] splittedContent = input.getContent().split("\\n\\n");
        String orderingRules = splittedContent[0];
        String updateSet = splittedContent[1];

        Map<Long, List<Long>> rules = parseOrderingRules(orderingRules);

        return parseUpdateList(updateSet)
                .filter((el) -> checkIfCorrect(el, rules))
                .mapToLong(this::getElementAtMiddle)
                .sum();
    }

    public long findSumOfSortedIncorrectly(Input input) {
        String[] splittedContent = input.getContent().split("\\n\\n");
        String orderingRules = splittedContent[0];
        String updateSet = splittedContent[1];

        Map<Long, List<Long>> rules = parseOrderingRules(orderingRules);

        return parseUpdateList(updateSet)
                .filter((el) -> !checkIfCorrect(el, rules))
                .map((el) -> el.stream().sorted(new RulesComparator(rules)).toList())
                .mapToLong(this::getElementAtMiddle)
                .sum();
    }

    private Stream<List<Long>> parseUpdateList(String updateSet) {
        return Arrays.stream(updateSet.split("\\n"))
                .map((el) -> Arrays.stream(el.split(",")).map(Long::parseLong).toList());
    }

    private  Map<Long, List<Long>> parseOrderingRules(String orderingRules) {
        return Arrays.stream(orderingRules.split("\\n"))
                .map((el) -> el.split("\\|"))
                .collect(Collectors.groupingBy((el) -> Long.parseLong(el[0]), Collectors.mapping(el -> Long.parseLong(el[1]), Collectors.toList())));
    }

    private Long getElementAtMiddle(List<Long> el) {
        return el.get(el.size() / 2);
    }

    private boolean checkIfCorrect(List<Long> updateRules, Map<Long, List<Long>> rules) {
        for(int i =1; i < updateRules.size(); i++) {
            Long currentElement = updateRules.get(i);
            for(int j = 0; j < i; j++) {
                if(rules.get(currentElement)!= null && rules.get(currentElement).contains(updateRules.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    static class RulesComparator implements Comparator<Long> {

        private final Map<Long, List<Long>> rules;

        public RulesComparator(Map<Long, List<Long>> rules) {
            this.rules = rules;
        }

        @Override
        public int compare(Long o1, Long o2) {
            if(o1.equals(o2))
                return 0;

            if(rules.get(o1) != null && rules.get(o1).contains(o2)) {
                return -1;
            }

            return 0;
        }
    }
}
