import org.junit.Test;

public class PuzzleTest {
    @Test(timeout = 200000)
    public void test() {
        Puzzle.main(new String[] { "YKS", "KAKS", "KOLM" });                    // 234 solutions
        Puzzle.main(new String[] { "SEND", "MORE", "MONEY" });                  // 1 solution
        Puzzle.main(new String[] { "CBEHEIDGEI", "CBEHEIDGEI", "BBBBBBBBBB" }); // no solutions
    }

    @Test(timeout = 200000)
    public void testSpeed() {
        long start = System.currentTimeMillis();
        Puzzle.main(new String[] { "ABCDEFGHIJAB", "ABCDEFGHIJA", "ACEHJBDFGIAC" }); // 2 solutions
        long end = System.currentTimeMillis();

        long delta = end - start;
        System.out.println("Elapsed Time: " + delta + "ms");
    }
}