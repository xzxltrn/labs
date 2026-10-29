package edu.course.lab04;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HashMapExperimentTest {

    @Test
    void keysWithSameHashCodeKeepDifferentValues() {
        CollidingKey first = new CollidingKey("a");
        CollidingKey second = new CollidingKey("b");
        HashMap<CollidingKey, String> map = new HashMap<>();

        map.put(first, "value A");
        map.put(second, "value B");

        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);
        assertEquals(2, map.size());
        assertEquals("value A", map.get(new CollidingKey("a")));
        assertEquals("value B", map.get(new CollidingKey("b")));
    }

    @Test
    void changedMutableKeyCanNoLongerBeFound() {
        MutableKey key = new MutableKey("a");
        HashMap<MutableKey, String> map = new HashMap<>();
        map.put(key, "value");

        key.setName("b");

        assertEquals(1, map.size());
        assertNull(map.get(key));
        assertNull(map.get(new MutableKey("a")));
        assertFalse(map.containsKey(key));
    }
}