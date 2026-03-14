import hash.AbstractHM;
import hash.chains.Chained;
import hash.exceptions.KeyNotFoundE;
import hash.probing.DoubleHashing;
import hash.probing.Linear;
import hash.probing.Quadratic;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpeedTest {

    @Test
    void testSpeed () throws KeyNotFoundE {
        AbstractHM<Integer, String> chaining = new Chained<>(11);
        AbstractHM<Integer, String> linear = new Linear<>(11);
        AbstractHM<Integer, String> quadratic = new Quadratic<>(11);
        AbstractHM<Integer, String> doubleHashing = new DoubleHashing<>(11);

        int seed = 17;
        int bound = 100000;
        int size = 1000000;
        /*
        Expected results are that all three probing strategies should be in the same range
        and about twice as fast as chaining.

        Chaining: 3303
        Linear: 1721
        Quadratic: 1603
        Double hashing: 1775

        */
        Random random = new Random(seed);

        for (int i = 0; i < size; i++) {
            int key = random.nextInt(bound);
            chaining.put(key, "a");
            linear.put(key, "a");
            quadratic.put(key, "a");
            doubleHashing.put(key, "a");
        }

        long chainT, linearT, quadraticT, doubleHashingT;

        random = new Random(seed);
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) chaining.get(random.nextInt(bound));
        long end = System.currentTimeMillis();
        chainT = end - start;
        System.out.println("Chaining: " + chainT);

        random = new Random(seed);
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) linear.get(random.nextInt(bound));
        end = System.currentTimeMillis();
        linearT = end - start;
        System.out.println("Linear: " + linearT);

        random = new Random(seed);
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) quadratic.get(random.nextInt(bound));
        end = System.currentTimeMillis();
        quadraticT = end - start;
        System.out.println("Quadratic: " + quadraticT);

        random = new Random(seed);
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) doubleHashing.get(random.nextInt(bound));
        end = System.currentTimeMillis();
        doubleHashingT = end - start;
        System.out.println("Double hashing: " + doubleHashingT);

        assertTrue(chainT > linearT);
        assertTrue(chainT > quadraticT);
        assertTrue(chainT > doubleHashingT);

    }
}
