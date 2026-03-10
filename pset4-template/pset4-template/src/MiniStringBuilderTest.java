import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class MiniStringBuilderTest {

  @Test
  void testMiniStringBuilder() {
      MiniStringBuilder builder = new MiniStringBuilder();
      assertAll(
              () -> assertEquals("", builder.toString()),
              () -> assertEquals(0, builder.toString().length())
      );
      MiniStringBuilder builder1 = new MiniStringBuilder("Hello");
      assertAll(
              () -> assertEquals("Hello", builder1.toString()),
              () -> assertEquals(5, builder1.toString().length())
      );
      MiniStringBuilder builder2 = new MiniStringBuilder("Hello");
      builder2.append(" World!");
      assertAll(
              () -> assertEquals("Hello World!", builder2.toString()),
              () -> assertEquals(12, builder2.toString().length())
      );
      MiniStringBuilder builder3 = new MiniStringBuilder("A");
      for (int i = 0; i < 100; i++) {
        builder3.append("A");
      }
      assertAll(
              () -> assertEquals(101, builder3.toString().length()),
              () -> assertEquals("A" + "A".repeat(100), builder3.toString())
      );
      MiniStringBuilder builder4 = new MiniStringBuilder("Hello");
      builder4.clear();
      assertAll(
              () -> assertEquals("", builder4.toString()),
              () -> assertEquals(0, builder4.toString().length())
      );
      MiniStringBuilder builder5 = new MiniStringBuilder("Hello");
      MiniStringBuilder builder6 = new MiniStringBuilder("Hello");
      MiniStringBuilder builder7 = new MiniStringBuilder("World");

      assertAll(
              () -> assertEquals(true, builder5.equals(builder6)),
              () -> assertEquals(false, builder5.equals(builder7))
      );
      MiniStringBuilder builder8 = new MiniStringBuilder("Hello");
      assertAll(
              () -> assertEquals("Hello".hashCode(), builder8.toString().hashCode())
      );
      MiniStringBuilder builder9 = new MiniStringBuilder("Hello");
      assertAll(
              () -> assertEquals("Hello", builder9.toString()),
              () -> assertEquals(5, builder9.toString().length())
      );
  }
}