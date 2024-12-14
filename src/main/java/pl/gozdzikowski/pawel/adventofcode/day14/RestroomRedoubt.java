package pl.gozdzikowski.pawel.adventofcode.day14;

import org.jgrapht.graph.WeightedIntrusiveEdgesSpecifics;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RestroomRedoubt {
    Pattern pattern = Pattern.compile("(-?\\d+)");

    public long calculateNumOfQuarters(Input input, int iterations, int width, int height) {
        List<Robot> robots = input.get().stream().map(this::parseRobot).toList();
        List<Pair<Long, Long>> positions = robots.stream().map((robot) -> robot.positionAfterIterations(iterations, width, height)).toList();
        return calculateMultiplicationOfQuarter(positions, width, height);
    }

    public long calculateNumSecondsToFormChristasTree(Input input, int width, int height) {
        List<Robot> robots = input.get().stream().map(this::parseRobot).toList();
        long iterations = 0;
        List<Pair<Long, Long>> positions;
        do {
            iterations++;
            System.out.println("Iterations: " + iterations);
            final long fIterations = iterations;
            positions = robots.stream().map((robot) -> robot.positionAfterIterations(fIterations, width, height)).toList();
        } while (!formChristmasTree(positions, width, height));

        return iterations;
    }

    private boolean formChristmasTree(List<Pair<Long, Long>> positions, int width, int height) {
        String state = printChristmasTree(positions, width, height);

        if(state.contains("#########"))
            return true;

        return false;
    }

    private String printChristmasTree(List<Pair<Long, Long>> positions, int width, int height) {
        StringBuilder sb = new StringBuilder();
        for (long y = 0; y < height; y++) {
            for (long x = 0; x < width; x++) {
                if (positions.contains(Pair.of(x, y))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }

            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private long calculateMultiplicationOfQuarter(List<Pair<Long, Long>> positions, int width, int height) {
        int firstHalf = 0;
        int secondHalf = 0;
        int thirdHalf = 0;
        int fourthHalf = 0;

        int xHalf = width / 2;
        int yHalf = height / 2;

        for (Pair<Long, Long> position : positions) {
            if (position.left() < xHalf && position.right() < yHalf) {
                firstHalf++;
            }
            if (position.left() > xHalf && position.right() < yHalf) {
                secondHalf++;
            }
            if (position.left() < xHalf && position.right() > yHalf) {
                thirdHalf++;
            }
            if (position.left() > xHalf && position.right() > yHalf) {
                fourthHalf++;
            }
        }

        return (long) firstHalf * secondHalf * thirdHalf * fourthHalf;
    }

    private Robot parseRobot(String line) {
        List<Integer> groups = pattern.matcher(line).results().map((el) -> Integer.parseInt(el.group(0))).toList();
        return new Robot(
                Pair.of(groups.get(0), groups.get(1)),
                Pair.of(groups.get(2), groups.get(3))
        );
    }

    record Robot(
            Pair<Integer, Integer> initialPosition,
            Pair<Integer, Integer> velocity
    ) {
        Pair<Long, Long> positionAfterIterations(long iterations, int width, int height) {
            long posX = initialPosition.left() + iterations * velocity.left();
            long posY = initialPosition.right() + iterations * velocity.right();
            posX = (posX >= 0) ? posX % width : (width - (Math.abs(posX) % width)) % width;
            posY = (posY >= 0) ? posY % height : (height - (Math.abs(posY) % height)) % height;

            return Pair.of(posX, posY);
        }
    }
}
