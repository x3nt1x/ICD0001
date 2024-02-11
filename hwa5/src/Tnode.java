import java.util.*;

/**
 * Tree with two pointers.
 */
public class Tnode {
    private final String name;
    private Tnode firstChild;
    private Tnode nextSibling;

    public Tnode(String name) {
        this.name = name;
        this.firstChild = null;
        this.nextSibling = null;
    }

    public static void main(String[] args) {
        Tnode.buildFromRPN(" \t   \t    "); // Empty expression
        Tnode.buildFromRPN("6 7 xyz + 9."); // Illegal symbol in expression
        Tnode.buildFromRPN("1 5. 2. - 7."); // Too many numbers in expression
        Tnode.buildFromRPN("3. 4. + - 5."); // Not enough numbers in expression

        Tnode.buildFromRPN("DUP");     // Not enough numbers to perform operation 'DUP'
        Tnode.buildFromRPN("8 SWAP");  // Not enough numbers to perform operation 'SWAP'
        Tnode.buildFromRPN("5 2 ROT"); // Not enough numbers to perform operation 'ROT'
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        buildToString(result);
        return result.toString();
    }

    private void buildToString(StringBuilder result) {
        result.append(name);

        if (firstChild != null) {
            result.append("(");
            firstChild.buildToString(result);

            Tnode pointer = firstChild.nextSibling;

            while (pointer != null) {
                result.append(",");
                pointer.buildToString(result);

                pointer = pointer.nextSibling;
            }

            result.append(")");
        }
    }

    public static Tnode buildFromRPN(String rpn) {
        if (rpn == null) {
            throw new RuntimeException("Empty expression");
        }

        String formattedRpn = rpn.trim();

        if (formattedRpn.isEmpty()) {
            throw new RuntimeException("Empty expression");
        }

        Stack<Tnode> nodes = new Stack<>();

        for (String symbol : formattedRpn.split(" ")) {
            if (symbol.isEmpty()) {
                continue;
            }

            if (!isValidSymbol(symbol)) {
                throw new RuntimeException(String.format("Illegal symbol '%s' in expression:  %s", symbol, rpn));
            }

            if (specialOp(symbol, nodes, rpn)) {
                continue;
            }

            Tnode node = new Tnode(symbol);

            if (isOperator(symbol)) {
                if (nodes.size() < 2) {
                    throw new RuntimeException("Not enough numbers in expression: " + rpn);
                }

                Tnode rightChild = nodes.pop();
                Tnode leftChild = nodes.pop();

                node.firstChild = leftChild;
                leftChild.nextSibling = rightChild;
            }

            nodes.push(node);
        }

        if (nodes.size() > 1) {
            throw new RuntimeException("Too many numbers in expression: " + rpn);
        }

        return nodes.pop();
    }

    public static boolean specialOp(String symbol, Stack<Tnode> nodes, String rpn) {
        try {
            switch (symbol) {
                case "SWAP":
                    swap(nodes);
                    return true;
                case "DUP":
                    dup(nodes);
                    return true;
                case "ROT":
                    rot(nodes);
                    return true;
                default:
                    return false;
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage() + rpn);
        }
    }

    public static void swap(Stack<Tnode> nodes) {
        if (nodes.size() < 2) {
            throw new RuntimeException("Not enough numbers to perform operation 'SWAP': ");
        }

        Tnode first = nodes.pop();
        Tnode second = nodes.pop();

        nodes.push(first);
        nodes.push(second);
    }

    public static Tnode dupTree(Tnode tnode) {
        if (tnode == null) {
            return null;
        }

        Tnode duplicate = new Tnode(tnode.name);
        duplicate.firstChild = dupTree(tnode.firstChild);
        duplicate.nextSibling = dupTree(tnode.nextSibling);

        return duplicate;
    }

    public static void dup(Stack<Tnode> nodes) {
        if (nodes.empty()) {
            throw new RuntimeException("Not enough numbers to perform operation 'DUP': ");
        }

        Tnode peek = nodes.peek();

        nodes.push(dupTree(peek));
    }

    public static void rot(Stack<Tnode> nodes) {
        if (nodes.size() < 3) {
            throw new RuntimeException("Not enough numbers to perform operation 'ROT': ");
        }

        Tnode first = nodes.pop();
        Tnode second = nodes.pop();
        Tnode third = nodes.pop();

        nodes.push(second);
        nodes.push(first);
        nodes.push(third);
    }

    private static boolean isValidSymbol(String symbol) {
        return isOperator(symbol) || isNumber(symbol);
    }

    public static boolean isOperator(String symbol) {
        return symbol.equals("+")
            || symbol.equals("-")
            || symbol.equals("*")
            || symbol.equals("/")
            || symbol.equals("DUP")
            || symbol.equals("ROT")
            || symbol.equals("SWAP");
    }

    private static boolean isNumber(String symbol) {
        try {
            Double.parseDouble(symbol);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}