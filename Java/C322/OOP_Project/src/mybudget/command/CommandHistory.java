package mybudget.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * COMMAND PATTERN
 * Maintains the undo/redo history for all executed commands.
 */
public class CommandHistory {
    private Deque<Command> undoStack = new ArrayDeque<>();
    private Deque<Command> redoStack = new ArrayDeque<>();

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // new action clears redo history
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("[History] Nothing to undo.");
            return;
        }
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("[History] Nothing to redo.");
            return;
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }

    public void printHistory() {
        System.out.println("\n--- Command History ---");
        if (undoStack.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            undoStack.forEach(c -> System.out.println("  • " + c.getDescription()));
        }
        System.out.println("-----------------------");
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }
}
