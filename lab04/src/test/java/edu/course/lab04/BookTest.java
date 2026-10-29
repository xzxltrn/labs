package edu.course.lab04;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookTest {

    @Test
    void valueIsFoundByAnotherBookWithSameIsbn() {
        HashMap<Book, String> shelves = new HashMap<>();
        shelves.put(new Book("978-1", "Java"), "shelf 1");

        String result = shelves.get(new Book("978-1", "Another title"));

        assertEquals("shelf 1", result);
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book first = new Book("978-1", "Java");
        Book second = new Book("978-2", "Java");

        assertNotEquals(first, second);
    }

    @Test
    void equalBooksHaveSameHashCode() {
        Book first = new Book("978-1", "Java");
        Book second = new Book("978-1", "Python");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void bookIsNotEqualToNullOrOtherType() {
        Book book = new Book("978-1", "Java");

        assertFalse(book.equals(null));
        assertFalse(book.equals("978-1"));
    }
}