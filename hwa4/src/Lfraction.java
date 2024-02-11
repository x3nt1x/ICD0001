import java.util.*;

/**
 * This class represents fractions of form n/d where n and d are long integer numbers.
 * Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {
    private final long numerator;
    private final long denominator;

    public Lfraction(long numerator, long denominator) {
        if (denominator == 0L) {
            throw new RuntimeException("Fraction denominator must not be zero: ");
        }

        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Used source:
     * https://www.geeksforgeeks.org/java-program-to-compute-gcd/
     */
    public static long greatestCommonFactor(long a, long b) {
        return a == 0L ? Math.abs(b) : greatestCommonFactor(b % a, a);
    }

    /**
     * Used source:
     * https://www.geeksforgeeks.org/least-common-denominator-lcd/
     */
    public static long leastCommonDenominator(long a, long b) {
        return (a * b) / greatestCommonFactor(a, b);
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    public double toDouble() {
        return (double) numerator / denominator;
    }

    public long integerPart() {
        long gcf = greatestCommonFactor(numerator, denominator);

        long reducedNumerator = numerator / gcf;
        long reducedDenominator = denominator / gcf;

        return reducedNumerator / reducedDenominator;
    }

    public Lfraction fractionPart() {
        return this.simplify();
    }

    @Override
    public String toString() {
        return String.format("%s/%s", numerator, denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lfraction)) {
            return false;
        }

        Lfraction other = (Lfraction) o;
        return this.compareTo(other) == 0;
    }

    @Override
    public int compareTo(Lfraction fraction) {
        Lfraction original = this.simplify();
        Lfraction other = fraction.simplify();

        long lcd = leastCommonDenominator(original.denominator, other.denominator);

        long num1 = original.numerator * (lcd / original.denominator);
        long num2 = other.numerator * (lcd / other.denominator);

        if (num1 == 0L && num2 == 0L) {
            return Long.compare(this.integerPart(), fraction.integerPart());
        }

        return Long.compare(num1, num2);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Lfraction(numerator, denominator);
    }

    private Lfraction simplify() {
        long gcf = greatestCommonFactor(numerator, denominator);

        long reducedNumerator = numerator / gcf;
        long reducedDenominator = denominator / gcf;

        long integer = reducedNumerator / reducedDenominator;
        long numerator = reducedNumerator - (integer * reducedDenominator);

        return new Lfraction(numerator, reducedDenominator);
    }

    public static Lfraction toLfraction(double f, long d) {
        return new Lfraction(Math.round(f * d), d);
    }

    public Lfraction plus(Lfraction addend) {
        long numerator = (this.numerator * addend.denominator) + (addend.numerator * this.denominator);
        long denominator = this.denominator * addend.denominator;

        long gcf = greatestCommonFactor(numerator, denominator);

        return new Lfraction(numerator / gcf, denominator / gcf);
    }

    public Lfraction minus(Lfraction subtrahend) {
        return this.plus(subtrahend.opposite());
    }

    public Lfraction times(Lfraction multiplicand) {
        long numerator = this.numerator * multiplicand.numerator;
        long denominator = this.denominator * multiplicand.denominator;

        long gcf = greatestCommonFactor(numerator, denominator);

        return new Lfraction(numerator / gcf, denominator / gcf);
    }

    public Lfraction divideBy(Lfraction divisor) {
        if (divisor.numerator == 0L || this.numerator == 0L) {
            throw new RuntimeException("Can't divide by zero");
        }

        return this.times(divisor.inverse());
    }

    public Lfraction pow(int n) {
        if (n == 0) {
            return new Lfraction(1, 1);
        } else if (n == 1) {
            return new Lfraction(this.getNumerator(), this.getDenominator());
        } else if (n > 1) {
            return this.times(this.pow(n - 1));
        }

        return this.pow(Math.abs(n)).inverse();
    }

    public Lfraction inverse() {
        if (numerator == 0L) {
            throw new RuntimeException("Can't inverse zero");
        }

        if (numerator < 0L) {
            return new Lfraction(-denominator, Math.abs(numerator));
        }

        return new Lfraction(denominator, numerator);
    }

    public Lfraction opposite() {
        if (numerator < 0L) {
            return new Lfraction(Math.abs(numerator), denominator);
        }

        return new Lfraction(-numerator, denominator);
    }

    public static Lfraction valueOf(String input) {
        if (input == null) {
            throw new RuntimeException("Fraction is empty");
        }

        String formattedInput = input.trim();

        if (formattedInput.isEmpty()) {
            throw new RuntimeException("Fraction is empty");
        } else if (formattedInput.chars().filter(character -> character == '/').count() != 1) {
            throw new RuntimeException(String.format("Fraction must have only one separator: %s", input));
        }

        boolean startsWith = formattedInput.startsWith("/");
        boolean endsWith = formattedInput.endsWith("/");

        if (startsWith && endsWith) {
            throw new RuntimeException(String.format("Fraction is missing a numerator and a denominator: %s", input));
        } else if (startsWith) {
            throw new RuntimeException(String.format("Fraction is missing a numerator: %s", input));
        } else if (endsWith) {
            throw new RuntimeException(String.format("Fraction is missing a denominator: %s", input));
        }

        String[] values = formattedInput.split("/");
        long numerator;
        long denominator;

        try {
            numerator = Long.parseLong(values[0]);
        } catch (Exception ignore) {
            throw new RuntimeException(String.format("Fraction numerator must be a number: %s", input));
        }

        try {
            denominator = Long.parseLong(values[1]);
        } catch (Exception ignore) {
            throw new RuntimeException(String.format("Fraction denominator must be a number: %s", input));
        }

        try {
            return new Lfraction(numerator, denominator);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage() + input);
        }
    }
}