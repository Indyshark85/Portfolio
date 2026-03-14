import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    public List<String> preOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.add(node.getText());
            result.addAll(preOrder(node.getLeft()));
            result.addAll(preOrder(node.getRight()));
        }
        return result;
    }

    public List<String> inOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.addAll(inOrder(node.getLeft()));
            result.add(node.getText());
            result.addAll(inOrder(node.getRight()));
        }
        return result;
    }

    public List<String> postOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.addAll(postOrder(node.getLeft()));
            result.addAll(postOrder(node.getRight()));
            result.add(node.getText());
        }
        return result;
    }


    @Test
    void testBasic() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        TreePrinter.print(tree.getRoot());
        assertTrue(tree.isRedBlackTree());
        assertEquals(1, tree.findMin());
        assertEquals(4, tree.findMax());
        assertEquals(3, tree.height());
    }

}