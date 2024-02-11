import static org.junit.Assert.*;

import org.junit.Test;

public class LfractionTest {
    @Test(timeout = 1000)
    public void testPlus() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = new Lfraction(4, 15);
        Lfraction sum = f1.plus(f2);
        assertEquals("Wrong sum: <" + f1 + "> + <" + f2 + ">", new Lfraction(2, 3), sum);

        Lfraction result = sum.plus(sum);
        assertEquals("Wrong sum: <2/3> + <2/3>", new Lfraction(4, 3), result);
        assertEquals("Do not change the arguments of the sum", new Lfraction(2, 3), sum);

        f1 = new Lfraction(-1, 250);
        f2 = new Lfraction(-1, 375);
        sum = f1.plus(f2);
        assertEquals("Wrong sum: <" + f1 + "> + <" + f2 + ">", new Lfraction(-1, 150), sum);

        f1 = new Lfraction(1, 221);
        f2 = new Lfraction(1, 323);
        sum = f1.plus(f2);
        assertEquals("Wrong sum: <" + f1 + "> + <" + f2 + ">", new Lfraction(32, 4199), sum);

        f1 = new Lfraction(1, 39203);
        f2 = new Lfraction(1, 41989);
        sum = f1.plus(f2);
        assertEquals("Wrong sum: <" + f1 + "> + <" + f2 + ">", new Lfraction(408, 8271833), sum);

        f1 = new Lfraction(-2, 5);
        f2 = new Lfraction(2, 5);
        sum = f1.plus(f2);
        assertEquals("Wrong sum: <" + f1 + "> + <" + f2 + ">", new Lfraction(0, 1), sum);
    }

    @Test(timeout = 1000)
    public void testTimes() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = new Lfraction(4, 15);
        Lfraction product = f1.times(f2);
        assertEquals("Wrong product: <" + f1 + "> * <" + f2 + ">", new Lfraction(8, 75), product);

        f1 = new Lfraction(-3, 5);
        f2 = new Lfraction(-5, 7);
        product = f1.times(f2);
        assertEquals("Wrong product: <" + f1 + "> * <" + f2 + ">", new Lfraction(3, 7), product);

        Lfraction result = product.times(product);
        assertEquals("Wrong product: <3/7> * <3/7>", new Lfraction(9, 49), result);
        assertEquals("Do not change the arguments of the product", new Lfraction(3, 7), product);

        f1 = new Lfraction(0, 1);
        f2 = new Lfraction(2, 3);
        product = f1.times(f2);
        assertEquals("Wrong product: <" + f1 + "> * <" + f2 + ">", new Lfraction(0, 1), product);

        f1 = new Lfraction(3, 2);
        f2 = new Lfraction(2, 3);
        product = f1.times(f2);
        assertEquals("Wrong product: <" + f1 + "> * <" + f2 + ">", new Lfraction(1, 1), product);

        f1 = new Lfraction(3, 5);
        f2 = new Lfraction(5, 7);
        product = f1.times(f2);
        assertEquals("Result must be reduced: <3/5> * <5/7> = <3/7>", new Lfraction(3, 7), product);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateZeroDenominator() {
        new Lfraction(1, 0);
    }

    @Test(expected = RuntimeException.class)
    public void testZeroInverse() {
        new Lfraction(0, 1).inverse();
    }

    @Test(timeout = 1000)
    public void testClone() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = null;

        try {
            f2 = (Lfraction) f1.clone();
        } catch (CloneNotSupportedException ignored) {
        }

        assertNotSame("Clone must differ from original", f2, f1);
        assertEquals("Clone must be equal to original", f1, f2);

        f2.plus(f1);
        assertEquals("Clone must be independent from original", new Lfraction(2, 5), f2);
    }

    @Test(timeout = 1000)
    public void testToString() {
        String string = new Lfraction(1, 4).toString();
        assertTrue(string + " must represent quarter", (string.indexOf('1') < string.indexOf('4')) && (string.indexOf('1') >= 0));

        string = new Lfraction(-1, 5).toString();
        assertTrue(string + " does not contain minus", string.indexOf('-') >= 0);
    }

    @Test(expected = RuntimeException.class)
    public void testDivideByZero() {
        Lfraction f1 = new Lfraction(1, 5);
        Lfraction f2 = new Lfraction(0, 1);

        f1.divideBy(f2);
    }

    @Test(expected = RuntimeException.class)
    public void testValueOfErr1() {
        Lfraction.valueOf("2/4/6");
    }

    @Test(expected = RuntimeException.class)
    public void testValueOfErr2() {
        Lfraction.valueOf("2/4/");
    }

    @Test(timeout = 1000)
    public void testMinus() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = new Lfraction(4, 15);

        Lfraction difference = f1.minus(f2);
        assertEquals("Wrong difference: <" + f1 + "> - <" + f2 + ">", new Lfraction(2, 15), difference);

        Lfraction result = difference.minus(difference);
        assertEquals("Wrong difference: <2/15> - <2/15>", new Lfraction(0, 1), result);
        assertEquals("Do not change the arguments of the difference", new Lfraction(2, 15), difference);

        f1 = new Lfraction(-2, 5);
        f2 = new Lfraction(-4, 15);
        difference = f1.minus(f2);
        assertEquals("Wrong difference: <" + f1 + "> - <" + f2 + ">", new Lfraction(-2, 15), difference);
    }

    @Test(timeout = 1000)
    public void testDivideBy() {
        Lfraction f1 = new Lfraction(-2, 7);
        Lfraction f2 = new Lfraction(-1, 14);

        Lfraction quotient = f1.divideBy(f2);
        assertEquals("Wrong quotient: <" + f1 + "> / <" + f2 + ">", new Lfraction(4, 1), quotient);

        quotient = f2.divideBy(f1);
        assertEquals("Wrong quotient: <" + f1 + "> / <" + f2 + ">", new Lfraction(1, 4), quotient);
        assertEquals("Do not change the arguments of the quotient", new Lfraction(1, 4), quotient);

        Lfraction result = quotient.divideBy(quotient);
        assertEquals("Wrong quotient: <1/4> / <1/4>", new Lfraction(1, 1), result);
    }

    @Test(timeout = 1000)
    public void testOpposite() {
        Lfraction f1 = new Lfraction(1, 6);
        Lfraction f2 = f1.opposite();
        assertEquals("Wrong opposite", new Lfraction(-1, 6), f2);
        assertEquals("Do not change the argument of opposite", new Lfraction(1, 6), f1);

        f1 = new Lfraction(-4, 75);
        f2 = f1.opposite();
        assertEquals("Wrong opposite", new Lfraction(4, 75), f2);

        f1 = new Lfraction(0, 1);
        f2 = f1.opposite();
        assertEquals("Zero must be neutral to opposite", f1, f2);
    }

    @Test(timeout = 1000)
    public void testInverse() {
        Lfraction f1 = new Lfraction(2, 3);
        Lfraction f2 = f1.inverse();
        assertEquals("Wrong inverse", new Lfraction(3, 2), f2);
        assertEquals("Do not change the argument of inverse", new Lfraction(2, 3), f1);

        f1 = new Lfraction(-4, 75);
        f2 = f1.inverse();
        assertEquals("Wrong inverse", new Lfraction(-75, 4), f2);
        assertTrue("Denominator must always be positive", f2.getDenominator() > 0);

        f1 = new Lfraction(1, 1);
        f2 = f1.inverse();
        assertEquals("1 must be neutral to inverse", f1, f2);
    }

    @Test(timeout = 1000)
    public void testGetters() {
        Lfraction f1 = new Lfraction(2, 3);
        long numerator = f1.getNumerator();
        assertEquals("Wrong numerator ", 2, numerator);

        f1 = new Lfraction(-4, 75);
        numerator = f1.getNumerator();
        assertEquals("Wrong numerator", -4, numerator);

        f1 = new Lfraction(0, 7);
        numerator = f1.getNumerator();
        assertEquals("Wrong numerator", 0, numerator);

        f1 = new Lfraction(2, 3);
        long denominator = f1.getDenominator();
        assertEquals("wrong denominator ", 3, denominator);

        f1 = new Lfraction(-4, 75);
        denominator = f1.getDenominator();
        assertEquals("Wrong denominator", 75, denominator);
    }

    @Test(timeout = 1000)
    public void testIntegerPart() {
        Lfraction f1 = new Lfraction(2, 3);
        long integer = f1.integerPart();
        assertEquals("Wrong integer part ", 0, integer);

        f1 = new Lfraction(3, 2);
        integer = f1.integerPart();
        assertEquals("Wrong integer part ", 1, integer);

        f1 = new Lfraction(32, 3);
        integer = f1.integerPart();
        assertEquals("Wrong integer part ", 10, integer);

        f1 = new Lfraction(33, 3);
        integer = f1.integerPart();
        assertEquals("Wrong integer part ", 11, integer);

        f1 = new Lfraction(-33, 3);
        integer = f1.integerPart();
        assertEquals("Wrong integer part ", -11, integer);
    }

    @Test(timeout = 1000)
    public void testLfractionPart() {
        Lfraction f1 = new Lfraction(2, 3);
        Lfraction fraction = f1.fractionPart();
        assertEquals("Wrong fraction part ", new Lfraction(2, 3), fraction);

        f1 = new Lfraction(3, 2);
        fraction = f1.fractionPart();
        assertEquals("Wrong fraction part ", new Lfraction(1, 2), fraction);

        f1 = new Lfraction(32, 3);
        fraction = f1.fractionPart();
        assertEquals("Wrong fraction part ", new Lfraction(2, 3), fraction);

        f1 = new Lfraction(33, 3);
        fraction = f1.fractionPart();
        assertEquals("Wrong fraction part ", new Lfraction(0, 1), fraction);

        f1 = new Lfraction(-33, 3);
        fraction = f1.fractionPart();
        assertEquals("Wrong fraction part ", new Lfraction(0, 1), fraction);

        f1 = new Lfraction(-5, 4);
        fraction = f1.fractionPart();
        assertTrue("Wrong fraction part " + fraction.toString() + " for " + f1, fraction.equals(new Lfraction(-1, 4))
                                                                             || fraction.equals(new Lfraction(3, 4)));
    }

    @Test(timeout = 1000)
    public void testEquals() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = new Lfraction(4, 10);
        assertEquals("<2/5> must be equal to <4/10>", f1, f2);
        assertNotEquals("<2/5> is not <3/5>", f1, new Lfraction(3, 5));

        f1 = new Lfraction(12345678901234567L, 1L);
        f2 = new Lfraction(12345678901234568L, 1L);
        assertNotEquals("<12345678901234567/1> is not <12345678901234568/1>", f1, f2);
    }

    @Test(timeout = 1000)
    public void testCompareTo() {
        Lfraction f1 = new Lfraction(2, 5);
        Lfraction f2 = new Lfraction(4, 7);
        Lfraction f3 = new Lfraction(4, 10);
        assertTrue("<2/5> must be equal to <4/10>", f1.compareTo(f3) == 0);
        assertTrue("<2/5> must be less than <4/7>", f1.compareTo(f2) < 0);
        assertTrue("<4/7> must be greater than <2/5>", f2.compareTo(f1) > 0);

        f1 = new Lfraction(-2, 5);
        f2 = new Lfraction(-4, 7);
        f3 = new Lfraction(-4, 10);
        assertTrue("<-2/5> must be equal to <-4/10>", f1.compareTo(f3) == 0);
        assertTrue("<-4/7> must be less than <-2/5>", f2.compareTo(f1) < 0);
        assertTrue("<-2/5> must be greater than <-4/7>", f1.compareTo(f2) > 0);

        f1 = new Lfraction(12345678901234567L, 1L);
        f2 = new Lfraction(12345678901234568L, 1L);
        assertNotEquals("<12345678901234567/1> is not <12345678901234568/1>", 0, f1.compareTo(f2));
    }

    @Test(timeout = 1000)
    public void testToLfraction() {
        Lfraction f1 = Lfraction.toLfraction(Math.PI, 7);
        Lfraction f2 = new Lfraction(22, 7);
        assertEquals("Math.PI must be nearly <22/7>", f1, f2);

        f1 = Lfraction.toLfraction(-10., 2);
        f2 = new Lfraction(-20, 2);
        assertEquals("-10. must be <-20/2>", f1, f2);
    }

    @Test(timeout = 1000)
    public void testToDouble() {
        Lfraction fraction = new Lfraction(2, 5);
        assertEquals("<2/5> must be nearly 0.4", 0.4, fraction.toDouble(), 0.00001);

        fraction = new Lfraction(-17, 100);
        assertEquals("<-17/100> must be nearly -0.17", -0.17, fraction.toDouble(), 0.00001);
    }

    @Test(timeout = 1000)
    public void testValueOf() {
        Lfraction fraction = new Lfraction(2, 5);
        assertEquals("valueOf must read back what toString outputs. ", fraction, Lfraction.valueOf(fraction.toString()));

        fraction = new Lfraction(-17, 100);
        assertEquals("valueOf must read back what toString outputs. ", fraction, Lfraction.valueOf(fraction.toString()));
    }

    @Test(timeout = 1000)
    public void testHashCode() {
        Lfraction f1 = new Lfraction(1, 2);
        Lfraction f2 = new Lfraction(1, 2);
        Lfraction f3 = null;
        Lfraction f4 = null;

        try {
            f3 = (Lfraction) f1.clone();
        } catch (CloneNotSupportedException ignored) {
        }

        int h1 = f1.hashCode();
        int h2 = f2.hashCode();
        int h3 = f3.hashCode();

        assertEquals("hashCode has to be same for equal objects", h1, h2);
        assertEquals("hashCode has to be same for clone objects", h1, h3);
        assertEquals("hashCode has to be same for the same object", h1, f1.hashCode());

        f2 = new Lfraction(0, 2);
        f3 = new Lfraction(1, 3);
        f4 = new Lfraction(3, 1);

        h2 = f2.hashCode();
        h3 = f3.hashCode();
        int h4 = f4.hashCode();

        assertNotEquals("hashCode must not be symmetrical", h3, h4);
        assertNotEquals("hashCode does not depend on numerator", h1, h2);
        assertNotEquals("hashCode does not depend on denominator", h1, h3);
    }

    @Test(timeout = 1000)
    public void testPow() {
        Lfraction f1 = new Lfraction(3, 4);
        Lfraction f2 = new Lfraction(-3, 4);

        assertEquals(new Lfraction(4, 3), f1.pow(-1));
        assertEquals(new Lfraction(64, 27), f1.pow(-3));
        assertEquals(new Lfraction(27, 64), f1.pow(3));
        assertEquals(new Lfraction(-27, 64), f2.pow(3));

        Lfraction f3 = f1.pow(1);
        assertEquals("Fraction to the power of 1 must be equal to itself", f1, f3);
        assertNotSame("Fraction to the power of 1 must not be itself", f1, f3);

        assertEquals("Fraction to the power of 0 must be <1/1>", new Lfraction(1, 1), f1.pow(0));
    }

    @Test(expected = RuntimeException.class)
    public void testPowerZero1() {
        new Lfraction(0, 5).pow(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testPowerZero2() {
        new Lfraction(0, 5).pow(-3);
    }
}