package mybudget.command;

/**
 * COMMAND PATTERN - Base interface
 * Every financial action implements execute() and undo().
 */
public interface Command {
    void execute();
    void undo();
    String getDescription();
}
