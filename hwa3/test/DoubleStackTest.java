import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleStackTest {
    static final double delta = 0.000000001;

    @Test(timeout = 1000)
    public void testNewStack() {
        DoubleStack stack = new DoubleStack();
        assertTrue("New stack must be empty", stack.stEmpty());

        stack.push(1.);
        stack.pop();
        assertTrue("Stack must be empty after one push and one pop", stack.stEmpty());
    }

    @Test(timeout = 1000)
    public void testLIFO() {
        DoubleStack stack = new DoubleStack();

        stack.push(6.);
        stack.push(-3.);

        double d1 = stack.pop();
        double d2 = stack.pop();

        assertTrue("After two pushes and two pops stack must be empty", stack.stEmpty());
        assertTrue("LIFO order must hold: 6. -3. returns -3. first", (d1 == -3.) && (d2 == 6.));
    }

    @Test(timeout = 1000)
    public void testOp() {
        DoubleStack stack = new DoubleStack();
        double data;

        stack.push(5.);
        stack.push(3.);
        stack.op("+");
        data = stack.pop();
        assertEquals("5. + 3. must be 8.", 8., data, delta);
        assertTrue("push push op pop must not grow the stack", stack.stEmpty());

        stack.push(5.);
        stack.push(3.);
        stack.op("-");
        data = stack.pop();
        assertEquals("5. - 3. must be 2.", 2., data, delta);
        assertTrue("push push op pop must not grow the stack", stack.stEmpty());

        stack.push(5.);
        stack.push(3.);
        stack.op("*");
        data = stack.pop();
        assertEquals("5. * 3. must be 15.", 15., data, delta);
        assertTrue("push push op pop must not grow the stack", stack.stEmpty());

        stack.push(51.);
        stack.push(3.);
        stack.op("/");
        data = stack.pop();
        assertEquals("51. / 3. must be 17.", 17., data, delta);
        assertTrue("push push op pop must not grow the stack", stack.stEmpty());
    }

    @Test(timeout = 1000)
    public void testTos() {
        DoubleStack stack = new DoubleStack();

        stack.push(2.);
        stack.push(5.);

        double d1 = stack.tos();
        double d2 = stack.pop();
        assertEquals("5. must be on top", 5., d1, delta);
        assertEquals("tos must not change the top", 5., d2, delta);

        double d3 = stack.pop();
        assertEquals("tos must not change the stack", 2., d3, delta);
        assertTrue("tos must not pop", stack.stEmpty());
    }

    @Test(timeout = 1000)
    public void testEquals() {
        DoubleStack stack1 = new DoubleStack();
        DoubleStack stack2 = new DoubleStack();
        assertEquals("Two empty stacks must be equal", stack1, stack2);

        stack1.push(1.);
        stack2.push(1.);
        assertEquals("1. in both stacks - stacks must be equal", stack1, stack2);

        stack1.push(0.);
        assertNotEquals("1. 0. and just 1. must not be equal", stack1, stack2);

        stack2.push(3.);
        assertNotEquals("1. 0. and 1. 3. must not be equal", stack1, stack2);

        stack1.pop();
        stack2.pop();
        assertEquals("1. in stacks with different history, stacks must be equal", stack1, stack2);

        stack1.pop();
        assertNotEquals("First empty, second contains 1., must not be equal", stack1, stack2);
    }

    @Test(expected = RuntimeException.class)
    public void testPopEmpty() {
        DoubleStack stack = new DoubleStack();

        stack.pop();
    }

    @Test(expected = RuntimeException.class)
    public void testOpUnderflow() {
        DoubleStack stack = new DoubleStack();

        stack.push(4.);
        stack.op("+");
    }

    @Test(expected = RuntimeException.class)
    public void testOpSign() {
        DoubleStack stack = new DoubleStack();

        stack.push(4.);
        stack.push(5.);
        stack.op("h");
    }

    @Test(timeout = 1000)
    public void testClone() {
        DoubleStack stack1 = new DoubleStack();
        DoubleStack stack2 = null;

        stack1.push(5.);
        stack1.push(4.);

        try {
            stack2 = (DoubleStack) stack1.clone();
        } catch (CloneNotSupportedException ignored) {
        }

        assertNotSame("Clone must differ from original", stack2, stack1);
        assertEquals("Clone must be equal to original", stack2, stack1);

        stack1.pop();
        stack1.push(6.);
        assertNotEquals("Clone must be independent", stack1, stack2);
    }

    @Test(timeout = 1000)
    public void testToString() {
        DoubleStack stack = new DoubleStack();
        assertNotNull("Empty stack must be ok", stack.toString());

        stack.push(-8.5);
        stack.push(7.14);
        String s1 = stack.toString().substring(0, 8);

        stack.push(2.73);
        String s2 = stack.toString().substring(0, 8);

        assertEquals("Top must be the last element; toString from bottom must start with -8.5 7.14", s1, s2);
    }

    @Test(expected = RuntimeException.class)
    public void testTosUnderflow() {
        DoubleStack stack = new DoubleStack();

        stack.tos();
    }

    @Test(timeout = 1000)
    public void testInterpret() {
        String input = "1.";
        assertEquals("Expression: " + input, 1., DoubleStack.interpret(input), delta);

        input = "2. 5. -";
        assertEquals("Expression: " + input, -3., DoubleStack.interpret(input), delta);

        input = "35. 10. -3. + /";
        assertEquals("Expression: " + input, 5., DoubleStack.interpret(input), delta);

        input = "2 5 SWAP -";
        assertEquals("Expression: " + input, 3., DoubleStack.interpret(input), delta);

        input = "3 DUP *";
        assertEquals("Expression: " + input, 9., DoubleStack.interpret(input), delta);

        input = "2 5 9 ROT - -";
        assertEquals("Expression: " + input, -2., DoubleStack.interpret(input), delta);

        input = "-3 -5 -7 ROT - SWAP DUP * +";
        assertEquals("Expression: " + input, 21., DoubleStack.interpret(input), delta);
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretStackbalance() {
        DoubleStack.interpret("35. 10. -3. + / 2.");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretIllegalArg1() {
        DoubleStack.interpret("35. 10. -3. + x 2.");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretIllegalArg2() {
        DoubleStack.interpret("35. y 10. -3. + - +");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretUnderflow() {
        DoubleStack.interpret("35. 10. + -");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretNull() {
        DoubleStack.interpret(null);
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretEmpty() {
        DoubleStack.interpret("");
    }

    @Test(timeout = 1000)
    public void testInterpretLong() {
        String input = "1. -10. 4. 8. 3. - + * +";
        assertEquals("Expression: " + input, -89., DoubleStack.interpret(input), delta);

        input = "156. 154. 152. - 3. + -";
        assertEquals("Expression: " + input, 151., DoubleStack.interpret(input), delta);
    }

    @Test(timeout = 1000)
    public void testInterpretTokenizer() {
        String input = "1.  2.    +";
        assertEquals("Expression: " + input, 3., DoubleStack.interpret(input), delta);

        input = "   \t \t356.  \t \t";
        assertEquals("Expression: " + input, 356., DoubleStack.interpret(input), delta);

        input = "\t2. \t5. +   \t";
        assertEquals("Expression: " + input, 7., DoubleStack.interpret(input), delta);
    }

    @Test(expected = RuntimeException.class)
    public void testEmpty1() {
        DoubleStack.interpret("\t\t");
    }

    @Test(expected = RuntimeException.class)
    public void testEmpty2() {
        DoubleStack.interpret("\t \t ");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol() {
        DoubleStack.interpret("2 xx");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol2() {
        DoubleStack.interpret("x");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol3() {
        DoubleStack.interpret("2 1 + xx");
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyNumbers() {
        DoubleStack.interpret("2 3");
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyNumbers2() {
        DoubleStack.interpret("2 3 + 5");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers() {
        DoubleStack.interpret("2 -");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers2() {
        DoubleStack.interpret("2 5 + -");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers3() {
        DoubleStack.interpret("+");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretDupNotEnoughElements() {
        DoubleStack.interpret("DUP");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretSwapNotEnoughElements() {
        DoubleStack.interpret("8 SWAP");
    }

    @Test(expected = RuntimeException.class)
    public void testInterpretRotNotEnoughElements() {
        DoubleStack.interpret("5 2 ROT +");
    }
}