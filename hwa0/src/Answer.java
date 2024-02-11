import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Answer {
    public static void main(String[] param) {
        // conversion double -> string
        System.out.println(String.valueOf(9.11));

        // conversion string -> int
        System.out.println(Integer.parseInt("1337"));

        // "HH:mm:ss"
        System.out.println(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));

        // cos 45 degrees
        System.out.println(Math.cos(45));

        // table of square roots
        for (int i = 0; i < 100; i += 5) {
            System.out.println(Math.sqrt(i));
        }

        // reverse case
        String firstString = "ABcd12";
        String result = reverseCase(firstString);
        System.out.println("\"" + firstString + "\" -> \"" + result + "\"");

        // reverse string
        String initialString = "1234ab";
        StringBuilder reversed = new StringBuilder(initialString).reverse();
        System.out.println("\"" + initialString + "\" -> \"" + reversed + "\"");

        // count words
        String string = "How  many     words  here";
        int words = countWords(string);
        System.out.println(string + "\t" + words);

        /*
          HashMap tasks:
           - create
           - add pairs
           - print all keys
           - remove a key
           - print all pairs
        */
        Map<String, String> map = new HashMap<>();
        map.put("first", "first class");
        map.put("second", "second class");
        map.put("third", "third class");
        map.put("forth", "forth class");
        map.put("fifth", "fifth class");

        System.out.println(map.keySet());
        map.remove("second");
        System.out.println(map);

        /*
          Reverse list tasks:
           - create
           - add elements
           - print initial list
           - reverse list
           - print reversed list
        */
        List<Integer> list = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            list.add((new Random()).nextInt(1000));
        }

        System.out.println("Before reverse:  " + list);
        reverseList(list);
        System.out.println("After reverse: " + list);

        // maximum
        System.out.println("Maximum: " + maximum(list));

        // minimum
        System.out.println("Minimum: " + minimum(list));
    }

    /**
     * Finding the maximal element.
     *
     * @param items Collection of Comparable elements
     * @return maximal element
     * @throws NoSuchElementException if items is empty
     */
    static public <T extends Object & Comparable<? super T>>
    T maximum(Collection<? extends T> items) throws NoSuchElementException {
        return Collections.max(items);
    }

    /**
     * Finding the minimal element.
     *
     * @param items Collection of Comparable elements
     * @return minimal element
     * @throws NoSuchElementException if items is empty
     */
    static public <T extends Object & Comparable<? super T>>
    T minimum(Collection<? extends T> items) throws NoSuchElementException {
        return Collections.min(items);
    }

    /**
     * Counting the number of words.
     * Any number of any kind of whitespace symbols between words is allowed.
     *
     * @param text input
     * @return number of words in the text
     */
    public static int countWords(String text) {
        return text.trim().split("\\s+").length;
    }

    /**
     * Case-reverse.
     * upper -> lower AND lower -> upper.
     *
     * @param string input
     * @return processed string
     */
    public static String reverseCase(String string) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char letter = string.charAt(i);

            if (!Character.isAlphabetic(letter)) {
                result.append(letter);
                continue;
            }

            if (Character.isUpperCase(letter)) {
                result.append(Character.toLowerCase(letter));
            } else {
                result.append(Character.toUpperCase(letter));
            }
        }

        return result.toString();
    }

    /**
     * List reverse.
     * Do not create a new list.
     *
     * @param list list to reverse
     * @throws UnsupportedOperationException if list or its list-iterator does not support the set operation
     */
    public static <T> void reverseList(List<T> list) throws UnsupportedOperationException {
        Collections.reverse(list);
    }
}