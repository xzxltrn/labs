package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    @Test
    void returnsTrueForZero() {
        boolean result = CourseToolkit.isEven(0);
        assertTrue(result);
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);

        assertTrue(result);
    }

    @Test
    void isPrimeReturnsFalseForOne() {
        boolean result = CourseToolkit.isPrime(1);

        assertFalse(result);
    }

    @Test
    void isPrimeReturnsFalseForNegativeNumber() {
        boolean result = CourseToolkit.isPrime(-7);

        assertFalse(result);
    }

    @Test
    void isPrimeReturnsTrueForTwo() {
        boolean result = CourseToolkit.isPrime(2);

        assertTrue(result);
    }

    @Test
    void isPrimeReturnsTrueForPrimeNumber() {
        boolean result = CourseToolkit.isPrime(13);

        assertTrue(result);
    }

    @Test
    void isPrimeReturnsFalseForCompositeNumber() {
        boolean result = CourseToolkit.isPrime(15);

        assertFalse(result);
    }

    @Test
    void isPrimeReturnsFalseForSquareOfPrime() {
        boolean result = CourseToolkit.isPrime(49);

        assertFalse(result);
    }

    @Test
    void isPalindromeReturnsTrueForPalindrome() {
        boolean result = CourseToolkit.isPalindrome("level");

        assertTrue(result);
    }

    @Test
    void isPalindromeReturnsFalseForNonPalindrome() {
        boolean result = CourseToolkit.isPalindrome("java");

        assertFalse(result);
    }

    @Test
    void isPalindromeIsCaseSensitive() {
        boolean result = CourseToolkit.isPalindrome("Level");

        assertFalse(result);
    }

    @Test
    void isPalindromeTreatsSpacesAsSignificant() {
        boolean result = CourseToolkit.isPalindrome("lev el");

        assertFalse(result);
    }

    @Test
    void isPalindromeThrowsForNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    @Test
    void averageReturnsFractionalResult() {
        int[] values = { 1, 2 };

        double result = CourseToolkit.average(values);

        assertEquals(1.5, result, 1e-9);
    }

    @Test
    void averageWorksWithNegativeNumbers() {
        int[] values = { -2, -4, -6 };

        double result = CourseToolkit.average(values);

        assertEquals(-4.0, result, 1e-9);
    }

    @Test
    void averageDoesNotModifyArray() {
        int[] values = { 3, 1, 2 };
        int[] expected = { 3, 1, 2 };

        CourseToolkit.average(values);

        assertArrayEquals(expected, values);
    }

    @Test
    void averageThrowsForNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
    }

    @Test
    void averageThrowsForEmptyArray() {
        int[] values = {};

        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(values));
    }

    @Test
    void minReturnsSmallestElement() {
        int[] values = { 5, 2, 8 };

        int result = CourseToolkit.min(values);

        assertEquals(2, result);
    }

    @Test
    void minWorksWithNegativeNumbers() {
        int[] values = { -3, -1, -7 };

        int result = CourseToolkit.min(values);

        assertEquals(-7, result);
    }

    @Test
    void minThrowsForEmptyArray() {
        int[] values = {};

        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.min(values));
    }

    @Test
    void maxReturnsLargestElement() {
        int[] values = { 5, 2, 8 };

        int result = CourseToolkit.max(values);

        assertEquals(8, result);
    }

    @Test
    void maxWorksWithNegativeNumbers() {
        int[] values = { -3, -1, -7 };

        int result = CourseToolkit.max(values);

        assertEquals(-1, result);
    }

    @Test
    void maxThrowsForNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.max(null));
    }
}
