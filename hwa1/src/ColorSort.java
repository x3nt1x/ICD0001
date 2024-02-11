public class ColorSort {
    public enum Color { RED, GREEN, BLUE }

    public static void reorder(Color[] balls) {
        int red = 0, green = 0;

        for (Color ball : balls) {
            if (ball == Color.RED) {
                red++;
            }

            if (ball == Color.GREEN) {
                green++;
            }
        }

        for (int i = 0; i < balls.length; i++) {
            if (i < red) {
                balls[i] = Color.RED;
                continue;
            }

            if (i < red + green) {
                balls[i] = Color.GREEN;
                continue;
            }

            balls[i] = Color.BLUE;
        }
    }
}