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
import java.util.stream.Stream;

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


    public long calculateSumOfCodesMoves(String[] input) {

        Long result = 0L;
        for(String code : input) {
            System.out.println(code);
            result+= (convertToDigit(code) * getSequencesOfMoves(code));
        }

        return result;
    }

    private Long convertToDigit(String code) {
        return Long.valueOf(code.replaceAll("A", ""));
    }

    private long getSequencesOfMoves(String code) {

        List<String> sequences = calculateSequencesToPressCode(code.split(""), "A", keypad);
        System.out.println(sequences);
        List<String> allPossibleSequencesAfterFirstRobot = new ArrayList<>();
        for(String sequence : sequences) {
            allPossibleSequencesAfterFirstRobot.addAll(calculateSequencesToPressCode(sequence.split(""), "A", directional));
        }

        System.out.println("After first robot");
        List<String> allPossibleSequencesAfterSecondRobot = new ArrayList<>();

        for(String sequence : allPossibleSequencesAfterFirstRobot) {
            allPossibleSequencesAfterSecondRobot.addAll(calculateSequencesToPressCode(sequence.split(""), "A", directional));
        }

        System.out.println("After second robot");


        return allPossibleSequencesAfterSecondRobot.stream().mapToLong(String::length).min().getAsLong();
    }

    private List<String> calculateSequencesToPressCode(String[] splitedCode, String currentPosition, String[][] pad) {
        List<String> sequenceOfShortedPresses= new ArrayList<>();
        for (String codeNum : splitedCode) {
            List<String> shortestPresses = findShortestPath(currentPosition, codeNum, pad);
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
            sequenceOfShortedPresses = sequenceOfShortedPresses.stream().map((el) -> el + "A").toList();
            currentPosition = codeNum;
        }
        return sequenceOfShortedPresses;
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
        queue.add(new State("", List.of(findPosition(keypad, startingPosition))));
        Set<Pair<String,Pair<Integer, Integer>>> visitedNodes = new HashSet<>();

        Pair<Integer, Integer> endPosition = findPosition(keypad, endingPosition);
        long minimalCost = Long.MAX_VALUE;
        List<State> shortestPaths = new LinkedList<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            Pair<Integer, Integer> lastNode = state.path().getLast();

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

            visitedNodes.add(Pair.of(lastSign(state.presses), lastNode));

            queue.addAll(neighbours(state, keypad));
        }

        List<String> shortestPresses = shortestPaths.stream().map(State::presses).collect(Collectors.toList());
        CACHE.put(Pair.of(startingPosition, endingPosition), shortestPresses);
        return shortestPresses;
    }

    private Collection<State> neighbours(State state, String[][] keypad) {
        return DIRECTION_TO_OFFSET.entrySet().stream().map((el) ->
                        Pair.of(plus(state.path().getLast(), el.getValue()), el.getKey())
                ).filter((el) -> !ArraysExt.outOfBound(el.left(), keypad) && !keypad[el.left().right()][el.left().left()].equals("#"))
                .filter((el) -> !state.path().contains(el.left()))
                .map((el) -> new State(state.presses + el.right(), concat(state.path, el.left())))
                .toList();
    }

    private List<Pair<Integer, Integer>> concat(List<Pair<Integer, Integer>> list, Pair<Integer, Integer> newElement) {
        return Stream.concat(list.stream(), Stream.of(newElement)).toList();
    }

    public Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    record State(String presses, List<Pair<Integer, Integer>> path) {

    }

    String lastSign(String presses) {
        if(presses.isEmpty()) {
            return "";
        }
        return presses.substring(presses.length() - 1);
    }

    public static void main(String[] args) {
        KeypadConundrum keypadConundrum = new KeypadConundrum();

        System.out.println(keypadConundrum.calculateSumOfCodesMoves(new String [] {"286A", "480A", "140A", "413A", "964A"}));
    }
}
