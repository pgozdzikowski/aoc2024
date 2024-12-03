package pl.gozdzikowski.pawel.adventofcode.day2;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RedNosedReports {

    public int countNumOfSafeReports(Input input) {
        List<List<Integer>> reports = convertToReportsList(input);

        return reports.stream()
                .mapToInt((report) -> checkIfReportIsSafe(report) ? 1 : 0)
                .sum();
    }

    public int countNumOfSafeReportsWithPossibleOneInvalid(Input input) {
        List<List<Integer>> reports = convertToReportsList(input);

        return reports.stream()
                .mapToInt((report) -> checkIfSafeWithOnePossibleInvalid(report) ? 1 : 0)
                .sum();
    }

    private static List<List<Integer>> convertToReportsList(Input input) {
        return input.get().stream()
                .map((el) -> Arrays.stream(el.split("\\s+"))
                .map(Integer::parseInt).toList()).toList();
    }

    private boolean checkIfReportIsSafe(List<Integer> report) {
        return checkRulesOnDiff(IntStream.range(1, report.size())
                .mapToObj((el) -> report.get(el) - report.get(el - 1))
                .toList());
    }

    private static boolean checkRulesOnDiff(List<Integer> diffs) {
        return (diffs.stream().allMatch((el) -> el > 0) || diffs.stream().allMatch((el) -> el < 0)) &&
                diffs.stream().allMatch((el) -> Math.abs(el) >= 1 && Math.abs(el) <= 3);
    }

    private boolean checkIfSafeWithOnePossibleInvalid(List<Integer> report) {
        for (int i = 0; i < report.size(); ++i) {
            List<Integer> tmpReport = new ArrayList<>(report);
            tmpReport.remove(i);

            if (checkIfReportIsSafe(tmpReport))
                return true;
        }
        return false;
    }
}
