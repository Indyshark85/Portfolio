import org.junit.jupiter.api.Test;

public class TODOTest {
    @Test
    public void testColor() {
        // write a simple test to make sure you understand the behavior of colors.
        // test redden, redder, etc in the Color class
    }

    @Test
    public void testEmptyRB() {
        // write a simple test to make sure you understand the behavior of EmptyRB
        // create an EmptyRB that is black and another one that is doubleBlack
        // test their behavior under redden, blacken, and redder
    }

    @Test
    public void constructRB () {
        // use the constructors to create various trees and make sure they are well-formed
        // try to construct trees with different structures and varying black heights
    }

    @Test
    public void testInsert () {
        // test the insert method by inserting a few elements and checking the result
        // make sure the tree is well-formed after each insert
        // try to construct cases that exhibit the four kinds of red-red violations
        // and try to get multiple violations in a single insert
    }

    @Test
    public void testRemove () {
        // test the remove method by removing a few elements and checking the result
        // make sure the tree is well-formed after each remove
        // try to construct cases that remove leaves, nodes with one child,
        // and nodes with two children with various color combinations.
        // Your test cases should cover all the cases in the remove method (and
        // its helpers)
    }


}
