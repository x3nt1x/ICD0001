import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class AnswerTest {
    static String string = null;
    static List<String> strings = null;
    static List<Double> doubles = null;
    static List<Integer> integers = null;

    @Test(timeout = 1000)
    public void testReverseCase() {
        string = "AAAbbb";
        assertEquals(string, "aaaBBB", Answer.reverseCase(string));

        string = "AaB,xX123";
        assertEquals(string, "aAb,Xx123", Answer.reverseCase(string));

        string = "JÜriöÖülestõus1Ää";
        assertEquals(string, "jüRIÖöÜLESTÕUS1äÄ", Answer.reverseCase(string));
    }

    @Test(timeout = 1000)
    public void testCountWords() {
        string = "four words are here";
        assertEquals(string, 4, Answer.countWords(string));

        string = "\t\ttwo\t   here\t ";
        assertEquals(string, 2, Answer.countWords(string));

        string = "three" + "\t" + "words here";
        assertEquals(string, 3, Answer.countWords(string));
    }

    @Test(timeout = 1000)
    public void testMaximum1() {
        integers = Collections.singletonList(1);
        assertEquals(integers.toString(), Integer.valueOf(1), Answer.maximum(integers));

        integers = Arrays.asList(1, -1, 0, -1, 2);
        assertEquals(integers.toString(), Integer.valueOf(2), Answer.maximum(integers));

        integers = Arrays.asList(-1, -1, -10, -1, -2);
        assertEquals(integers.toString(), Integer.valueOf(-1), Answer.maximum(integers));
    }

    @Test(timeout = 1000)
    public void testMaximum2() {
        strings = Collections.singletonList("A");
        assertEquals(strings.toString(), "A", Answer.maximum(strings));

        strings = Arrays.asList("A", "C", "B");
        assertEquals(strings.toString(), "C", Answer.maximum(strings));

        doubles = Arrays.asList(1., -1., 0., -1., 2.);
        assertEquals(doubles.toString(), Double.valueOf(2.), Answer.maximum(doubles));
    }

    @Test(expected = RuntimeException.class)
    public void testMaximumEmptyList() {
        Answer.maximum(new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void testMaximumNull() {
        Answer.maximum(null);
    }

    @Test(timeout = 1000)
    public void testMinimum1() {
        integers = Collections.singletonList(1);
        assertEquals(integers.toString(), Integer.valueOf(1), Answer.minimum(integers));

        integers = Arrays.asList(1, -1, 0, -1, 2);
        assertEquals(integers.toString(), Integer.valueOf(-1), Answer.minimum(integers));

        integers = Arrays.asList(-1, -1, -10, -1, -2);
        assertEquals(integers.toString(), Integer.valueOf(-10), Answer.minimum(integers));
    }

    @Test(timeout = 1000)
    public void testMinimum2() {
        strings = Collections.singletonList("G");
        assertEquals(strings.toString(), "G", Answer.minimum(strings));

        strings = Arrays.asList("B", "C", "A");
        assertEquals(strings.toString(), "A", Answer.minimum(strings));

        doubles = Arrays.asList(1., -3., 0., -1., 2.);
        assertEquals(doubles.toString(), Double.valueOf(-3.), Answer.minimum(doubles));
    }

    @Test(expected = RuntimeException.class)
    public void testMinimumEmptyList() {
        Answer.minimum(new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void testMinimumNull() {
        Answer.minimum(null);
    }

    @Test(timeout = 1000)
    public void testReverseList() {
        strings = Arrays.asList("A", "B");
        List<String> stringsCopy = new ArrayList<>(strings);
        Answer.reverseList(strings);
        assertEquals(stringsCopy.toString(), Arrays.asList("B", "A"), strings);

        strings = Arrays.asList("A", "C", "B");
        stringsCopy = new ArrayList<>(strings);
        Answer.reverseList(strings);
        assertEquals(stringsCopy.toString(), Arrays.asList("B", "C", "A"), strings);

        strings = Collections.singletonList("A");
        stringsCopy = new ArrayList<>(strings);
        Answer.reverseList(strings);
        assertEquals(stringsCopy.toString(), Collections.singletonList("A"), strings);

        doubles = Arrays.asList(1., -1., 0., -1., 2.);
        List<Double> doublesCopy = new ArrayList<>(doubles);
        Answer.reverseList(doubles);
        assertEquals(doublesCopy.toString(), Arrays.asList(2., -1., 0., -1., 1.), doubles);
    }

    @Test(expected = RuntimeException.class)
    public void testReverseListNull() {
        Answer.reverseList(null);
    }
}