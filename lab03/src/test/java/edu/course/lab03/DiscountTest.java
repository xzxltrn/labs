package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountTest {

    @Test
    void noDiscountReturnsOriginalPrice() {
        DiscountPolicy policy = new NoDiscount();

        double result = policy.apply(100.0);

        assertEquals(100.0, result, 1e-9);
    }

    @Test
    void percentDiscountOfTwentyGivesEightyForHundred() {
        DiscountPolicy policy = new PercentDiscount(20);

        double result = policy.apply(100.0);

        assertEquals(80.0, result, 1e-9);
    }

    @Test
    void percentDiscountRejectsPercentAboveHundred() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(150));
    }

    @Test
    void discountRejectsNegativePrice() {
        DiscountPolicy policy = new PercentDiscount(20);

        assertThrows(IllegalArgumentException.class, () -> policy.apply(-1.0));
    }

    @Test
    void calculatorWorksWithAnyPolicy() {
        PriceCalculator withoutDiscount = new PriceCalculator(new NoDiscount());
        PriceCalculator withDiscount = new PriceCalculator(new PercentDiscount(20));

        assertEquals(100.0, withoutDiscount.calculate(100.0), 1e-9);
        assertEquals(80.0, withDiscount.calculate(100.0), 1e-9);
    }
}