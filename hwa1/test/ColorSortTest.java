import static org.junit.Assert.*;

import org.junit.Test;

public class ColorSortTest {
    /**
     * Number of milliseconds allowed to spend for sorting 1 million elements
     */
    static int threshold = 25;

    /**
     * Test data
     */
    static int red = 0;
    static int green = 0;
    static int blue = 0;
    static ColorSort.Color[] balls = null;

    /**
     * Correctness check for the result
     */
    static boolean check(ColorSort.Color[] balls, int r, int g, int b) {
        if (balls == null) {
            return false;
        }

        int total = r + g + b;
        int length = balls.length;

        if (r < 0 || g < 0 || b < 0 || length != total) {
            return false;
        }

        if (length == 0) {
            return true;
        }

        for (int i = 0; i < r; i++) {
            if (balls[i] != ColorSort.Color.RED) {
                return false;
            }
        }

        for (int i = r; i < r + g; i++) {
            if (balls[i] != ColorSort.Color.GREEN) {
                return false;
            }
        }

        for (int i = r + g; i < length; i++) {
            if (balls[i] != ColorSort.Color.BLUE) {
                return false;
            }
        }

        return true;
    }

    @Test(timeout = 1000)
    public void testFunctionality() {
        red = 0;
        green = 0;
        blue = 0;
        balls = new ColorSort.Color[100000];

        for (int i = 0; i < balls.length; i++) {
            double random = Math.random();

            if (random < 1. / 3.) {
                balls[i] = ColorSort.Color.RED;
                red++;
            } else if (random > 2. / 3.) {
                balls[i] = ColorSort.Color.BLUE;
                blue++;
            } else {
                balls[i] = ColorSort.Color.GREEN;
                green++;
            }
        }

        ColorSort.reorder(balls);
        assertTrue("Result incorrect", check(balls, red, green, blue));
    }

    @Test(timeout = 1000)
    public void testShort() {
        red = 0;
        green = 0;
        blue = 0;
        balls = new ColorSort.Color[1];

        double random = Math.random();

        if (random < 1. / 3.) {
            balls[0] = ColorSort.Color.RED;
            red++;
        } else if (random > 2. / 3.) {
            balls[0] = ColorSort.Color.BLUE;
            blue++;
        } else {
            balls[0] = ColorSort.Color.GREEN;
            green++;
        }

        ColorSort.reorder(balls);
        assertTrue("One element array not working", check(balls, red, green, blue));

        red = 0;
        green = 0;
        blue = 0;
        balls = new ColorSort.Color[0];

        ColorSort.reorder(balls);
        assertTrue("Zero element array not working", check(balls, red, green, blue));
    }

    @Test(timeout = 1000)
    public void testAllRed() {
        balls = new ColorSort.Color[100000];
        int length = balls.length;

        red = length;
        green = 0;
        blue = 0;

        for (int i = 0; i < length; i++) {
            balls[i] = ColorSort.Color.RED;
        }

        ColorSort.reorder(balls);
        assertTrue("Result incorrect for all red", check(balls, red, green, blue));
    }

    @Test(timeout = 1000)
    public void testAllGreen() {
        balls = new ColorSort.Color[100000];
        int length = balls.length;

        red = 0;
        green = length;
        blue = 0;

        for (int i = 0; i < length; i++) {
            balls[i] = ColorSort.Color.GREEN;
        }

        ColorSort.reorder(balls);
        assertTrue("Result incorrect for all green", check(balls, red, green, blue));
    }

    @Test(timeout = 1000)
    public void testAllBlue() {
        balls = new ColorSort.Color[100000];
        int length = balls.length;

        red = 0;
        green = 0;
        blue = length;

        for (int i = 0; i < length; i++) {
            balls[i] = ColorSort.Color.BLUE;
        }

        ColorSort.reorder(balls);
        assertTrue("Result incorrect for all blue", check(balls, red, green, blue));
    }

    @Test(timeout = 1000)
    public void testSpeed() {
        red = 0;
        green = 0;
        blue = 0;
        balls = new ColorSort.Color[1000000];

        for (int i = 0; i < balls.length; i++) {
            double random = Math.random();

            if (random < 1. / 3.) {
                balls[i] = ColorSort.Color.RED;
                red++;
            } else if (random > 2. / 3.) {
                balls[i] = ColorSort.Color.BLUE;
                blue++;
            } else {
                balls[i] = ColorSort.Color.GREEN;
                green++;
            }
        }

        long start = System.currentTimeMillis();
        ColorSort.reorder(balls);
        long end = System.currentTimeMillis();

        long delta = end - start;

        assertTrue("Result incorrect", check(balls, red, green, blue));
        assertTrue("Too slow: " + delta, delta < threshold);

        System.out.println("Time spent: " + delta + " ms");
    }
}