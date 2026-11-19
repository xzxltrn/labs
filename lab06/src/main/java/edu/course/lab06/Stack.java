package edu.course.lab06;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Stack<T> {
    private final ArrayList<T> elements = new ArrayList<>();

    public void push(T value) {
        elements.add(Objects.requireNonNull(value, "Stack does not accept null"));
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elements.get(size() - 1);
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elements.remove(size() - 1);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}