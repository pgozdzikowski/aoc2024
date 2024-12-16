package pl.gozdzikowski.pawel.adventofcode.day16;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class ReindeerMaze {

    public long findShortestPath(Input input) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        Pair<Integer, Integer> initialPosition = findInitialPosition(world);
        return findWithLowerCost(initialPosition, Direction.EAST, world).get().cost();

    }

    private Pair<Integer, Integer> findInitialPosition(String[][] world) {
        for(int y = 0; y < world.length; y++) {
            for(int x = 0; x < world[y].length; x++) {
                if(world[y][x].equals("S")) {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }

    private static Optional<State> findWithLowerCost(Pair<Integer, Integer> startingPosition, Direction direction, String[][] world) {
        Queue<State> queue = new PriorityQueue<>();
        queue.add(new State(List.of(startingPosition), 0L, direction));
        Set<Pair<Integer, Integer>> visitedNode = new HashSet<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            Pair<Integer, Integer> lastNode = state.lastOnPath();

            if(visitedNode.contains(lastNode)) {
                continue;
            }

            if (world[lastNode.right()][lastNode.left()].equals("E")) {
                return Optional.of(state);
            }

            visitedNode.add(lastNode);

            queue.addAll(neighbours(state, world));
        }
        return Optional.empty();
    }

    private static Collection<State> neighbours(State state, String[][] world) {
        List<State> newStates = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            Pair<Integer, Integer> positionAfterAdd = plus(state.lastOnPath(), direction.offset);
            if(!state.path.contains(positionAfterAdd) && !world[positionAfterAdd.right()][positionAfterAdd.left()].equals("#")) {
                List<Pair<Integer, Integer>> updatedPath = Stream.concat(state.path.stream(), Stream.of(positionAfterAdd)).toList();
                if(state.direction == direction) {
                    newStates.add(new State(updatedPath, state.cost + 1, direction));
                } else {
                    newStates.add(new State(updatedPath, state.cost + 1001, direction));
                }
            }
        };
        return newStates;
    }

    private static Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    enum Direction {
        NORTH(Pair.of(0, -1)), EAST(Pair.of(0, 1)), SOUTH(Pair.of(1, 0)), WEST(Pair.of(-1, 0));

        private final Pair<Integer, Integer> offset;

        Direction(Pair<Integer,Integer> offset) {
            this.offset = offset;
        }
    }

    record State(
            List<Pair<Integer, Integer>> path,
            long cost,
            Direction direction
    ) implements Comparable<State> {
        public Pair<Integer, Integer> lastOnPath() {
            return path.get(path.size() - 1);
        }

        @Override
        public int compareTo(State o) {
            return cost < o.cost ? -1 : 1;
        }
    }
}
