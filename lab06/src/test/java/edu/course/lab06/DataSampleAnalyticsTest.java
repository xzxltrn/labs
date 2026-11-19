package edu.course.lab06;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataSampleAnalyticsTest {
    private final DataSample first = new DataSample("S-03", "READY", "CSV", 4);
    private final DataSample second = new DataSample("S-02", "NEW", "API", 2);
    private final DataSample third = new DataSample("S-01", "READY", "CSV", 2);

    @Test
    void countsSamplesByStatus() {
        assertEquals(Map.of("READY", 2, "NEW", 1),
                DataSampleAnalytics.countByStatus(List.of(first, second, third)));
    }

    @Test
    void returnsUniqueSourcesInOrderOfFirstAppearance() {
        assertEquals(List.of("CSV", "API"), new ArrayList<>(
                DataSampleAnalytics.uniqueSources(List.of(first, second, third))));
    }

    @Test
    void sortsByFeatureCountThenIdWithoutChangingInput() {
        List<DataSample> original = List.of(first, second, third);
        List<DataSample> samples = new ArrayList<>(original);

        assertEquals(List.of(third, second, first),
                DataSampleAnalytics.sortedByFeatureCountAndId(samples));
        assertEquals(original, samples);
    }
}