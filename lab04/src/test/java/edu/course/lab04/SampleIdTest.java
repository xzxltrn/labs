package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SampleIdTest {

    @Test
    void idsWithSameFieldsAreEqualAndHaveSameHashCode() {
        SampleId first = new SampleId("train", 1);
        SampleId second = new SampleId("train", 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void idsWithDifferentIndexAreNotEqual() {
        assertNotEquals(new SampleId("train", 1), new SampleId("train", 2));
    }

    @Test
    void idsWithDifferentDatasetAreNotEqual() {
        assertNotEquals(new SampleId("train", 1), new SampleId("test", 1));
    }

    @Test
    void idIsNotEqualToNullOrOtherType() {
        SampleId id = new SampleId("train", 1);

        assertFalse(id.equals(null));
        assertFalse(id.equals("train#1"));
    }

    @Test
    void constructorRejectsBlankDatasetName() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId("  ", 1));
    }

    @Test
    void constructorRejectsNegativeIndex() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId("train", -1));
    }
}