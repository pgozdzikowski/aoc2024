package pl.gozdzikowski.pawel.adventofcode.day15;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WarehouseWoes {

    public long countGpsPositionsOfPackeges(Input input) {
        String[] splited = input.getContent().split("\n\n");
        String[][] warehouse = Arrays.stream(splited[0].split("\n")).map((el) -> el.split("")).toArray(String[][]::new);
        String[] commands = Arrays.stream(splited[1].split("")).filter((el) -> !el.equals("\n")).toArray(String[]::new);
        Pair<Integer, Integer> positionOfRobot = findInitialPositionOfRobot(warehouse);
        for (String command : commands) {
            positionOfRobot = execute(command, positionOfRobot, warehouse);
        }

        return calculateSumOfGpsPositionsOfPackeges(warehouse, "O");
    }

    public long countGpsPositionsOfBiggerPackages(Input input) {
        String[] splited = input.getContent().split("\n\n");
        String[][] warehouse = Arrays.stream(splited[0].split("\n")).map((el) -> el.split("")).toArray(String[][]::new);
        String[] commands = Arrays.stream(splited[1].split("")).filter((el) -> !el.equals("\n")).toArray(String[]::new);
        warehouse = doubleWarehouse(warehouse);
        Pair<Integer, Integer> positionOfRobot = findInitialPositionOfRobot(warehouse);
        printWarehouse(warehouse);

        for (String command : commands) {
            positionOfRobot = execute(command, positionOfRobot, warehouse);
        }

        return calculateSumOfGpsPositionsOfPackeges(warehouse, "[");
    }

    private String[][] doubleWarehouse(String[][] warehouse) {
        List<List<String>> doubleWarehouse = new ArrayList<>(warehouse.length * 2);

        for (int y = 0; y < warehouse.length; y++) {
            List<String> xList = new ArrayList<>();
            for (int x = 0; x < warehouse[y].length; x++) {
                switch (warehouse[y][x]) {
                    case "#" -> xList.addAll(List.of("#", "#"));
                    case "O" -> xList.addAll(List.of("[", "]"));
                    case "." -> xList.addAll(List.of(".", "."));
                    case "@" -> xList.addAll(List.of("@", "."));
                }
            }
            doubleWarehouse.add(xList);
        }

        return doubleWarehouse.stream().map((el) -> el.toArray(String[]::new)).toArray(String[][]::new);
    }


    private long calculateSumOfGpsPositionsOfPackeges(String[][] warehouse, String calcSymbol) {
        long result = 0;
        for (int y = 0; y < warehouse.length; y++) {
            for (int x = 0; x < warehouse[0].length; x++) {
                if (warehouse[y][x].equals(calcSymbol)) {
                    result += (100 * y + x);
                }
            }
        }
        return result;
    }

    Map<String, Pair<Integer, Integer>> BRACKET_TO_OFFSET = Map.of(
            "[", Pair.of(1, 0),
            "]", Pair.of(-1, 0)
    );

    List<Pair<Integer, Integer>> VERTICAL_DIRECTIONS = List.of(Pair.of(-1, 0), Pair.of(1, 0));

    private Pair<Integer, Integer> execute(String command, Pair<Integer, Integer> startingPosition, String[][] warehouse) {
        Pair<Integer, Integer> offset = mapCommandToOffset(command);

        Set<Pair<Integer, Integer>> visitedPositions = new HashSet<>();
        Stack<Pair<Integer, Integer>> stack = new Stack<>();
        stack.push(plus(startingPosition, offset));
        visitedPositions.add(startingPosition);
        while(!stack.isEmpty()) {
            Pair<Integer, Integer> position = stack.pop();
            if(warehouse[position.right()][position.left()].equals("#")) {
                return startingPosition;
            }

            if(warehouse[position.right()][position.left()].equals("."))
                continue;

            if(visitedPositions.contains(position))
                continue;

            if(VERTICAL_DIRECTIONS.contains(offset)) {
                Pair<Integer, Integer> positionAfterMove = plus(position, offset);
                stack.push(positionAfterMove);
            } else {
                Pair<Integer, Integer> positionAfterMove = plus(position, offset);
                stack.push(positionAfterMove);
                if(BRACKET_TO_OFFSET.containsKey(warehouse[positionAfterMove.right()][positionAfterMove.left()])) {
                    Pair<Integer, Integer> secondPositionToCheck = plus(position, BRACKET_TO_OFFSET.get(warehouse[position.right()][position.left()]));
                    stack.push(secondPositionToCheck);
                }

            }

            visitedPositions.add(position);
        }

        Map<Pair<Integer, Integer>, String> oldToNew = visitedPositions.stream().collect(Collectors.toMap(Function.identity(), (el) -> warehouse[el.right()][el.left()]));
        visitedPositions.forEach((el) -> warehouse[el.right()][el.left()] = ".");
        visitedPositions.stream().map((el) -> Pair.of(el, plus(el, offset))).forEach((el) -> {
            warehouse[el.right().right()][el.right().left()] = oldToNew.get(el.left());
        });

        return plus(startingPosition, offset);
    }

    private Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    private Pair<Integer, Integer> mapCommandToOffset(String command) {
        return switch (command) {
            case "^" -> Pair.of(0, -1);
            case "<" -> Pair.of(-1, 0);
            case "v" -> Pair.of(0, 1);
            case ">" -> Pair.of(1, 0);
            default -> throw new IllegalStateException("Unknown command");
        };
    }

    private void printWarehouse(String[][] warehouse) {
        for (int y = 0; y < warehouse.length; y++) {
            for (int x = 0; x < warehouse[0].length; x++) {
                System.out.print(warehouse[y][x]);
            }
            System.out.println();
        }
    }

    private Pair<Integer, Integer> findInitialPositionOfRobot(String[][] warehouse) {
        for (int y = 0; y < warehouse.length; y++) {
            for (int x = 0; x < warehouse[0].length; x++) {
                if (warehouse[y][x].equals("@"))
                    return new Pair<>(x, y);
            }
        }

        throw new IllegalStateException("Unable to find initial position");
    }
}
