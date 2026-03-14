/**
 * Inner class representing a move in the grid.
 */
public enum Move {
    RIGHT(0, 1),
    LEFT(0, -1),
    DOWN(1, 0),
    UP(-1, 0);

    private final int dx;
    private final int dy;

    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static Move[] possibleMoves() {
        return new Move[]{RIGHT, LEFT, DOWN, UP};
    }

    public String toString() {
        return switch (this) {
            case RIGHT -> "\u2192";
            case LEFT -> "\u2190";
            case DOWN -> "\u2193";
            case UP -> "\u2191";
        };
    }
}
