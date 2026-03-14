import org.junit.jupiter.api.Test;

public class TODOTest {

    @Test
    void testMod () {
        int a = 10;
        int b = 12;
        System.out.println((10 + 5) % b);
        System.out.println((3 - 5) % b);
        System.out.println(Math.floorMod(10 + 5, b));
        System.out.println(Math.floorMod(3 - 5, b));
    }

    @Test
    void test1() {
        // write a test for dynamic arrays without resizing
        // make sure that enqueue / dequeue works as expected
        // from both directions
    }

    @Test
    void test2() {
        // write a test for dynamic arrays with resizing
    }

    @Test
    void test3() {
        // write a test for linked list that checks that insertion and deletion from
        // both ends work as expected

    }

    @Test
    void test4() {
        // write a test case that compares the performance of dynamic arrays and linked lists
        // for a large number of insertions and deletions

    }

}
