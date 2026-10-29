package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleRegistryTest {

    private DataSample sample(String id) {
        return new DataSample(id, "cat", new double[] {1.0, 2.0});
    }

    @Test
    void twoEqualKeysFindSameValue() {
        SampleRegistry registry = new SampleRegistry();
        DataSample value = sample("s1");
        registry.put(new SampleId("train", 1), value);

        DataSample result = registry.get(new SampleId("train", 1));

        assertSame(value, result);
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        SampleRegistry registry = new SampleRegistry();
        DataSample first = sample("s1");
        DataSample second = sample("s2");

        registry.put(new SampleId("train", 1), first);
        registry.put(new SampleId("train", 1), second);

        assertEquals(1, registry.size());
        assertSame(second, registry.get(new SampleId("train", 1)));
    }

    @Test
    void removeDeletesValue() {
        SampleRegistry registry = new SampleRegistry();
        registry.put(new SampleId("train", 1), sample("s1"));

        boolean removed = registry.remove(new SampleId("train", 1));

        assertTrue(removed);
        assertFalse(registry.contains(new SampleId("train", 1)));
        assertEquals(0, registry.size());
    }

    @Test
    void containsReturnsTrueOnlyForAddedKey() {
        SampleRegistry registry = new SampleRegistry();
        registry.put(new SampleId("train", 1), sample("s1"));

        assertTrue(registry.contains(new SampleId("train", 1)));
        assertFalse(registry.contains(new SampleId("train", 2)));
    }

    @Test
    void getReturnsNullForMissingKey() {
        SampleRegistry registry = new SampleRegistry();

        assertNull(registry.get(new SampleId("train", 1)));
    }

    @Test
    void putRejectsNull() {
        SampleRegistry registry = new SampleRegistry();

        assertThrows(IllegalArgumentException.class, () -> registry.put(null, sample("s1")));
    }
}