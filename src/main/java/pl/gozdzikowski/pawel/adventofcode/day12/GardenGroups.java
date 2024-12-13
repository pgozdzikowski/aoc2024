package pl.gozdzikowski.pawel.adventofcode.day12;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.ArraysExt;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class GardenGroups {

    public long calculateSumOfPrice(Input input) {
        String[][] garden = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        List<GardenPolygon> areas = new ArrayList<>();
        calculateGardenAreas(garden, areas);

        return areas.stream().mapToLong((area) -> area.price(garden)).sum();
    }

    public long calculateSumOfDiscounts(Input input) {
        String[][] garden = input.get().stream().map((el) -> el.split("")).toArray(String[][]::new);
        List<GardenPolygon> areas = new ArrayList<>();
        calculateGardenAreas(garden, areas);

        return areas.stream().mapToLong((area) -> area.discount(garden)).sum();
    }

    private void calculateGardenAreas(String[][] garden, List<GardenPolygon> areas) {
        for (int y = 0; y < garden.length; y++) {
            for (int x = 0; x < garden[y].length; x++) {
                int finalX = x;
                int finalY = y;
                if (areas.stream().noneMatch((polygon) -> polygon.points().contains(Pair.of(finalX, finalY)))) {
                    String currentElement = garden[y][x];
                    areas.add(new GardenPolygon(currentElement, searchAreaStartingAt(garden, Pair.of(x, y), new ArrayList<>())));
                }

            }
        }
    }

    private List<Pair<Integer, Integer>> searchAreaStartingAt(String[][] garden, Pair<Integer, Integer> position, List<Pair<Integer, Integer>> visitedPositions) {

        if (visitedPositions.contains(position)) {
            return visitedPositions;
        }

        visitedPositions.add(position);

        directions().stream().map((direction) -> plus(position, direction))
                .filter((el) -> !ArraysExt.outOfBound(el, garden))
                .filter((el) -> garden[position.right()][position.left()].equals(garden[el.right()][el.left()]))
                .filter((el) -> !visitedPositions.contains(el))
                .forEach((el) -> searchAreaStartingAt(garden, el, visitedPositions));

        return visitedPositions;
    }

    record GardenPolygon(
            String symbol,
            List<Pair<Integer, Integer>> points
    ) {
        private static final Comparator<Pair<Integer, Integer>> FIRST_Y_THEN_X = Comparator.comparingInt(Pair<Integer, Integer>::right)
                .thenComparing(Pair::left);
        private static final Comparator<Pair<Integer, Integer>> FIRST_X_THEN_Y = Comparator.comparingInt(Pair<Integer, Integer>::left)
                .thenComparing(Pair::right);
        public int area() {
            return points().size();
        }

        public long permiter(String[][] garden) {
            return directions().stream().flatMap((direction) ->points().stream().map((point) -> plus(point, direction))
                    ).filter((el) -> isEdgePoint(garden, el))
                    .count();
        }

        public long discount(String[][] garden) {
            return area() * sides(garden);
        }

        public long sides(String[][] garden) {
            return verticalDirections().stream().mapToLong((direction) ->
                            calculateLines(
                                    edgePointsForDirection(garden, direction),
                                    FIRST_X_THEN_Y,
                                    Pair.of(0, -1)
                            )
                    )
                    .sum() + horizontalDirections().stream().mapToLong((direction) ->
                            calculateLines(
                                    edgePointsForDirection(garden, direction),
                                    FIRST_Y_THEN_X,
                                    Pair.of(-1, 0)
                            )
                    )
                    .sum();
        }

        public long price(String[][] garden) {
            return area() * permiter(garden);
        }

        private List<Pair<Integer, Integer>> edgePointsForDirection(String[][] garden, Pair<Integer, Integer> direction) {
            return points().stream()
                    .map((point) -> plus(point, direction))
                    .filter((point) -> isEdgePoint(garden, point))
                    .toList();
        }

        private boolean isEdgePoint(String[][] garden, Pair<Integer, Integer> el) {
            return ArraysExt.outOfBound(el, garden) || !points.contains(el);
        }

        private long calculateLines(List<Pair<Integer, Integer>> edgePoints, Comparator<Pair<Integer, Integer>> comparator, Pair<Integer, Integer> diff) {
            List<Pair<Integer, Integer>> sortedPoints = edgePoints.stream().sorted(comparator).toList();
            List<Pair<Integer, Integer>> currentLine = new ArrayList<>();
            List<List<Pair<Integer, Integer>>> horizontalLines = new ArrayList<>();

            for (int i = 0; i < sortedPoints.size(); i++) {
                Pair<Integer, Integer> pointOnLine = plus(sortedPoints.get(i), diff);
                if (currentLine.contains(pointOnLine)) {
                    currentLine.add(sortedPoints.get(i));
                } else {
                    if (!currentLine.isEmpty()) {
                        horizontalLines.add(currentLine);
                    }
                    currentLine = new ArrayList<>();
                    currentLine.add(sortedPoints.get(i));
                }
            }

            if (!currentLine.isEmpty()) {
                horizontalLines.add(currentLine);
            }

            return horizontalLines.size();
        }
    }


    private static Pair<Integer, Integer> plus(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return Pair.of(first.left() + second.left(), first.right() + second.right());
    }

    private static List<Pair<Integer, Integer>> verticalDirections() {
        return List.of(Pair.of(-1, 0), Pair.of(1,0));
    }

    private static List<Pair<Integer, Integer>> horizontalDirections() {
        return List.of(Pair.of(0, -1), Pair.of(0,1));
    }

    private static List<Pair<Integer, Integer>> directions() {
        return Stream.concat(horizontalDirections().stream(), verticalDirections().stream()).toList();
    }
}
