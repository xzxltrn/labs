package edu.course.lab06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {
    @Test
    void storesAndUpdatesString() {
        Box<String> box = new Box<>("first");
        assertEquals("first", box.get());

        box.set("second");
        assertEquals("second", box.get());

        // box.set(42); // Не компилируется: Box<String> принимает только строки.
    }

    @Test
    void storesAndUpdatesInteger() {
        Box<Integer> box = new Box<>(10);
        assertEquals(Integer.valueOf(10), box.get());

        box.set(20);
        assertEquals(Integer.valueOf(20), box.get());
    }
}