import java.util.*;

/**
 * Comparison of sorting methods.
 * The same array of double values is used for all methods.
 *
 * Used sources:
 * https://www.interviewkickstart.com/learn/binary-insertion-sort
 * https://www.geeksforgeeks.org/binary-insertion-sort/
 */
public class DoubleSorting {
    static final int MAX_SIZE = 512000;
    static final int NUMBER_OF_ROUNDS = 4;

    public static void main(String[] args) {
        Random generator = new Random();

        final double[] data = new double[MAX_SIZE];

        for (int i = 0; i < MAX_SIZE; i++) {
            data[i] = generator.nextDouble() * 1000.;
        }

        int rightLimit = MAX_SIZE / (int) Math.pow(2., NUMBER_OF_ROUNDS);

        // start competition
        for (int round = 0; round < NUMBER_OF_ROUNDS; round++) {
            double[] copy;
            long start, end, delta;
            rightLimit = 2 * rightLimit;
            System.out.println();
            System.out.println("Length: " + rightLimit);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            insertionSort(copy);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Insertion sort: time (ms): ", delta / 1000000L);
            checkOrder(copy);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            binaryInsertionSort(copy);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Binary insertion sort: time (ms): ", delta / 1000000L);
            checkOrder(copy);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            JavaBinaryInsertionSort(copy);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Java binary insertion sort: time (ms): ", delta / 1000000L);
            checkOrder(copy);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            mergeSort(copy, 0, copy.length);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Merge sort: time (ms): ", delta / 1000000L);
            checkOrder(copy);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            quickSort(copy, 0, copy.length);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Quicksort: time (ms): ", delta / 1000000L);
            checkOrder(copy);

            copy = Arrays.copyOf(data, rightLimit);
            start = System.nanoTime();
            Arrays.sort(copy);
            end = System.nanoTime();
            delta = end - start;
            System.out.printf("%39s%11d%n", "Java API Arrays.sort: time (ms): ", delta / 1000000L);
            checkOrder(copy);
        }
    }

    public static void insertionSort(double[] array) {
        if (array.length < 2) {
            return;
        }

        for (int i = 1; i < array.length; i++) {
            int j;
            double key = array[i];

            for (j = i - 1; j >= 0; j--) {
                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
            }

            array[j + 1] = key;
        }
    }

    private static int findPos(double[] array, int length, double key) {
        int left = 0;
        int right = length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (array[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static void binaryInsertionSort(double[] array) {
        if (array.length < 2) {
            return;
        }

        for (int i = 1; i < array.length; i++) {
            double key = array[i];

            int pos = findPos(array, i, key);

            System.arraycopy(array, i - 1, array, i, 1);

            array[pos] = key;
        }
    }

    public static void JavaBinaryInsertionSort(double[] array) {
        if (array.length < 2) {
            return;
        }

        for (int i = 1; i < array.length; i++) {
            double key = array[i];

            int pos = Arrays.binarySearch(array, 0, i, key);

            if (pos < 0) {
                pos = -(pos + 1);
            }

            System.arraycopy(array, i - 1, array, i, 1);

            array[pos] = key;
        }
    }

    public static void mergeSort(double[] array, int left, int right) {
        if (array.length < 2) {
            return;
        }

        if ((right - left) < 2) {
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid, right);
        merge(array, left, mid, right);
    }

    static public void merge(double[] array, int left, int mid, int right) {
        if (array.length < 2 || (right - left) < 2 || mid <= left || mid >= right) {
            return;
        }

        int leftIndex = left;
        int rightIndex = mid;
        int mergedIndex = 0;
        double[] merged = new double[right - left];

        while (leftIndex < mid && rightIndex < right) {
            merged[mergedIndex++] = (array[leftIndex] > array[rightIndex]) ? array[rightIndex++] : array[leftIndex++];
        }

        if (leftIndex >= mid) {
            for (int i = rightIndex; i < right; i++) {
                merged[mergedIndex++] = array[i];
            }
        } else {
            for (int i = leftIndex; i < mid; i++) {
                merged[mergedIndex++] = array[i];
            }
        }

        System.arraycopy(merged, 0, array, left, right - left);
    }

    public static void quickSort(double[] array, int left, int right) {
        if (array == null || array.length < 1 || left < 0 || right <= left) {
            throw new IllegalArgumentException("quickSort: wrong parameters");
        }

        if ((right - left) < 2) {
            return;
        }

        int i = left;
        int j = right - 1;
        double pivot = array[(i + j) / 2];

        do {
            while (array[i] < pivot) {
                i++;
            }

            while (pivot < array[j]) {
                j--;
            }

            if (i <= j) {
                double temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        } while (i < j);

        if (left < j) {
            quickSort(array, left, j + 1);
        }

        if (i < right - 1) {
            quickSort(array, i, right);
        }
    }

    static void checkOrder(double[] array) {
        if (array.length < 2) {
            return;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                throw new IllegalArgumentException("array not ordered: " + "array[" + i + "]=" + array[i] + " array[" + (i + 1) + "]=" + array[i + 1]);
            }
        }
    }
}