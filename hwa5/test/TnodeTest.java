import static org.junit.Assert.*;

import org.junit.Test;

public class TnodeTest {
    @Test(timeout = 1000)
    public void testBuildFromRPN() {
        String input = "1 2 +";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "+(1,2)", result);

        input = "2 1 - 4 * 6 3 / +";
        tree = Tnode.buildFromRPN(input);
        result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "+(*(-(2,1),4),/(6,3))", result);
    }

    @Test(timeout = 1000)
    public void testBuild() {
        String input = "512 1 - 4 * -61 3 / +";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "+(*(-(512,1),4),/(-61,3))", result);

        input = "5";
        tree = Tnode.buildFromRPN(input);
        result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "5", result);
    }

    @Test(timeout = 1000)
    public void testSWAP() {
        String input = "2 5 SWAP -";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "-(5,2)", result);
    }

    @Test(timeout = 1000)
    public void testDUP() {
        String input = "3 DUP *";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "*(3,3)", result);
    }

    @Test(timeout = 1000)
    public void testROT() {
        String input = "2 5 9 ROT - +";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "+(5,-(9,2))", result);
    }

    @Test(timeout = 1000)
    public void testROTSWAP() {
        String input = "2 5 9 ROT + SWAP -";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "-(+(9,2),5)", result);
    }

    @Test(timeout = 1000)
    public void testDUPROTDUP() {
        String input = "2 5 DUP ROT - + DUP *";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "*(+(5,-(5,2)),+(5,-(5,2)))", result);
    }

    @Test(timeout = 1000)
    public void testROTSWAPDUP() {
        String input = "-3 -5 -7 ROT - SWAP DUP * +";
        Tnode tree = Tnode.buildFromRPN(input);
        String result = tree.toString().replaceAll("\\s+", "");
        assertEquals("Tree: " + input, "+(-(-7,-3),*(-5,-5))", result);
    }

    @Test(expected = RuntimeException.class)
    public void testEmpty1() {
        Tnode.buildFromRPN("\t\t");
    }

    @Test(expected = RuntimeException.class)
    public void testEmpty2() {
        Tnode.buildFromRPN("\t \t ");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol() {
        Tnode.buildFromRPN("2 xx");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol2() {
        Tnode.buildFromRPN("x");
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalSymbol3() {
        Tnode.buildFromRPN("2 1 + xx");
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyNumbers() {
        Tnode.buildFromRPN("2 3");
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyNumbers2() {
        Tnode.buildFromRPN("2 3 + 5");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers() {
        Tnode.buildFromRPN("2 -");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers2() {
        Tnode.buildFromRPN("2 5 + -");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers3() {
        Tnode.buildFromRPN("+");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers4() {
        Tnode.buildFromRPN("DUP");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers5() {
        Tnode.buildFromRPN("8 SWAP");
    }

    @Test(expected = RuntimeException.class)
    public void testTooFewNumbers6() {
        Tnode.buildFromRPN("5 2 ROT");
    }
}