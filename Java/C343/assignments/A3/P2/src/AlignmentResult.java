public class AlignmentResult {
    public final String alignedX;
    public final String alignedY;
    public final int score;

    public AlignmentResult(String alignedX, String alignedY, int score) {
        this.alignedX = alignedX;
        this.alignedY = alignedY;
        this.score = score;
    }

    @Override public String toString() {
        return alignedX + System.lineSeparator() + alignedY + System.lineSeparator() + "score=" + score;
    }
}
