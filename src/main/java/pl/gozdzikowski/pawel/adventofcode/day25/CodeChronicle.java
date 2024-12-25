package pl.gozdzikowski.pawel.adventofcode.day25;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.Arrays;
import java.util.List;

public class CodeChronicle {

    public Long findNumberOfFittingKeys(Input input) {
        List<Element> elements = Arrays.stream(input.getContent().split("\n{2}")).map(Element::new).toList();
        List<Element> keys = elements.stream().filter(Element::isKey).toList();
        List<Element> locks = elements.stream().filter(Element::isLock).toList();

        return findFitting(keys, locks);
    }

    private long findFitting(List<Element> keys, List<Element> locks) {
        long fits = 0;

        for (Element key : keys) {
            for (Element lock : locks) {
                if (!key.overlaps(lock)) {
                    fits++;
                }
            }
        }
        return fits;
    }


    private static class Element {
        private String[][] elements;

        public Element(String element) {
            this.elements = Arrays.stream(element.split("\n")).map((el) -> el.split("")).toArray(String[][]::new);
        }

        public boolean isLock() {
            return Arrays.stream(this.elements[0]).allMatch((el) -> el.equals("#")) &&
            Arrays.stream(this.elements[this.elements.length - 1]).allMatch((el) -> el.equals("."));
        }

        public boolean isKey() {
            return Arrays.stream(this.elements[0]).allMatch((el) -> el.equals(".")) &&
                    Arrays.stream(this.elements[this.elements.length - 1]).allMatch((el) -> el.equals("#"));
        }

        public boolean overlaps(Element element) {

            for(int x =0; x < elements[0].length; x++) {
                for(int y =1;  y < elements.length - 1; y++) {
                    if((this.elements[y][x].equals("#") && !element.elements[y][x].equals(".")) || (element.elements[y][x].equals("#") && !elements[y][x].equals("."))) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

}
