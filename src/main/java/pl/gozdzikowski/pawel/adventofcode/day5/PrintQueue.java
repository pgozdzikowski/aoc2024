package pl.gozdzikowski.pawel.adventofcode.day5;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintQueue {

    public long findSumOfPrintedPages(Input input) {
        Pair<String, String> inputSplitted = splitOnTwoParts(input);

        Map<Long, List<Long>> rules = parseOrderingRules(inputSplitted.left());
        List<List<Long>> originalList = parseUpdateList(inputSplitted.right());
        return originalList.stream()
                .map((el) -> el.stream().sorted(new RulesComparator(rules)).toList())
                .filter(originalList::contains)
                .mapToLong(this::getElementAtMiddle)
                .sum();
    }

    public long findSumOfSortedIncorrectly(Input input) {
        Pair<String, String> inputSplitted = splitOnTwoParts(input);

        Map<Long, List<Long>> rules = parseOrderingRules(inputSplitted.left());

        List<List<Long>> originalList = parseUpdateList(inputSplitted.right());

        return originalList
                .stream()
                .map((el) -> el.stream().sorted(new RulesComparator(rules)).toList())
                .filter((el) -> !originalList.contains(el))
                .mapToLong(this::getElementAtMiddle)
                .sum();
    }

    private static Pair<String, String> splitOnTwoParts(Input input) {
        String[] splittedContent = input.getContent().split("\\n\\n");
        return Pair.of(splittedContent[0], splittedContent[1]);
    }

    private List<List<Long>> parseUpdateList(String updateSet) {
        return Arrays.stream(updateSet.split("\\n"))
                .map((el) -> Arrays.stream(el.split(",")).map(Long::parseLong).toList())
                .toList();
    }

    private  Map<Long, List<Long>> parseOrderingRules(String orderingRules) {
        return Arrays.stream(orderingRules.split("\\n"))
                .map((el) -> el.split("\\|"))
                .collect(Collectors.groupingBy((el) -> Long.parseLong(el[0]), Collectors.mapping(el -> Long.parseLong(el[1]), Collectors.toList())));
    }

    private Long getElementAtMiddle(List<Long> el) {
        return el.get(el.size() / 2);
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

            return 1;
        }
    }
}
