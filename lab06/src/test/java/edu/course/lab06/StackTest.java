package edu.course.lab06;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StackTest {
    @Test
    void newStackIsEmpty() {
        Stack<String> stack = new Stack<>();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushIncreasesSize() {
        Stack<String> stack = new Stack<>();

        stack.push("first");
        stack.push("second");

        assertEquals(2, stack.size());
    }

    @Test
    void pushRejectsNull() {
        Stack<String> stack = new Stack<>();

        assertThrows(NullPointerException.class, () -> stack.push(null));
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekReturnsLastElementWithoutRemovingIt() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);

        assertEquals(Integer.valueOf(20), stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void peekThrowsWhenStackIsEmpty() {
        Stack<String> stack = new Stack<>();

        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void popRemovesElementsInReverseOrder() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");

        assertEquals("second", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("first", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popThrowsWhenStackIsEmpty() {
        Stack<String> stack = new Stack<>();

        assertThrows(NoSuchElementException.class, stack::pop);
    }
}