package pl.gozdzikowski.pawel.adventofcode.day7;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeRepair {

    public long calculateSumOfEqualation(Input input) {
        return input.get().stream().mapToLong((el) -> evalute(el, List.of(Operation.ADD, Operation.MUL)))
                .sum();
    }

    public long calculateWithPossibleConcatenation(Input input) {
        return input.get().stream().mapToLong((el) -> evalute(el, List.of(Operation.ADD, Operation.MUL, Operation.CONCAT)))
                .sum();
    }

    private long evalute(String el, List<Operation> possibleOperations) {
        System.out.println(el);
        String[] split = el.split(":");
        Long result = Long.parseLong(split[0]);
        List<Long> numbers = Arrays.stream(split[1].split("\\s"))
                .filter((c) -> !c.isEmpty())
                .map(String::trim).map(Long::parseLong)
                .collect(Collectors.toList());

        List<List<Operation>> operations = generateAllPermutationsOfAvailableElements(possibleOperations, numbers.size() - 1);

        return operations.stream().anyMatch((operationList) -> calclulate(operationList, numbers).equals(result))
                ? result : 0;
    }

    private static List<List<Operation>> generateAllPermutationsOfAvailableElements(List<Operation> possibleOperations, int k) {
        Deque<List<Operation>> operations = new LinkedList<>();
        operations.add(new LinkedList<>());
        for (int i = 0; i < k; i++) {
            List<List<Operation>> listAfterIteration = new LinkedList<>();
            while (!operations.isEmpty()) {
                List<Operation> currentOperations = operations.pollFirst();
                for(Operation operation : possibleOperations) {
                    List<Operation> newList = new ArrayList<>(currentOperations);
                    newList.add(operation);
                    listAfterIteration.add(newList);
                }
            }
            operations.addAll(listAfterIteration);
        }
        return new LinkedList<>(operations);
    }

    private Long calclulate(List<Operation> operationList, List<Long> numbers) {
        Long currentResult = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            switch (operationList.get(i - 1)) {
                case ADD -> currentResult += numbers.get(i);
                case MUL -> currentResult *= numbers.get(i);
                case CONCAT -> currentResult = Long.valueOf(currentResult.toString() + numbers.get(i).toString());
            }
        }

        return currentResult;
    }

    enum Operation {
        ADD, MUL, CONCAT
    }
}
