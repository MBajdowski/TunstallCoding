package FileAnalysis;

import Tunstall.Tunstall;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class FileAnalyzer {

    public static Collection<HistogramData> calculateHistogramData(final Tunstall tunstall) {
        Map<Integer, HistogramData> histogramDataMap = new HashMap<>();
        for (int i = 0; i < tunstall.getN(); i++) {
            histogramDataMap.put(i, new HistogramData(i));
        }

        //noinspection SuspiciousMethodCalls
        tunstall.getInputBytes().stream()
                .forEach(singleByte ->
                        histogramDataMap.get(singleByte & 0xFF).increment());

        return histogramDataMap.values().stream()
                .sorted((o1, o2) -> Integer.compare(o1.getX(), o2.getX()))
                .collect(toList());
    }

    public static double calculateEntropy(final Tunstall tunstall) {
        return -tunstall.getInputBytes().stream()
                .distinct()
                .filter(Objects::nonNull)
                .map(tunstall::getProbability)
                .map(probability -> probability * (Math.log(probability) / Math.log(2)))
                .reduce((x, y) -> x + y)
                .orElse(0d);
    }

}
