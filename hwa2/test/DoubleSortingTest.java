import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class DoubleSortingTest {
    static final double delta = 0.0000001;

    /**
     * Check whether an array is ordered.
     *
     * @param array sorted array
     * @return true if array is ordered and false when not
     */
    static boolean inOrder(double[] array) {
        if (array.length < 2) {
            return true;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }

        return true;
    }

    @Test(timeout = 1000)
    public void testTrivialArray() {
        double[] a = new double[] { 1., 3., 2. };
        double[] b = new double[] { 1., 2., 3. };
        String message = Arrays.toString(a);

        DoubleSorting.binaryInsertionSort(a);
        assertArrayEquals(message, b, a, delta);
    }

    @Test(timeout = 1000)
    public void testRandom1000() {
        double[] a = new double[1000];
        Random generator = new Random();

        for (int i = 0; i < a.length; i++) {
            a[i] = generator.nextDouble() * 100.;
        }

        DoubleSorting.binaryInsertionSort(a);
        assertTrue("array not sorted!", inOrder(a));
    }
}