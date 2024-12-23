package pl.gozdzikowski.pawel.adventofcode.day21;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class KeypadConundrum {

    private static Map<Pair<String, String>, List<String>> CACHE = new HashMap<>();

    String[][] keypad = {
            {"7", "8", "9"},
            {"4", "5", "6"},
            {"1", "2", "3"},
            {"#", "0", "A"},
    };

    String[][] directional = {
            {"#", "^", "A"},
            {"<", "v", ">"},
    };

    Map<String, Pair<Integer, Integer>> DIRECTION_TO_OFFSET = Map.ofEntries(
            Map.entry("<", Pair.of(-1, 0)),
            Map.entry("^", Pair.of(0, -1)),
            Map.entry(">", Pair.of(1, 0)),
            Map.entry("v", Pair.of(0, 1))
    );


    public List<String> sequencesToPushButton(String code) {
        String[] splitedCode = code.split("");
        String currentPosition = "A";
        List<String> sequenceOfShortedPresses= new ArrayList<>();
        for (String codeNum : splitedCode) {
            List<String> shortestPresses = findShortestPath(currentPosition, codeNum, keypad);
            if(sequenceOfShortedPresses.isEmpty()) {
                sequenceOfShortedPresses.addAll(shortestPresses);
            } else {
                List<String> newPressesList= new ArrayList<>();
                for(int i = 0; i < sequenceOfShortedPresses.size(); i++) {
                    String currentPress = sequenceOfShortedPresses.get(i);
                    for(String shortestPress : shortestPresses) {
                        newPressesList.add(currentPress + shortestPress);
                    }
                }
                sequenceOfShortedPresses = newPressesList;
            }
            currentPosition = codeNum;
        }

        return sequenceOfShortedPresses.stream().distinct().toList();
    }

    private Pair<Integer, Integer> findPosition(String[][] keypad, String position) {
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j].equals(position)) {
                    return new Pair(j, i);
                }
            }
        }
        throw new IllegalStateException("Keypad could not be found");
    }

    private List<String> findShortestPath(String startingPosition, String endingPosition, String[][] keypad) {

        if(CACHE.containsKey(Pair.of(startingPosition, endingPosition))) {
            return CACHE.get(Pair.of(startingPosition, endingPosition));
        }

        Queue<State> queue = new LinkedList<>();
        queue.add(new State("", findPosition(keypad, startingPosition)));
        Set<Pair<Integer, Integer>> visitedNodes = new HashSet<>();

        Pair<Integer, Integer> endPosition = findPosition(keypad, endingPosition);
        long minimalCost = Long.MAX_VALUE;
        List<State> shortestPaths = new LinkedList<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            Pair<Integer, Integer> lastNode = state.currentPosition();

            if (lastNode.equals(endPosition)) {
                if (minimalCost > state.presses.length()) {
                    shortestPaths.clear();
                    minimalCost = state.presses.length();
                }
                if (minimalCost == state.presses.length()) {
                    shortestPaths.add(state);
                }
            }

            if (visitedNodes.contains(lastNode)) {
                continue;
            }

            visitedNodes.add(lastNode);

            queue.addAll(neighbours(state, keypad));
        }

        List<String> shortestPresses = shortestPaths.stream().map(State::presses).collect(Collectors.toList());
        CACHE.put(Pair.of(startingPosition, endingPosition), shortestPresses);
        return shortestPresses;
    }

    private Collection<State> neighbours(State state, String[][] keypad) {
        return DIRECTION_TO_OFFSET.entrySet().stream().map((el) ->
                        Pair.of(plus(state.currentPosition(), el.getValue()), el.getKey())
                ).filter((el) -> !ArraysExt.outOfBound(el.left(), keypad) && !keypad[el.left().right()][el.left().left()].equals("#"))
                .map((el) -> new State(state.presses + el.right(), el.left()))
                .toList();
    }

    public Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    record State(String presses, Pair<Integer, Integer> currentPosition) {

    }
}
