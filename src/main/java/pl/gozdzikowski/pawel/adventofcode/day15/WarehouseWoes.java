package pl.gozdzikowski.pawel.adventofcode.day15;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private Pair<Integer, Integer> execute(String command, Pair<Integer, Integer> positionOfMove, String[][] warehouse) {
        Pair<Integer, Integer> offset = mapCommandToOffset(command);

        Pair<Integer, Integer> nextPos = plus(positionOfMove, offset);

        System.out.println("Command: " + command);
        if (warehouse[nextPos.right()][nextPos.left()].equals(".")) {
            Pair<Integer, Integer> prevPos = plus(nextPos, negate(offset));
            swap(warehouse, prevPos, nextPos);
            return nextPos;
        } else if (warehouse[nextPos.right()][nextPos.left()].equals("#")) {
            return positionOfMove;
        } else if (warehouse[nextPos.right()][nextPos.left()].equals("O")) {
            Pair<Integer, Integer> posOfGap = findPositionOfGap(warehouse, nextPos, offset);
            if (posOfGap != null) {
                while (!posOfGap.equals(positionOfMove)) {
                    Pair<Integer, Integer> prevPos = plus(posOfGap, negate(offset));
                    swap(warehouse, prevPos, posOfGap);
                    posOfGap = prevPos;
                }
                return plus(posOfGap, offset);
            }
        }

        return positionOfMove;
    }


    private Pair<Integer, Integer> findPositionOfGap(String[][] warehouse, Pair<Integer, Integer> position, Pair<Integer, Integer> offset) {
        Pair<Integer, Integer> posOfGap = position;
        while (!warehouse[posOfGap.right()][posOfGap.left()].equals(".")) {
            if (warehouse[posOfGap.right()][posOfGap.left()].equals("#")) {
                posOfGap = null;
                break;
            }
            posOfGap = plus(posOfGap, offset);
        }
        return posOfGap;
    }

    private static void swap(String[][] warehouse, Pair<Integer, Integer> prevPos, Pair<Integer, Integer> nextPos) {
        String swap = warehouse[prevPos.right()][prevPos.left()];
        warehouse[prevPos.right()][prevPos.left()] = warehouse[nextPos.right()][nextPos.left()];
        warehouse[nextPos.right()][nextPos.left()] = swap;
    }

    private Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    private Pair<Integer, Integer> negate(Pair<Integer, Integer> first) {
        return Pair.of(-first.left(), -first.right());
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
