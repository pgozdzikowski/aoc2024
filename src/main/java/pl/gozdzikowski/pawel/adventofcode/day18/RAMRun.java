package pl.gozdzikowski.pawel.adventofcode.day18;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class RAMRun {

    public long  findShortestPathAfter(Input input, int width, int height, int numOfBytes) {
        char[][] grid = new char[height][width];
        List<Pair<Integer, Integer>> bytesList = input.get().stream().map((el) -> el.split(","))
                .map((el) -> Pair.of(Integer.parseInt(el[0]), Integer.parseInt(el[1]))).toList();
        initializeGrid(bytesList, width, height, numOfBytes, grid);
        printGrid(grid);
        return findShortestPath(Pair.of(0, 0), grid, width, height).get().path.size() -1;
    }

    public Pair<Integer, Integer> checkAfterWhichHowManyIsNotReachable(Input input, int width, int height) {
        int bytes =1;
        List<Pair<Integer, Integer>> bytesList = input.get().stream().map((el) -> el.split(","))
                .map((el) -> Pair.of(Integer.parseInt(el[0]), Integer.parseInt(el[1]))).toList();
        while(true) {
            char[][] grid = new char[height][width];
            initializeGrid(bytesList, width, height, bytes, grid);
            if(!findShortestPath(Pair.of(0, 0), grid, width, height).isPresent()) {
                break;
            }
            System.out.println(bytes);
            bytes++;
        }
        return bytesList.get(bytes - 1);
    }

    private void printGrid(char[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + "");
            }
            System.out.println();
        }
    }

    private static void initializeGrid(List<Pair<Integer, Integer>> bytesList, int width, int height, int numOfBytes, char[][] grid) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                grid[y][x] = '.';
            }
        }

        bytesList.subList(0, numOfBytes)
                .forEach(pair -> {
                    grid[pair.right()][pair.left()] = '#';
                });
    }

    private Optional<State> findShortestPath(Pair<Integer, Integer> startingPosition, char[][] world, int width, int height) {
        Queue<State> queue = new PriorityQueue<>();
        queue.add(new State(List.of(startingPosition), 0L));
        Set<Pair<Integer, Integer>> visitedNode = new HashSet<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            Pair<Integer, Integer> lastNode = state.lastNode();

            if(visitedNode.contains(lastNode)) {
                continue;
            }

            if (lastNode.equals(Pair.of(width - 1, height - 1))) {
                return Optional.of(state);
            }

            visitedNode.add(lastNode);

            queue.addAll(neighbours(state, world));
        }
        return Optional.empty();
    }

    private Collection<State> neighbours(State state, char[][] world) {
        return directions().stream().map((el) -> plus(state.lastNode(), el))
                .filter((el) -> !ArraysExt.outOfBound(el, world))
                .filter((el) -> world[el.right()][el.left()] != '#' && !state.path().contains(el))
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
