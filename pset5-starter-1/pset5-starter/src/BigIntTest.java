import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigIntTest {

    @Test
    public void testConstructorAndToString() {
        assertAll(
                () -> assertEquals("42", new BigInt("42").toString()),
                () -> assertEquals("420", new BigInt("0420").toString()),
                () -> assertEquals("-42", new BigInt("-42").toString()),
                () -> assertEquals("420000", new BigInt("0000420000").toString()),
                () -> assertEquals("42", new BigInt("+42").toString())
        );
    }

    @Test
    public void testEquals() {
        assertAll(
                () -> assertEquals(new BigInt("42"), new BigInt("42")),
                () -> assertEquals(new BigInt("00042"), new BigInt("0042")),
                () -> assertEquals(new BigInt("+42"), new BigInt("42")),
                () -> assertNotEquals(new BigInt("42"), new BigInt("-42")),
                () -> assertNotEquals(new BigInt("-42"), new BigInt("42")),
                () -> assertNotEquals(new BigInt("422"), new BigInt("420"))
        );
    }

    @Test
    public void testAdd() {

        assertEquals("-407", new BigInt("-433").add(new BigInt("26")).toString());
        assertEquals("-407", new BigInt("26").add(new BigInt("-433")).toString());
        assertEquals("407", new BigInt("433").add(new BigInt("-26")).toString());
        assertEquals("-407", new BigInt("-26").add(new BigInt("-381")).toString());
        assertEquals("0", new BigInt("123").add(new BigInt("-123")).toString());

    }

    @Test
    public void testSub() {
        assertAll(
                // A - B
                () -> assertEquals("77", new BigInt("100").sub(new BigInt("23")).toString()),
                // A - (-B)
                () -> assertEquals("200", new BigInt("100").sub(new BigInt("-100")).toString()),
                // (-A) - B
                () -> assertEquals("-200", new BigInt("-100").sub(new BigInt("100")).toString()),
                // (-A) - (-B) where A > B
                () -> assertEquals("-23", new BigInt("-100").sub(new BigInt("-77")).toString()),
                // (-A) - (-B) where B > A
                () -> assertEquals("23", new BigInt("-77").sub(new BigInt("-100")).toString())
        );
    }

    @Test
    public void testMul() {
        assertAll(
                // A * B
                () -> assertEquals("56088", new BigInt("123").mul(new BigInt("456")).toString()),
                // A * (-B)
                () -> assertEquals("-56088", new BigInt("123").mul(new BigInt("-456")).toString()),
                // (-A) * B
                () -> assertEquals("-56088", new BigInt("-123").mul(new BigInt("456")).toString()),
                // (-A) * (-B)
                () -> assertEquals("56088", new BigInt("-123").mul(new BigInt("-456")).toString()),
                // multiply by zero
                () -> assertEquals("0", new BigInt("123456").mul(new BigInt("0")).toString())
        );
    }

    @Test
    public void testNegateAndCopy() {
        BigInt original = new BigInt("12345");
        BigInt copy = original.copy();
        BigInt negated = original.negate();

        assertAll(
                () -> assertEquals(original, copy),
                () -> assertEquals("-12345", negated.toString()),
                () -> assertEquals("12345", original.toString()) // make sure original isn't changed
        );
    }

    @Test
    public void testCompareTo() {
        assertAll(
                () -> assertTrue(new BigInt("100").compareTo(new BigInt("99")) > 0),
                () -> assertTrue(new BigInt("-100").compareTo(new BigInt("99")) < 0),
                () -> assertTrue(new BigInt("100").compareTo(new BigInt("-99")) > 0),
                () -> assertEquals(0, new BigInt("12345").compareTo(new BigInt("12345")))
        );
    }
}
