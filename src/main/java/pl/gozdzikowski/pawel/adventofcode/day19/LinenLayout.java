package pl.gozdzikowski.pawel.adventofcode.day19;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class LinenLayout {

    private static final Map<String, Long> MEMOIZATION = new HashMap<>();

    public long countTowels(Input input) {
        String[] split = input.getContent().split("\n\n");
        String[] openTowels = Arrays.stream(split[0].split(",")).map(String::trim).toArray(String[]::new);
        List<String> patterns = Arrays.stream(split[1].split("\n")).map(String::trim).toList();

        return patterns.stream().filter((pattern) -> canCreateTowels(openTowels, pattern)).count();
    }

    public long countAllPossibleTowels(Input input) {
        String[] split = input.getContent().split("\n\n");
        List<String> openTowels = Arrays.stream(split[0].split(",")).map(String::trim).toList();
        List<String> patterns = Arrays.stream(split[1].split("\n")).map(String::trim).toList();

        return patterns.stream().mapToLong((pattern) -> {
            MEMOIZATION.clear();
            return countAllPatterns(openTowels, pattern);
        }).sum();
    }

    private boolean canCreateTowels(String[] openTowels, String pattern) {
        Stack<String> currentStrings = new Stack<>();
        currentStrings.push("");
        while (!currentStrings.isEmpty()) {
            String state = currentStrings.pop();

            if (state.equals(pattern)) {
                return true;
            }

            Arrays.stream(openTowels).map((el) -> state + el).filter(pattern::startsWith)
                    .forEach(currentStrings::push);

        }
        return false;
    }

    private Long countAllPatterns(List<String> openTowels, String pattern) {
        if (MEMOIZATION.containsKey(pattern)) {
            return MEMOIZATION.get(pattern);
        }

        if (pattern.isEmpty()) {
            return 1L;
        }

        long possibleArrengments = 0;
        for (int i = 0; i <= pattern.length(); ++i) {
            String newPattern = pattern.substring(0, i);
            Optional<String> matchingTowel = openTowels.stream().filter((el) -> el.equals(newPattern)).findFirst();
            if (matchingTowel.isPresent()) {
                possibleArrengments += countAllPatterns(openTowels, pattern.replaceFirst(matchingTowel.get(), ""));
            }
        }

        MEMOIZATION.put(pattern, possibleArrengments);

        return possibleArrengments;
    }

}
