import java.util.*;

public class DoubleStack {
    private final List<Double> list = new ArrayList<>();

    public static void main(String[] args) {
        interpret(" \t   \t    "); // Empty expression
        interpret("6 7 xyz + 9."); // Illegal symbol in expression
        interpret("1 5. 2. - 7."); // Too many numbers in expression
        interpret("3. 4. + - 5."); // Not enough numbers in expression

        interpret("DUP");     // Not enough numbers to perform operation 'DUP'
        interpret("8 SWAP");  // Not enough numbers to perform operation 'SWAP'
        interpret("5 2 ROT"); // Not enough numbers to perform operation 'ROT'
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DoubleStack doubleStackClone = new DoubleStack();

        doubleStackClone.list.addAll(list);

        return doubleStackClone;
    }

    public boolean stEmpty() {
        return list.isEmpty();
    }

    public void push(double a) {
        list.add(a);
    }

    public double pop() {
        if (!this.stEmpty()) {
            return list.remove(list.size() - 1);
        }

        throw new RuntimeException("Not enough numbers");
    }

    public void swap() {
        if (list.size() < 2) {
            throw new RuntimeException("Not enough numbers to perform operation 'SWAP': ");
        }

        double first = this.pop();
        double second = this.pop();

        this.push(first);
        this.push(second);
    }

    public void dup() {
        if (this.stEmpty()) {
            throw new RuntimeException("Not enough numbers to perform operation 'DUP': ");
        }

        this.push(this.tos());
    }

    public void rot() {
        if (list.size() < 3) {
            throw new RuntimeException("Not enough numbers to perform operation 'ROT': ");
        }

        double first = this.pop();
        double second = this.pop();
        double third = this.pop();

        this.push(second);
        this.push(first);
        this.push(third);
    }

    public boolean specialOp(String s) {
        switch (s) {
            case "SWAP":
                this.swap();
                return true;
            case "DUP":
                this.dup();
                return true;
            case "ROT":
                this.rot();
                return true;
            default:
                return false;
        }
    }

    public void op(String s) {
        if (this.specialOp(s)) {
            return;
        }

        if (list.size() < 2) {
            throw new RuntimeException(String.format("Not enough numbers in expression to perform operation '%s': ", s));
        }

        double first = this.pop();
        double second = this.pop();

        switch (s) {
            case "-":
                this.push(second - first);
                break;
            case "+":
                this.push(second + first);
                break;
            case "*":
                this.push(second * first);
                break;
            case "/":
                this.push(second / first);
                break;
            default:
                throw new RuntimeException(String.format("Illegal symbol '%s' in expression: ", s));
        }
    }

    public double tos() {
        if (!this.stEmpty()) {
            return list.get(list.size() - 1);
        }

        throw new RuntimeException("Not enough numbers");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleStack)) {
            return false;
        }

        DoubleStack other = (DoubleStack) o;
        return Objects.equals(list, other.list);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (Double number : list) {
            string.append(number);
        }

        return string.toString();
    }

    public static double interpret(String input) {
        if (input == null) {
            throw new RuntimeException("Empty expression");
        }

        String formattedInput = input.trim();

        if (formattedInput.isEmpty()) {
            throw new RuntimeException("Empty expression");
        }

        DoubleStack stack = new DoubleStack();

        for (String symbol : formattedInput.split(" ")) {
            if (symbol.isEmpty()) {
                continue;
            }

            try {
                Double number = stack.getNumber(symbol);

                if (number != null) {
                    stack.push(number);
                } else {
                    stack.op(symbol);
                }
            } catch (RuntimeException exception) {
                throw new RuntimeException(exception.getMessage() + input);
            }
        }

        if (stack.list.size() > 1) {
            throw new RuntimeException("Too many numbers in expression: " + input);
        }

        return stack.tos();
    }

    private Double getNumber(String number) {
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return null;
        }
    }
}