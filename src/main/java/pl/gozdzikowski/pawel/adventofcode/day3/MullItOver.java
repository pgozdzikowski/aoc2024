package pl.gozdzikowski.pawel.adventofcode.day3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

public class MullItOver {
    private final Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    private final Pattern patternWithExcluding = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|don't\\(\\)|do\\(\\)");
    private final String DO_STATEMENT = "do()";
    private final String DONT_STATEMENT = "don't()";


    public long sumInstructionMultiplication(Input input) {
        return pattern.matcher(input.getContent()).results()
                .mapToLong((matchResult) -> Long.parseLong(matchResult.group(1)) * Long.parseLong(matchResult.group(2)))
                .sum();
    }

    public long sumInstructionMultiplicationWithExclusion(Input input) {
        String content = input.getContent();

        Matcher matcher = patternWithExcluding.matcher(content);

        long result = 0L;
        boolean shouldExecute = true;

        while(matcher.find()) {
            switch(matcher.group(0)) {
                case DONT_STATEMENT -> shouldExecute = false;
                case DO_STATEMENT -> shouldExecute = true;
                default -> {
                    if (shouldExecute) result += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
                }
            }
        }

        return result;
    }
}
