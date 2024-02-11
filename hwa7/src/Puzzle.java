import java.util.*;

/**
 * Used sources:
 * https://leetcode.ca/2019-06-29-1307-Verbal-Arithmetic-Puzzle/
 */
public class Puzzle {
    public static void main(String[] args) {
        String first = args[0];
        String second = args[1];
        String sum = args[2];

        String[] words = new String[] { first, second };
        List<Map<Character, Integer>> solutions = findSolutions(words, sum);

        System.out.println(first + " + " + second + " = " + sum);
        System.out.println("Solutions: " + solutions.size());
        System.out.println("Solution: " + (!solutions.isEmpty() ? solutions.get(0) : "No solution"));
        System.out.println();
    }

    public static List<Map<Character, Integer>> findSolutions(String[] words, String result) {
        int resultLength = result.length();
        List<Map<Character, Integer>> solutions = new ArrayList<>();

        // map to store the digit assigned to each letter
        Map<Character, Integer> letterDigitMap = new HashMap<>();

        // set to keep track of letters that cannot be assigned a leading zero
        Set<Character> leadingSet = new HashSet<>();

        // initialize leadingSet
        for (String word : words) {
            if (word.length() > resultLength) {
                return solutions;
            }

            if (word.length() > 1) {
                leadingSet.add(word.charAt(0));
            }
        }

        if (result.length() > 1) {
            leadingSet.add(result.charAt(0));
        }

        // track used digits and carry values
        boolean[] used = new boolean[10];
        int[] carry = new int[resultLength + 1];

        backtrack(words, result, used, carry, 0, 0, leadingSet, letterDigitMap, solutions);

        return solutions;
    }

    public static void backtrack(String[] words, String result,
                                 boolean[] used, int[] carry, int position, int wordIndex,
                                 Set<Character> leadingSet,
                                 Map<Character, Integer> letterDigitMap,
                                 List<Map<Character, Integer>> solutions) {
        /*
            if the current position is at the end of the result word,
            check if the final carry is zero, i.e. a valid solution
        */
        if (position == result.length()) {
            if (carry[position] == 0) {
                solutions.add(new HashMap<>(letterDigitMap));
            }

            return;
        }

        // if there are more words to process
        if (wordIndex < words.length) {
            String word = words[wordIndex];
            int wordLength = word.length();

            /*
                If the word has fewer digits than the current position
                or the current letter at the reversed position is a digit, move to the next word.
            */
            if (wordLength <= position || letterDigitMap.containsKey(word.charAt(wordLength - position - 1))) {
                backtrack(words, result, used, carry, position, wordIndex + 1, leadingSet, letterDigitMap, solutions);
            } else {
                char letter = word.charAt(wordLength - position - 1);
                int start = leadingSet.contains(letter) ? 1 : 0;

                // try assigning digits to the current letter
                for (int i = start; i <= 9; i++) {
                    if (used[i]) {
                        continue;
                    }

                    used[i] = true;
                    letterDigitMap.put(letter, i);
                    backtrack(words, result, used, carry, position, wordIndex + 1, leadingSet, letterDigitMap, solutions);

                    // backtrack by undoing the assignment for the next iteration
                    used[i] = false;
                    letterDigitMap.remove(letter);
                }
            }

            return;
        }

        // calculate the sum of digits at the current position, considering carry
        int remain = carry[position];

        for (String word : words) {
            if (word.length() > position) {
                char letter = word.charAt(word.length() - position - 1);
                remain += letterDigitMap.get(letter);
            }
        }

        // update carry and remainder
        carry[position + 1] = remain / 10;
        remain %= 10;

        char letter = result.charAt(result.length() - position - 1);

        // check if the assignment is valid for the current letter in the result
        if (letterDigitMap.containsKey(letter) && letterDigitMap.get(letter) == remain) {
            backtrack(words, result, used, carry, position + 1, 0, leadingSet, letterDigitMap, solutions);
        } else if (!letterDigitMap.containsKey(letter) && !used[remain] && !(leadingSet.contains(letter) && remain == 0)) {
            // if the assignment is valid, continue with the next position
            used[remain] = true;
            letterDigitMap.put(letter, remain);
            backtrack(words, result, used, carry, position + 1, 0, leadingSet, letterDigitMap, solutions);

            // backtrack by undoing the assignment for the next iteration
            used[remain] = false;
            letterDigitMap.remove(letter);
        }
    }
}