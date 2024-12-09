package pl.gozdzikowski.pawel.adventofcode.day8;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResonantCollinearity {

    public int countDistinctAntinodesAtDistanceTwo(Input input) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        Map<String, List<Pair<Integer, Integer>>> locationsOfSameTypeAntennas = locationOfSameTypeAntennas(world);

        return findAntinodes(world, locationsOfSameTypeAntennas, 2, 2)
                .size();
    }

    public int countDistinctAntinodesAtAnyLength(Input input) {
        String[][] world = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        Map<String, List<Pair<Integer, Integer>>> locationsOfSameTypeAntennas = locationOfSameTypeAntennas(world);

        return findAntinodes(world, locationsOfSameTypeAntennas, 0, Integer.MAX_VALUE)
                .size();
    }

    private static Map<String, List<Pair<Integer, Integer>>> locationOfSameTypeAntennas(String[][] world) {
        Map<String, List<Pair<Integer, Integer>>> locationsOfSameTypeAntennas = new HashMap<>();

        for (int y = 0; y < world.length; y++) {
            for (int x = 0; x < world[y].length; x++) {
                if (!world[y][x].equals(".")) {
                    List<Pair<Integer, Integer>> list = locationsOfSameTypeAntennas.getOrDefault(world[y][x], new ArrayList<>());
                    list.add(Pair.of(x, y));
                    locationsOfSameTypeAntennas.put(world[y][x], list);
                }
            }
        }
        return locationsOfSameTypeAntennas;
    }

    private boolean outOfBound(Pair<Integer, Integer> position, String[][] world) {
        return position.left() < 0 || position.right() < 0 || position.left() >= world[0].length || position.right() >= world.length;
    }

    private Set<Pair<Integer, Integer>> findAntinodes(String[][] world, Map<String, List<Pair<Integer, Integer>>> locationsOfSameTypeAntennas, int from, int to) {
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();
        for (var entry : locationsOfSameTypeAntennas.entrySet()) {
            List<Pair<Integer, Integer>> locationsOfAntenas = entry.getValue();

            for (int i = 0; i < locationsOfAntenas.size(); i++) {
                Pair<Integer, Integer> firstAntenna = locationsOfAntenas.get(i);
                for (int j = i + 1; j < locationsOfAntenas.size(); j++) {
                    antinodes.addAll(createAntinodesBetweenPoints(firstAntenna, locationsOfAntenas.get(j), world, from, to));
                }
            }
        }
        return antinodes;
    }

    private Set<Pair<Integer, Integer>> createAntinodesBetweenPoints(Pair<Integer, Integer> firstAntena, Pair<Integer, Integer> secondAntena, String[][] world, int from, int to
    ) {
        Pair<Integer, Integer> distance = distanceBetweenAntennas(firstAntena, secondAntena);
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();

        int direction = secondAntena.left() >= firstAntena.left() ? 1 : -1;
        int multiplier = from;

        do {
            Pair<Integer, Integer> currentPoint = Pair.of(firstAntena.left() + direction * multiplier * distance.left(), firstAntena.right() + multiplier * distance.right());
            if (outOfBound(currentPoint, world)) break;
            antinodes.add(currentPoint);
            multiplier++;
        } while (multiplier < to);

        multiplier = from;
        do {
            Pair<Integer, Integer> currentPoint = Pair.of(secondAntena.left() - direction * multiplier * distance.left(), secondAntena.right() - multiplier * distance.right());
            if (outOfBound(currentPoint, world)) break;
            antinodes.add(currentPoint);
            multiplier++;
        } while (multiplier < to);

        return antinodes;
    }

    private Pair<Integer, Integer> distanceBetweenAntennas(Pair<Integer, Integer> firstAntenna, Pair<Integer, Integer> secondAntenna) {
        return Pair.of(Math.abs(secondAntenna.left() - firstAntenna.left()), Math.abs(secondAntenna.right() - firstAntenna.right()));
    }
}
