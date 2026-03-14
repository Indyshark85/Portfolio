public abstract class AbstractAlignment implements AlignmentStrategy {
    protected final int matchScore;
    protected final int mismatchScore;
    protected final int gapPenalty;

    protected AbstractAlignment(int matchScore, int mismatchScore, int gapPenalty) {
        this.matchScore = matchScore;
        this.mismatchScore = mismatchScore;
        this.gapPenalty = gapPenalty;
    }

    protected int score(char a, char b) {
        return a == b ? matchScore : mismatchScore;
    }
}
