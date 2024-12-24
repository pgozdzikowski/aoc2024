package pl.gozdzikowski.pawel.adventofcode.day24;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class CrossedWires {

    public long produceNumberByWires(Input input) {
        String[] splited = input.getContent().split("\n{2}");
        Map<String, Integer> inputs = Arrays.stream(splited[0].split("\n")).map((el) -> el.split(":"))
                .collect(Collectors.toMap((el) -> el[0].trim(), el -> Integer.parseInt(el[1].trim())));

        List<List<String>> gates = Arrays.stream(splited[1].split("\n")).map(this::parseSingleLine).toList();

        Map<String, Integer> outputs = new HashMap<>(inputs);

        Queue<List<String>> toCalclate = new LinkedList<>(gates);

        while(!toCalclate.isEmpty()) {
            List<String> gate = toCalclate.poll();
            if(!outputs.containsKey(gate.get(0)) || !outputs.containsKey(gate.get(2))) {
                toCalclate.add(gate);
                continue;
            }
            Operation operation = Operation.valueOf(gate.get(1));
            int result = operation.execute(outputs.get(gate.get(0)), outputs.get(gate.get(2)));
            outputs.put(gate.get(3), result);
        }


        List<Pair<Integer, Integer>> sortedOutputs = getBitWithValue(outputs, "z");

        return createNumber(sortedOutputs);
    }

    private List<Pair<Integer, Integer>> getBitWithValue(Map<String, Integer> outputs, String varName) {
        return outputs.entrySet().stream()
                .filter((el) -> el.getKey().startsWith(varName))
                .map((el) -> Pair.of(createNumber(el.getKey()), el.getValue()))
                .sorted(Comparator.comparing(Pair::left))
                .toList();
    }

    private Integer createNumber(String input) {
        return Integer.parseInt(input.replaceAll("[a-z]+", ""));
    }

    private long createNumber(List<Pair<Integer, Integer>> outputs) {
        long result = 0;
        for(Pair<Integer, Integer> output: outputs) {
            result |= ((long) output.right() << output.left());
        }
        return result;
    }

    private List<String> parseSingleLine(String line) {
        String[] splited = line.split("->");

        String[] operands = splited[0].split("\\s+");

        return List.of(operands[0].trim(), operands[1].trim(), operands[2].trim(), splited[1].trim());
    }

    enum Operation {
        XOR {
            @Override
            public int execute(int leftSignal, int rightSignal) {
                return leftSignal == rightSignal ? 0 : 1;
            }
        }, AND {
            @Override
            public int execute(int leftSignal, int rightSignal) {
                if (leftSignal == 1 && rightSignal == 1) {
                    return 1;
                }
                return 0;
            }
        }, OR {
            @Override
            public int execute(int leftSignal, int rightSignal) {
                if (leftSignal == 1 || rightSignal == 1) {
                    return 1;
                }
                return 0;
            }
        };

        public abstract int execute(int leftSignal, int rightSignal);
    }
}
