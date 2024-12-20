package pl.gozdzikowski.pawel.adventofcode.day20;

import pl.gozdzikowski.pawel.adventofcode.day18.RAMRun;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt;
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

public class RaceCondition {

    public long findSaveBytCheat(Input input, int cheatDistanceFrom, int cheatDistanceTo) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        Pair<Integer, Integer> initialPosition = findInitialPosition(world);
        Optional<State> shortestPath = findShortestPath(initialPosition, world);

        List<Pair<Integer, Integer>> path = shortestPath.get().path;

        List<Pair<Integer, Integer>> bestCheatNodes = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            for (int j = i + 3; j < path.size(); j++) {
                Pair<Integer, Integer> firstVertex = path.get(i);
                Pair<Integer, Integer> secondVertex = path.get(j);
                Pair<Integer, Integer> distance = distance(firstVertex, secondVertex);
                if ((inRange(1, 2, distance.left()) && distance.right() == 0) || (distance.left() == 0 && inRange(1, 2, distance.right()))) {
                    int lengthOfDistance = Math.max(distance.left(), distance.right());
                    int distanceSaves= j - i - lengthOfDistance ;
                    if(distanceSaves >= cheatDistanceFrom && distanceSaves <= cheatDistanceTo) {
                        bestCheatNodes.add(firstVertex);
                    }
                }
            }

        }

        return bestCheatNodes.size();
    }

    private boolean inRange(int start, int end, int value) {
        return start <= value && value <= end;
    }

    private Pair<Integer, Integer> distance(Pair<Integer, Integer> firstVertex, Pair<Integer, Integer> secondVertex) {
        return Pair.of(Math.abs(firstVertex.left() - secondVertex.left()), Math.abs(firstVertex.right() - secondVertex.right()));
    }


    private Pair<Integer, Integer> findInitialPosition(String[][] world) {
        for (int y = 0; y < world.length; y++) {
            for (int x = 0; x < world[y].length; x++) {
                if (world[y][x].equals("S")) {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }


    private Optional<State> findShortestPath(Pair<Integer, Integer> startingPosition, String[][] world) {
        Queue<State> queue = new PriorityQueue<>();
        queue.add(new State(List.of(startingPosition), 0L));
        Set<Pair<Integer, Integer>> visitedNode = new HashSet<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            Pair<Integer, Integer> lastNode = state.lastNode();

            if (visitedNode.contains(lastNode)) {
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


    private Collection<State> neighbours(State state, String[][] world) {
        return directions().stream().map((el) -> plus(state.lastNode(), el))
                .filter((el) -> !ArraysExt.outOfBound(el, world))
                .filter((el) -> !world[el.right()][el.left()].equals("#") && !state.path().contains(el))
                .map((el) -> {
                    var newPath = Stream.concat(state.path.stream(), Stream.of(el)).toList();
                    return new State(newPath, state.cost + 1);
                })
                .toList();
    }

    public Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    public List<Pair<Integer, Integer>> directions() {
        return List.of(Pair.of(-1, 0), Pair.of(0, 1), Pair.of(0, -1), Pair.of(1, 0));
    }


    record State(List<Pair<Integer, Integer>> path, long cost) implements Comparable<State> {

        @Override
        public int compareTo(State o) {
            return cost < o.cost ? -1 : 1;
        }

        public Pair<Integer, Integer> lastNode() {
            return path.get(path.size() - 1);
        }
    }
}
