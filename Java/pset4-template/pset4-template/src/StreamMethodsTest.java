import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StreamMethodsTest {

  @Test
  void testContainsHigh() {

    assertAll(
            () -> assertEquals(true,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{1, 451}))),
            () -> assertEquals(false,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{1, 449}))),
            () -> assertEquals(true,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{2, 300}, new int[]{3, 451}, new int[]{4, 200}))),
            () -> assertEquals(false,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{2, 300}, new int[]{3, 400}, new int[]{4, 200}))),
            () -> assertEquals(false,
                    StreamMethods.containsHigh(Arrays.asList())),
            () -> assertEquals(false,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{5, 450}))),
            () -> assertEquals(true,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{2, 500}, new int[]{3, 449}, new int[]{4, 451}))),
            () -> assertEquals(true,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{6, 700}, new int[]{7, 800}, new int[]{8, 900}))),
            () -> assertEquals(false,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{1, -100}, new int[]{2, -450}, new int[]{3, -300}))),
            () -> assertEquals(true,
                    StreamMethods.containsHigh(Arrays.asList(new int[]{1, -100}, new int[]{2, 451}, new int[]{3, -300})))
    );
  }

  @Test
  void testSqAddFiveOmit() {
    assertAll(
            () -> assertEquals(Arrays.asList(9, 14, 21),StreamMethods.sqAddFiveOmit(Arrays.asList(2, 3, 4))),
            () -> assertEquals(Arrays.asList(30, 230),StreamMethods.sqAddFiveOmit(Arrays.asList(5, 10, 15))),
            () -> assertEquals(Arrays.asList(),StreamMethods.sqAddFiveOmit(Arrays.asList())),
            () -> assertEquals(Arrays.asList(14),StreamMethods.sqAddFiveOmit(Arrays.asList(3))),
            () -> assertEquals(Arrays.asList(),StreamMethods.sqAddFiveOmit(Arrays.asList(10)))
    );
  }

  @Test
  void testRemoveLonger() {
    assertAll(
            () -> assertEquals(Arrays.asList("cat", "dog"),
                    StreamMethods.removeLonger(Arrays.asList("cat", "elephant", "dog", "giraffe"),3)),
            () -> assertEquals(Arrays.asList("cat", "dog", "rat"),
                    StreamMethods.removeLonger(Arrays.asList("cat", "dog", "rat"),5)),
            () -> assertEquals(Arrays.asList(),
                    StreamMethods.removeLonger(Arrays.asList("elephant", "giraffe", "hippopotamus"),4)),
            () -> assertEquals(Arrays.asList(),
                    StreamMethods.removeLonger(Arrays.asList(), 3)),
            () -> assertEquals(Arrays.asList("lion", "wolf"),
                    StreamMethods.removeLonger(Arrays.asList("lion", "wolf", "whale"), 4)),
            () -> assertEquals(Arrays.asList("fox", "bear"),
                    StreamMethods.removeLonger(Arrays.asList("fox", "bear", "elephant"), 4))
    );
  }

  @Test
  void testFilterSumChars() {
    assertAll(
            () -> assertEquals(204,
                    StreamMethods.filterSumChars("AbC123")),
            () -> assertEquals(198,
                    StreamMethods.filterSumChars("A@b#C!")),
            () -> assertEquals(291,
                    StreamMethods.filterSumChars("Z9y8x7")),
            () -> assertEquals(15,
                    StreamMethods.filterSumChars("12345")),
            () -> assertEquals(372,
                    StreamMethods.filterSumChars("hello")),
            () -> assertEquals(0,
                    StreamMethods.filterSumChars("")),
            () -> assertEquals(198,
                    StreamMethods.filterSumChars("aBc")),
            () -> assertEquals(267,
                    StreamMethods.filterSumChars("XYZ"))
    );
  }
}