import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Path implements Comparable<Path> {
    private final LinkedList<Move> moves;
    private final double reward;

    public Path(double reward) {
        this.moves = new LinkedList<>();
        this.reward = RewardConstants.STEP.value() + reward * AutonomousVehicle.DISTANCE_PENALTY;
    }

    public void add(Move move) {
        moves.addFirst(move);
    }

    public int compareTo(@NotNull Path o) {
        return Double.compare(reward, o.reward);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Reward: ");
        sb.append(String.format("%.2f%n", reward));
        sb.append("Moves: ");
        for (Move move : moves) sb.append(move).append(" ");
        sb.append("\n");
        return sb.toString();
    }
}
