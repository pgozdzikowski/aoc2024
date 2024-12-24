package pl.gozdzikowski.pawel.adventofcode.day23;

import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LANParty {

    public long findGroupsOfConnectedComputers(Input input) {
        List<String[]> listOfNodes = input.get().stream().map((el) -> el.split("-")).toList();
        Map<String, List<String>> graph = createGraph(listOfNodes);
        List<List<String>> interconnectedPaths = new ArrayList<>();

        for(String startingNode : graph.keySet()) {
            interconnectedPaths.addAll(findInterconnectedPaths(startingNode, graph, "t", 3L));
        }

        Set<Set<String>> distinctPaths = interconnectedPaths.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        return distinctPaths.size();
    }

    public String findMaxComputersInLanParty(Input input) {
        List<String[]> listOfNodes = input.get().stream().map((el) -> el.split("-")).toList();

        Graph<String, DefaultEdge> graph  = new DefaultUndirectedGraph<>(DefaultEdge.class);

        for(String[] node : listOfNodes) {
            if (!graph.containsVertex(node[0]))
                graph.addVertex(node[0]);

            if (!graph.containsVertex(node[1]))
                graph.addVertex(node[1]);

            graph.addEdge(node[0], node[1]);
        }

        BronKerboschCliqueFinder<String, DefaultEdge> bronKerboschCliqueFinder = new BronKerboschCliqueFinder<>(graph);

        Set<String> maximumClique = bronKerboschCliqueFinder.maximumIterator().next();

        return maximumClique.stream().sorted().collect(Collectors.joining(","));
    }

    private static Map<String, List<String>> createGraph(List<String[]> listOfNodes) {
        Map<String, List<String>> graph = new HashMap<>();
        for(String[] node : listOfNodes) {
            addToGraph(node[0], node[1], graph);
            addToGraph(node[1], node[0], graph);
        }
        return graph;
    }

    private static void addToGraph(String source, String target, Map<String, List<String>> graph) {
        if(graph.containsKey(source)) {
            graph.get(source).add(target);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(target);
            graph.put(source, newList);
        }
    }

    private List<List<String>> findInterconnectedPaths(String startingPosition, Map<String, List<String>> graph, String filter, Long length) {
        Queue<List<String>> queue = new LinkedList<>();
        queue.add(List.of(startingPosition));

        List<List<String>> pathOfLength = new ArrayList<>();

        while (!queue.isEmpty()) {
            List<String> currentPath = queue.poll();

            if (currentPath.size() == length && (currentPath.stream().anyMatch((el) -> el.startsWith(filter)))) {
                pathOfLength.add(currentPath);
                continue;
            }

            if(currentPath.size() > length) {
                break;
            }

            queue.addAll(neighbours(currentPath, graph));
        }

        return pathOfLength;
    }

    private List<List<String>> neighbours(List<String> currentPath, Map<String, List<String>> graph) {
        return graph.get(currentPath.getLast())
                .stream()
                .filter((el) -> !currentPath.contains(el) && currentPath.stream().allMatch((currentPathEl) -> graph.get(el).contains(currentPathEl)) )
                .map((el) -> Stream.concat(currentPath.stream(), Stream.of(el)).toList())
                .toList();
    }
}
