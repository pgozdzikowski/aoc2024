package pl.gozdzikowski.pawel.adventofcode.day6;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt.outOfBound;

public class GuardGallivant {

    private static final Map<Direction, Direction> COLLISION_RULES = Map.ofEntries(
            Map.entry(Direction.UP, Direction.RIGHT),
            Map.entry(Direction.RIGHT, Direction.DOWN),
            Map.entry(Direction.DOWN, Direction.LEFT),
            Map.entry(Direction.LEFT, Direction.UP)
    );

    public int countGuardDistinctPositions(Input input) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);

        GuardState currentGuardState = findInitialGuardState(world);

        var pathResult = (PathResult)findStatesInWhichGuardWas(currentGuardState, world);

        return pathResult.getGuardStates().stream().map(GuardState::position)
                .collect(Collectors.toSet()).size();
    }

    // brute force solution replacing each dot to #
    public int countGuardDistinctPositionsChangingAdditional(Input input) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        GuardState initialGuardState = findInitialGuardState(world);
        int countOfAdditional = 0;

        List<Pair<Integer, Integer>> guardPositions = ((PathResult) findStatesInWhichGuardWas(initialGuardState, world)).getGuardStates()
                .stream()
                .map(GuardState::position)
                .distinct()
                .toList();

        for(Pair<Integer, Integer> position : guardPositions) {
            if (!Pair.of(position.left(), position.right()).equals(initialGuardState.position())) {
                world[position.right()][position.left()] = "#";
                if (findStatesInWhichGuardWas(initialGuardState, world) instanceof InfiniteLoopPath) {
                    countOfAdditional++;
                }
                world[position.right()][position.left()] = ".";
            }
        }

        return countOfAdditional;
    }

    private GuardPathResult findStatesInWhichGuardWas(GuardState initialGuardState, String[][] world) {
        List<GuardState> guardStates = new ArrayList<>();

        GuardState currentGuardState = initialGuardState;

        guardStates.add(currentGuardState);

        while (true) {
            Pair<Integer, Integer> delta = currentGuardState.direction().getDelta();
            Pair<Integer, Integer> newPosition = plus(currentGuardState.position(), delta);
            if (outOfBound(newPosition, world)) {
                break;
            }

            if (world[newPosition.right()][newPosition.left()].equals("#")) {
                currentGuardState = currentGuardState.changeDirection(COLLISION_RULES.get(currentGuardState.direction()));
            } else {
                currentGuardState = currentGuardState.changePosition(newPosition);
                if (guardStates.contains(currentGuardState)) {
                    return new InfiniteLoopPath();
                }
                guardStates.add(currentGuardState);
            }
        }

        return new PathResult(guardStates);
    }

    public Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    GuardState findInitialGuardState(String[][] world) {
        for (int y = 0; y < world.length; y++) {
            for (int x = 0; x < world[0].length; x++) {
                if (Direction.symbols().contains(world[y][x])) {
                    return new GuardState(Direction.fromSymbol(world[y][x]), Pair.of(x, y));
                }
            }
        }
        throw new IllegalStateException("Unable to find guard initial state");
    }

    record GuardState(
            Direction direction,
            Pair<Integer, Integer> position
    ) {
        GuardState changeDirection(Direction direction) {
            return new GuardState(direction, position);
        }

        GuardState changePosition(Pair<Integer, Integer> position) {
            return new GuardState(direction, position);
        }
    }

    enum Direction {
        UP("^", Pair.of(0, -1)),
        LEFT("<", Pair.of(-1, 0)),
        RIGHT(">", Pair.of(1, 0)),
        DOWN("v", Pair.of(0, 1));

        private final String symbol;
        private final Pair<Integer, Integer> delta;

        Direction(String symbol, Pair<Integer, Integer> delta) {
            this.symbol = symbol;
            this.delta = delta;
        }

        static Set<String> symbols() {
            return Arrays.stream(Direction.values()).map(Direction::getSymbol).collect(Collectors.toSet());
        }

        static Direction fromSymbol(String symbol) {
            return Arrays.stream(values()).filter((el) -> el.symbol.equals(symbol)).findFirst().orElse(null);
        }

        public Pair<Integer, Integer> getDelta() {
            return delta;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    static abstract sealed class GuardPathResult permits InfiniteLoopPath, PathResult {

    }

    final static class PathResult extends GuardPathResult {
        private final List<GuardState> guardStates;

        public PathResult(List<GuardState> guardStates) {
            this.guardStates = guardStates;
        }

        public List<GuardState> getGuardStates() {
            return guardStates;
        }
    }

    final static class InfiniteLoopPath extends GuardPathResult {
    }


}
