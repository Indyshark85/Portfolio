import dynamicArray.DequeueA;
import org.junit.jupiter.api.Test;
import pointers.DequeueP;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class HashTest {

    static class A {
        int x = 3;
        int y = 4;

        void setY(int y) {
            this.y = y;
        }

        public boolean equals(Object other) {
            if (!(other instanceof A otherA)) return false;
            return x == otherA.x;
        }
        public int hashCode() { return x; }
    }

    @Test
    void basic () {
        String s1 = "hello";
        String s2 = "h" + "e" + "l" + "l" + "o";
        assertEquals(s1, s2);
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());

        s1 = "Siblings";
        s2 = "Teheran";
        assertNotEquals(s1, s2);
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());

        A a1 = new A();
        A a2 = new A();
        assertEquals(a1, a2);
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());

        a2.setY(5);
        assertEquals(a1, a2);
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());

        // Generally hashCode() should not be called on arrays directly
        Integer[] arr1 = {1, 2, 3, 4};
        Integer[] arr2 = {1, 2, 3, 4};
        assertNotEquals(arr1, arr2);
        System.out.println(Arrays.hashCode(arr1));
        System.out.println(Arrays.hashCode(arr2));
        assertEquals(Arrays.hashCode(arr1), Arrays.hashCode(arr2));
        System.out.println(arr1.hashCode());
        System.out.println(arr2.hashCode());
        assertNotEquals(arr1.hashCode(), arr2.hashCode());

        Integer[] arr3 = {1, 2, 3, 4};
        Integer[] arr4 = {null, 33, 4, -27};
        assertNotEquals(arr1, arr2);
        System.out.println(Arrays.hashCode(arr3));
        System.out.println(Arrays.hashCode(arr4));
        assertEquals(Arrays.hashCode(arr3), Arrays.hashCode(arr4));
        System.out.println(arr3.hashCode());
        System.out.println(arr4.hashCode());
        assertNotEquals(arr3.hashCode(), arr4.hashCode());

        DequeueP<Integer> dq = new DequeueP<>();
        dq.enqueueFront(1);
        dq.enqueueFront(2);
        dq.enqueueFront(3);
        System.out.println(dq.hashCode());
        dq.enqueueFront(4);
        System.out.println(dq.hashCode());

        DequeueA<Integer> dqa = new DequeueA<>(3);
        dqa.enqueueFront(1);
        dqa.enqueueFront(2);
        dqa.enqueueFront(3);
        DequeueA<Integer> dqb = new DequeueA<>(3);
        dqb.enqueueFront(1);
        dqb.enqueueFront(2);
        dqb.enqueueFront(3);
        dqb.doubleCapacity();
        System.out.println(dqa.hashCode());
        System.out.println(dqb.hashCode());
    }

}