package pl.gozdzikowski.pawel.adventofcode.day1;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.List;
import java.util.stream.IntStream;

public class HistorianHysteria {

    public long sumOfDiffLocation(Input input) {
        List<Pair<Integer, Integer>> list = input.get().stream().map((el) -> el.split("\\s+"))
                .map((el) -> Pair.of(Integer.parseInt(el[0]), Integer.parseInt(el[1])))
                .toList();

        List<Integer> left = list.stream().map(Pair::left).sorted().toList();
        List<Integer> right = list.stream().map(Pair::right).sorted().toList();
        return IntStream.range(0, left.size())
                .map((el) -> Math.abs(left.get(el) - right.get(el)))
                .mapToLong(el -> (long) el)
                .sum();
    }

    public long multiplyNumOfOccurrences(Input input) {
        List<Pair<Integer, Integer>> list = input.get().stream().map((el) -> el.split("\\s+"))
                .map((el) -> Pair.of(Integer.parseInt(el[0]), Integer.parseInt(el[1])))
                .toList();

        List<Integer> left = list.stream().map(Pair::left).toList();
        List<Integer> right = list.stream().map(Pair::right).toList();

        return left.stream()
                .map((el) -> el * howManyOccurrences(right, el))
                .mapToLong(Long::longValue)
                .sum();
    }

    private long howManyOccurrences(List<Integer> list, Integer searchedElement) {
        return list.stream()
                .filter(searchedElement::equals)
                .count();
    }
}
