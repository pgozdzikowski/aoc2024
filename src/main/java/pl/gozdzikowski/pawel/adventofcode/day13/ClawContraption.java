package pl.gozdzikowski.pawel.adventofcode.day13;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClawContraption {

    Pattern pattern = Pattern.compile("(\\d+)");


    public long calculateTotalLowestNumToWinPrice(Input input) {
        String[] splitted = input.getContent().split("\\n\\n");
        List<Machine> machines = parseMachines(splitted);

        long result = 0L;
        for (Machine machine : machines) {
            result += calculateLowerNumberOfTokens(machine);
        }


        return result;
    }

    private Long calculateLowerNumberOfTokens(Machine machine) {
        List<State> states = new ArrayList<>();
        for (int bA = 0; bA < 100; ++bA) {
            for (int bB = 0; bB < 100; ++bB) {
                if ((bA * machine.buttonA.left() + bB * machine.buttonB.left()) == machine.prize().left()
                        && (bA * machine.buttonA.right() + bB * machine.buttonB.right()) == machine.prize().right()
                ) {
                    states.add(new State(bA, bB));
                }
            }
        }

        return states.stream().mapToLong(State::calculateCost)
                .sorted().findFirst()
                .orElse(0L);
    }


    private List<Machine> parseMachines(String[] splitted) {
        List<Machine> machines = new ArrayList<>();
        for (String line : splitted) {
            List<Integer> inputMappedToInteger = pattern.matcher(line).results().map((el) -> el.group(1)).map(Integer::parseInt).toList();
            Pair<Integer, Integer> buttonA = Pair.of(inputMappedToInteger.get(0), inputMappedToInteger.get(1));
            Pair<Integer, Integer> buttonB = Pair.of(inputMappedToInteger.get(2), inputMappedToInteger.get(3));
            Pair<Integer, Integer> prize = Pair.of(inputMappedToInteger.get(4), inputMappedToInteger.get(5));

            machines.add(new Machine(buttonA, buttonB, prize));
        }
        return machines;
    }

    record Machine(Pair<Integer, Integer> buttonA,
                   Pair<Integer, Integer> buttonB,
                   Pair<Integer, Integer> prize) {
    }

    record State(int numOfButtonA, int numOfButtonB) {
        int calculateCost() {
            return 3 * numOfButtonA + numOfButtonB;
        }
    }
}
