package util;

public enum Progress {
    CONTINUE, STOP;
    public boolean stop() { return this.equals(STOP); }
}
