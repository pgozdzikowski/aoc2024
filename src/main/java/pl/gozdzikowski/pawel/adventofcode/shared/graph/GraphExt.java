package pl.gozdzikowski.pawel.adventofcode.shared.graph;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.function.Function;
import java.util.function.Predicate;

public class GraphExt {

    public static List<? extends SequencedCollection<Pair<Integer, Integer>>> bfs(Pair<Integer, Integer> startingPosition,
                                                                  Predicate<? super SequencedCollection<Pair<Integer, Integer>>> endFunction,
                                                                  Function<? super SequencedCollection<Pair<Integer, Integer>>, List<? extends SequencedCollection<Pair<Integer, Integer>>>> neighbours
    ) {
        Deque<SequencedCollection<Pair<Integer, Integer>>> queue = new LinkedList<>();
        queue.add(new LinkedList<>(List.of(startingPosition)));
        List<SequencedCollection<Pair<Integer, Integer>>> pathsToEnd = new ArrayList<>();
        while (!queue.isEmpty()) {
            SequencedCollection<Pair<Integer, Integer>> currentPath = queue.pollFirst();

            if (endFunction.test(currentPath)) {
                pathsToEnd.add(currentPath);
                continue;
            }

            queue.addAll(neighbours.apply(currentPath));

        }
        return pathsToEnd;
    }
}
