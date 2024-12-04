package pl.gozdzikowski.pawel.adventofcode.day4;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CeresSearch {
    private static final String SEARCHED_WORD_XMAS = "XMAS";
    private static final String SEARCHED_WORD_MAS = "MAS";

    public int countOccurrencesOfXmas(Input input) {
        String[][] crossword = parseCrossword(input);

        return searchForWord(crossword, allDirections(), SEARCHED_WORD_XMAS).size();
    }

    public int countOccurrencesOfCrossingDiagonalXmas(Input input) {
        String[][] crossword = parseCrossword(input);

        var words = searchForWord(crossword, diagonalDirections(), SEARCHED_WORD_MAS);

        return findCrossesBetweenWords(words);
    }

    private List<List<Pair<Integer, Integer>>> searchForWord(String[][] crossword, List<Pair<Integer, Integer>> directions, String word) {
        List<List<Pair<Integer, Integer>>> words = new ArrayList<>();
        for (int y = 0; y < crossword.length; y++) {
            for (int x = 0; x < crossword[y].length; x++) {
                if (crossword[y][x].charAt(0) == word.charAt(0)) {
                    final int fX = x;
                    final int fY = y;
                    var lists = directions.stream().map((el) -> findListOfIndexContainingWord(word, crossword, el, fX, fY))
                            .filter((el) -> !el.isEmpty())
                            .toList();

                    words.addAll(lists);
                }
            }
        }
        return words;
    }

    private static int findCrossesBetweenWords(List<List<Pair<Integer, Integer>>> words) {
        return (int)IntStream.range(0, words.size())
                .boxed()
                .filter( (el) -> words.subList(el + 1, words.size()).stream().anyMatch((curr) -> crossesAtMiddle(words.get(el), curr)))
                .count();
    }

    private static boolean crossesAtMiddle(List<Pair<Integer, Integer>> w1, List<Pair<Integer, Integer>> w2) {
        int middle = w1.size() / 2;
        return w1.get(middle).equals(w2.get(middle));
    }

    private List<Pair<Integer, Integer>> findListOfIndexContainingWord(String searchedWord, String[][] crossword, Pair<Integer, Integer> direction, int x, int y) {
        int currentX = x;
        int currentY = y;
        List<Pair<Integer, Integer>> indexesOfWords = new ArrayList<>();
        for (int i = 0; i < searchedWord.length(); i++) {
            if (isNotOutOfBound(crossword, currentX, currentY)) {
                if (searchedWord.charAt(i) == crossword[currentY][currentX].charAt(0)) {
                    indexesOfWords.add(Pair.of(currentX, currentY));
                    currentX += direction.left();
                    currentY += direction.right();
                } else {
                    return List.of();
                }
            } else {
                return List.of();
            }
        }

        return indexesOfWords;
    }

    private static boolean isNotOutOfBound(String[][] crossword, int currentX, int currentY) {
        return currentX >= 0 && currentY >= 0 && currentX < crossword[0].length && currentY < crossword.length;
    }

    private static String[][] parseCrossword(Input input) {
        return input.get().stream().map((el) -> Arrays.stream(el.split("")).toArray(String[]::new))
                .toArray(String[][]::new);
    }

    private List<Pair<Integer, Integer>> allDirections() {
        return IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap((dx) -> IntStream.rangeClosed(-1, 1).boxed().map((dy) -> Pair.of(dx, dy)))
                .toList();
    }

    private List<Pair<Integer, Integer>> diagonalDirections() {
        return List.of(Pair.of(-1, -1), Pair.of(1, -1), Pair.of(1, 1), Pair.of(-1, 1));
    }
}
