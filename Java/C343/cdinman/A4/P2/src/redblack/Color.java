package redblack;

public enum Color {
    RED,
    BLACK,
    DOUBLE_BLACK,
    NEGATIVE_BLACK;

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isRed() { return this == RED; }
    public boolean isBlack() { return this == BLACK; }
    public boolean isDoubleBlack() { return this == DOUBLE_BLACK; }
    public boolean isNegativeBlack() { return this == NEGATIVE_BLACK; }
    public boolean isBlackOrDoubleBlack() { return this == BLACK || this == DOUBLE_BLACK; }

    // Color transformations: more black or more red ---------------------------------------

    public Color blacker() {
        return switch (this) {
            case NEGATIVE_BLACK -> RED;
            case RED -> BLACK;
            case BLACK -> DOUBLE_BLACK;
            case DOUBLE_BLACK -> throw new RuntimeException("Internal error in Color.blacker");
        };
    }

    public Color redder() {
        return switch (this) {
            case NEGATIVE_BLACK -> throw new RuntimeException("Internal error in Color.redder");
            case RED -> NEGATIVE_BLACK;
            case BLACK -> RED;
            case DOUBLE_BLACK -> BLACK;
        };
    }

    // For debugging purposes ----------------------------------------------------------------

    public String toString() {
        return switch (this) {
            case RED -> "R";
            case BLACK -> "B";
            case DOUBLE_BLACK -> "DB";
            case NEGATIVE_BLACK -> "NB";
        };
    }
}
