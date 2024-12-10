package pl.gozdzikowski.pawel.adventofcode.day10;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.graph.GraphExt;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Stream;

import static pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt.outOfBound;

public class HoofIt {

    private static final int MAX_POSITION = 9;

    public long countDistinctPathsReachingMax(Input input) {
        int[][] graph = convertToGraph(input);

        List<Pair<Integer, Integer>> startingPositions = findStartingPositions(graph);

        return startingPositions.stream()
                .mapToLong((el) -> findPathsReachingMax(graph, el)
                        .stream().map(SequencedCollection::getLast)
                        .distinct()
                        .count())
                .sum();
    }

    public long countNumberOfDistinctPaths(Input input) {
        int[][] graph = convertToGraph(input);

        List<Pair<Integer, Integer>> startingPositions = findStartingPositions(graph);

        return startingPositions.stream()
                .mapToLong((el) -> findPathsReachingMax(graph, el).size()).sum();
    }

    private int[][] convertToGraph(Input input) {
        return input.get().stream().map((el) -> Arrays.stream(el.split(""))
                        .mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }

    private List<? extends SequencedCollection<Pair<Integer, Integer>>> findPathsReachingMax(int[][] graph, Pair<Integer, Integer> startingPosition) {
        return GraphExt.bfs(startingPosition,
                (path) -> graph[path.getLast().right()][path.getLast().left()] == MAX_POSITION,
                (path) -> neighbours(path, graph)
        );
    }


    private List<List<Pair<Integer, Integer>>> neighbours(SequencedCollection<Pair<Integer, Integer>> path, int[][] graph) {
        Pair<Integer, Integer> lastNodeOnPath = path.getLast();

        return directions().stream().map(
                        (el) -> Pair.of(el.left() + lastNodeOnPath.left(), el.right() + lastNodeOnPath.right())
                ).filter((el) -> !outOfBound(el, graph) && !path.contains(el))
                .filter((el) -> graph[el.right()][el.left()] - graph[lastNodeOnPath.right()][lastNodeOnPath.left()] == 1)
                .map((el) -> Stream.concat(path.stream(), Stream.of(el)).toList())
                .toList();
    }

    private static List<Pair<Integer, Integer>> findStartingPositions(int[][] graph) {
        List<Pair<Integer, Integer>> startingPositions = new LinkedList<>();
        for (int y = 0; y < graph.length; y++) {
            for (int x = 0; x < graph[0].length; x++) {
                if (graph[y][x] == 0) {
                    startingPositions.add(new Pair(x, y));
                }
            }
        }
        return startingPositions;
    }

    private List<Pair<Integer, Integer>> directions() {
        return List.of(Pair.of(-1, 0), Pair.of(0, -1), Pair.of(1, 0), Pair.of(0, 1));
    }
}
