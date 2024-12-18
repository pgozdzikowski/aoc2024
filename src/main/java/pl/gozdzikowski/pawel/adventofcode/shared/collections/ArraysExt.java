package pl.gozdzikowski.pawel.adventofcode.shared.collections;

public class ArraysExt {

    public static <T> boolean outOfBound(Pair<Integer, Integer> position, T[][] array) {
        return position.left() < 0 || position.right() < 0 || position.left() >= array[0].length || position.right() >= array.length;
    }

    public static boolean outOfBound(Pair<Integer, Integer> position, int[][] array) {
        return position.left() < 0 || position.right() < 0 || position.left() >= array[0].length || position.right() >= array.length;
    }

    public static boolean outOfBound(Pair<Integer, Integer> position, char[][] array) {
        return position.left() < 0 || position.right() < 0 || position.left() >= array[0].length || position.right() >= array.length;
    }
}
