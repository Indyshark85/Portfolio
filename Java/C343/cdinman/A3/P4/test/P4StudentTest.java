import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P4StudentTest {
    private static final double EPS = 1e-9;

    @Test
    public void testEmptyArray() {
        CirclePacker packer = new OrderedCirclePacker();
        assertEquals(0.0, packer.packWidth(new double[]{}), EPS, "Empty array should yield width 0");
    }

    @Test
    public void testSingleCircle() {
        CirclePacker packer = new OrderedCirclePacker();
        double width = packer.packWidth(new double[]{2});
        assertEquals(4.0, width, EPS, "Single circle of radius 2 → width = 4");
    }

    @Test
    public void testTwoEqualCircles() {
        CirclePacker packer = new OrderedCirclePacker();
        double width = packer.packWidth(new double[]{2, 2});
        // sep(2,2) = 2 * sqrt(4) = 4 → centers at 2, 6 → rightmost edge = 6+2=8
        assertEquals(8.0, width, EPS, "Two equal circles of radius 2 → width = 8");
    }

    @Test
    public void testTwoDifferentCircles() {
        CirclePacker packer = new OrderedCirclePacker();
        double width = packer.packWidth(new double[]{2, 1});
        // sep(2,1) = 2*sqrt(2) ≈ 2.828 → centers at 2 and (2+2.828)=4.828 → rightmost = 4.828+1=5.828
        assertEquals(2 + 2 * Math.sqrt(2) + 1, width, EPS, "Radii 2,1 → width = 3 + 2√2");
    }

    @Test
    public void testExampleFromPrompt() {
        CirclePacker packer = new OrderedCirclePacker();
        double width = packer.packWidth(new double[]{2, 1, 2});
        double expected = 4 + 4 * Math.sqrt(2); // given in the problem
        assertEquals(expected, width, EPS, "Example {2,1,2} → width = 4 + 4√2");
    }

    @Test
    public void testIncreasingRadii() {
        CirclePacker packer = new OrderedCirclePacker();
        double[] radii = {1, 2, 3};
        double width = packer.packWidth(radii);
        assertTrue(width > 0, "Width should be positive for any valid radii");
        System.out.println("Width for {1,2,3}: " + width);
    }
}
