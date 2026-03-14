public enum RewardConstants {
    GOAL, NO_REWARD, STEP, TWO_LANES, CONSTRUCTION, SCHOOL_CROSSING, ONE_WAY, CRASH;

    double value() {
        return switch (this) {
            case GOAL -> 100.0;
            case NO_REWARD -> 0.0;
            case STEP -> 1.0;
            case TWO_LANES -> -5.0;
            case CONSTRUCTION -> -10.0;
            case SCHOOL_CROSSING -> -20.0;
            case ONE_WAY -> -50;
            case CRASH -> Double.NEGATIVE_INFINITY;
        };
    }

}
