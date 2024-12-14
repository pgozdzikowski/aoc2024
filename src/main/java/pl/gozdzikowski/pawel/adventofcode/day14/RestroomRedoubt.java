package pl.gozdzikowski.pawel.adventofcode.day14;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        try(BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream("search-christmas-tree.txt"))) {
            do {
                System.out.println("Iterations: " + iterations);
                buf.write(("Iterations:" + iterations + "\n").getBytes());
                final long fIterations = iterations;
                positions = robots.stream().map((robot) -> robot.positionAfterIterations(fIterations, width, height)).toList();

                iterations++;
            } while (!formChristmasTree(buf, positions, width, height) && iterations < 10000L);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return iterations;
    }
    private boolean formChristmasTree(BufferedOutputStream buf, List<Pair<Long, Long>> positions, int width, int height) throws IOException {
        printChristmasTree(buf, positions, width, height);

        return false;
    }

    private void printChristmasTree(BufferedOutputStream buf, List<Pair<Long, Long>> positions, int width, int height) throws IOException {
        for(long y=0; y<height; y++) {
            for(long x=0; x<width; x++) {
                if(positions.contains(Pair.of(x, y))) {
                    buf.write("#".getBytes());
                } else {
                    buf.write(".".getBytes());
                }

            }
            buf.write("\n".getBytes());
        }
        buf.flush();
    }

    private long calculateMultiplicationOfQuarter(List<Pair<Long, Long>> positions, int width, int height) {
        int firstHalf = 0;
        int secondHalf = 0;
        int thirdHalf = 0;
        int fourthHalf = 0;

        int xHalf = width / 2;
        int yHalf = height / 2;

        for(Pair<Long, Long> position : positions) {
            if(position.left() < xHalf && position.right() < yHalf) {
                firstHalf++;
            }
            if(position.left() > xHalf && position.right() < yHalf) {
                secondHalf++;
            }
            if(position.left() < xHalf && position.right() > yHalf) {
                thirdHalf++;
            }
            if(position.left() > xHalf && position.right() > yHalf) {
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
            long posX = initialPosition.left() + iterations* velocity.left();
            long posY = initialPosition.right() + iterations* velocity.right();

            posX = (posX % width + width) % width;
            posY = (posY % height + height) % height;

            return Pair.of(posX, posY);
        }
    }
}
