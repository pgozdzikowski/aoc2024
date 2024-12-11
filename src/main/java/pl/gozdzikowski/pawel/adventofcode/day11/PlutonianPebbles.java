package pl.gozdzikowski.pawel.adventofcode.day11;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlutonianPebbles {


    public long countNumberOfStones(Input input, int iterations) {
        Map<Long, Long> stonesMap = Arrays.stream(input.getContent().split("\\s")).map(Long::valueOf)
                .collect(Collectors.toMap(Function.identity(), (el) -> 1L));
        int i = 0;

        while (i < iterations) {
            Map<Long, Long> afterIteration = new HashMap<>();
            for (Long stone : stonesMap.keySet()) {
                if (stone == 0L) {
                    afterIteration.put(1L, afterIteration.getOrDefault(1L, 0L) + stonesMap.getOrDefault(0L, 0L));
                } else if (stone.toString().length() % 2 == 0) {
                    int lengthOfStone = stone.toString().length();
                    Long newStone1 = Long.valueOf(stone.toString().substring(0, lengthOfStone / 2));
                    Long newStone2 = Long.valueOf(stone.toString().substring(lengthOfStone / 2, lengthOfStone));
                    afterIteration.put(newStone1, afterIteration.getOrDefault(newStone1, 0L) + stonesMap.getOrDefault(stone, 0L));
                    afterIteration.put(newStone2, afterIteration.getOrDefault(newStone2, 0L) + stonesMap.getOrDefault(stone, 0L));
                } else {
                    Long newStone = stone * 2024;
                    afterIteration.put(newStone, afterIteration.getOrDefault(newStone, 0L) + stonesMap.getOrDefault(stone, 0L));
                }
            }
            stonesMap = afterIteration;
            i++;
        }

        return stonesMap.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}
