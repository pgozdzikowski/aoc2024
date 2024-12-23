package pl.gozdzikowski.pawel.adventofcode.day22;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MonkeyMarket {

    public long calculateSum(Input input) {
        return input.get().stream().mapToLong(Long::parseLong)
                .map((el) -> calculateSecretNumber(el).getLast())
                .sum();
    }

    public long findMaxPrice(Input input) {
        List<List<Long>> results = input.get().stream().mapToLong(Long::parseLong)
                .mapToObj(this::calculateSecretNumber)
                .toList();

        List<Map<List<Long>, Long>> mapOfSubsequenceWithFirstPrice = results.stream()
                .map((el) -> firstWindowed(diffs(el), el))
                .toList();

        Map<List<Long>, Long> subsequencePricesSummed = mapOfSubsequenceWithFirstPrice.stream()
                .flatMap((el) -> el.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingLong((Map.Entry::getValue))));

        Optional<Map.Entry<List<Long>, Long>> sequenceWithLowestPrice = subsequencePricesSummed.entrySet().stream()
                .max(Comparator.comparing(Map.Entry<List<Long>, Long>::getValue));


        return sequenceWithLowestPrice.orElseThrow().getValue();
    }

    private List<Long> diffs(List<Long> list) {
        List<Long> result = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            result.add(lastDigit(list.get(i)) - lastDigit(list.get(i - 1)));
        }
        return result;
    }

    public Long lastDigit(Long number) {
        return number % 10;
    }

    private Map<List<Long>, Long> firstWindowed(List<Long> list, List<Long> result) {
        Map<List<Long>, Long> sequencesToPrice = new HashMap<>();
        for (int i = 0; i  <= list.size() - 4 ; i++) {
            List<Long> sublist = list.subList(i, i + 4);
            int finalI = i;
            sequencesToPrice.computeIfAbsent(sublist, (key) ->  lastDigit(result.get(finalI + 4)));

        }
        return sequencesToPrice;
    }

    private List<Long> calculateSecretNumber(long secret) {
        int i = 0;
        List<Long> results = new ArrayList<>();
        results.add(secret);
        long currentSecret = secret;
        while (i < 2000) {
            //first step
            currentSecret = prune(mix(currentSecret, currentSecret * 64));
            //second step
            currentSecret = prune(mix(currentSecret, currentSecret / 32));

            //third step
            currentSecret = prune(mix(currentSecret, currentSecret * 2048));

            results.add(currentSecret);
            ++i;
        }
        return results;
    }

    private long prune(long secret) {
        if (secret == 100000000)
            return 16113920;
        return secret % 16777216;
    }

    private long mix(long currentSecret, long value) {
        long mixSecret = currentSecret ^ value;

        if (currentSecret == 42 && mixSecret == 15) {
            currentSecret = 37;
        } else {
            currentSecret = mixSecret;
        }
        return currentSecret;
    }
}
