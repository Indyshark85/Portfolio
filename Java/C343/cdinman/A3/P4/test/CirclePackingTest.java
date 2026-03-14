import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CirclePackingTest {

    @Test
    void example212() {
        CirclePacker packer = new OrderedCirclePacker();
        double w = packer.packWidth(new double[]{2,1,2});
        assertEquals(9.656854, w, 1e-6);
    }

    @Test
    void singleCircle() {
        CirclePacker packer = new OrderedCirclePacker();
        double w = packer.packWidth(new double[]{3.0});
        assertEquals(6.0, w, 1e-9);
    }

    @Test
    void increasingRadii() {
        CirclePacker packer = new OrderedCirclePacker();
        double w = packer.packWidth(new double[]{1,2,3});
        assertTrue(w > 0);
        // sanity: must be at least last x + r
        assertTrue(w >= 3.0);
    }
}
