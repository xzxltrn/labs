package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DataSampleAnalytics {
    private DataSampleAnalytics() {
    }

    public static Map<String, Integer> countByStatus(List<DataSample> samples) {
        Map<String, Integer> counts = new LinkedHashMap<>();

        for (DataSample sample : samples) {
            String status = sample.status();
            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }

        return counts;
    }

    public static Set<String> uniqueSources(List<DataSample> samples) {
        Set<String> sources = new LinkedHashSet<>();

        for (DataSample sample : samples) {
            sources.add(sample.source());
        }

        return sources;
    }

    public static List<DataSample> sortedByFeatureCountAndId(List<DataSample> samples) {
        List<DataSample> sorted = new ArrayList<>(samples);
        sorted.sort(Comparator.comparingInt(DataSample::featureCount)
                .thenComparing(DataSample::id));
        return sorted;
    }
}