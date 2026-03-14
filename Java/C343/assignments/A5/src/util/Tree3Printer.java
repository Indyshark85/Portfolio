package util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree3Printer {
    public interface PrintableNode {
        PrintableNode getLeft();
        PrintableNode getMiddle();
        PrintableNode getRight();
        String getText();
    }

    public static void print(PrintableNode root) {
        int maxLevel = maxLevel(root);
        int labelWidth = getMaxLabelWidth(root);
        if (labelWidth % 2 == 0) labelWidth++; // make it odd for centering

        printNodeInternal(Collections.singletonList(root), 1, maxLevel, labelWidth);
    }

    private static void printNodeInternal(List<PrintableNode> nodes, int level, int maxLevel, int labelWidth) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(3, Math.max(floor - 1, 0));
        int firstSpaces = (int) Math.pow(3, floor) - 1;
        int betweenSpaces = (int) Math.pow(3, floor + 1) - 1;

        printWhitespaces(firstSpaces * labelWidth / 2);

        List<PrintableNode> newNodes = new ArrayList<>();
        for (PrintableNode node : nodes) {
            if (node != null) {
                System.out.print(centered(node.getText(), labelWidth));
                newNodes.add(node.getLeft());
                newNodes.add(node.getMiddle());
                newNodes.add(node.getRight());
            } else {
                System.out.print(" ".repeat(labelWidth));
                newNodes.add(null);
                newNodes.add(null);
                newNodes.add(null);
            }

            printWhitespaces(betweenSpaces * labelWidth / 2);
        }
        System.out.println();

        // Print branches
        for (int i = 1; i <= edgeLines; i++) {
            for (PrintableNode printableNode : nodes) {
                printWhitespaces((firstSpaces - i) * labelWidth / 2);

                if (printableNode == null) {
                    printWhitespaces((edgeLines * 2 + i * 2 + 1) * labelWidth / 2);
                    continue;
                }

                System.out.print(printableNode.getLeft() != null ? "/" : " ");
                printWhitespaces((i * labelWidth) - 1);
                System.out.print(printableNode.getMiddle() != null ? "|" : " ");
                printWhitespaces((i * labelWidth) - 1);
                System.out.print(printableNode.getRight() != null ? "\\" : " ");

                printWhitespaces(((edgeLines - i) * 2 + 1) * labelWidth / 2);
            }
            System.out.println();
        }

        printNodeInternal(newNodes, level + 1, maxLevel, labelWidth);
    }

    private static String centered(String text, int width) {
        if (text.length() >= width) return text;
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }

    private static void printWhitespaces(int count) {
        if (count <= 0) return;
        System.out.print(" ".repeat(count));
    }

    private static int maxLevel(PrintableNode node) {
        if (node == null) return 0;
        return 1 + Math.max(
                Math.max(maxLevel(node.getLeft()), maxLevel(node.getMiddle())),
                maxLevel(node.getRight())
        );
    }

    private static int getMaxLabelWidth(PrintableNode root) {
        return getMaxLabelWidthRecursive(root, 0);
    }

    private static int getMaxLabelWidthRecursive(PrintableNode node, int currentMax) {
        if (node == null) return currentMax;
        int length = node.getText().length();
        currentMax = Math.max(currentMax, length);
        currentMax = getMaxLabelWidthRecursive(node.getLeft(), currentMax);
        currentMax = getMaxLabelWidthRecursive(node.getMiddle(), currentMax);
        currentMax = getMaxLabelWidthRecursive(node.getRight(), currentMax);
        return currentMax;
    }

    private static boolean isAllElementsNull(List<PrintableNode> list) {
        for (PrintableNode node : list)
            if (node != null) return false;
        return true;
    }
}