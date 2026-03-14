
public class RedBlackNode implements TreePrinter.PrintableNode {

    public Integer element;
    public RedBlackNode left;
    public RedBlackNode right;
    public RB color;


    private RedBlackNode() {}

    // Create a new EMPTY node
    public static RedBlackNode emptyNode() {
        RedBlackNode empty = new RedBlackNode();
        empty.element = null;
        empty.left = null;
        empty.right = null;
        empty.color = RB.BLACK;
        return empty;
    }

    public RedBlackNode(RB color, RedBlackNode left, int element, RedBlackNode right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    public void tint(RB color) {
        this.color = color;
    }

    // Check whether the current node is EMPTY
    public boolean isEmpty() {
        return this.element == null && this.left == null && this.right == null;
    }

    // Check whether the current node is DOUBLE_BLACK after deletion.
    // If so, the result tree should be adjusted to satisfy red black tree properties
    public boolean isDoubleBlack() {
        return this.color == RB.DOUBLE_BLACK;
    }

    @Override
    public RedBlackNode getLeft() {
        return left.isEmpty() ? null : left;
    }

    @Override
    public RedBlackNode getRight() {
        return right.isEmpty() ? null : right;
    }

    @Override
    public String getText() {
        String clr = null;
        switch (this.color) {
            case RED -> clr = "R";
            case BLACK -> clr = "B";
            case DOUBLE_BLACK -> clr = "BB";
        }
        return clr + "[" + element.toString() + "]";
    }
}


