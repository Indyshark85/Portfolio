public class Main {

    public static String upper(boolean b) {
        return b ? "TRUE" : "FALSE";
    }

    public static void main( String [ ] args ) {

        // Create an empty RedBlackTree, and insert numbers from 1 to 50 into that tree.
        RedBlackTree tree = new RedBlackTree();
        for( int i = 1; i <= 40; i ++ ) {
            tree.insert(i);
        }

        // Check properties about the result tree to ensure all invariants are maintained.
        System.out.println("Tree is red black tree after inserting numbers from 1 to 40: " + upper(tree.isRedBlackTree()));
        System.out.println("Min element is 1: " + upper((tree.findMin() == 1)));
        System.out.println("Max element is 40: " + upper((tree.findMax() == 40)));
        System.out.println("Tree height is 6: " + upper((tree.height() == 6)));

        boolean allElementsFound = true;
        for( int i = 1; i <= 40; i ++ ) {
            allElementsFound = allElementsFound && tree.contains(i);
        }
        System.out.println("All elements are found in the tree: " + upper(allElementsFound));
        System.out.println();

        // Print all elements of the tree in ascending order
        // You should see all numbers between 1 and 40
        System.out.println("Print the tree: ");
        tree.printTree();
        System.out.println();


        boolean correctDeletion = true;
        for (int i = 2; i <= 40; i += 2) {
            tree.delete(i);
            correctDeletion = correctDeletion && tree.isRedBlackTree() && !tree.contains(i);
        }

        System.out.println("Tree is red black tree after deleting even numbers: " + upper(tree.isRedBlackTree()));

    }
}
