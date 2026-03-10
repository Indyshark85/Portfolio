import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

  @Test
  void testBag() {
    Bag<String> bag = new Bag<>();
    bag.insert("apple");
    bag.insert("banana");
    bag.insert("apple");
    assertAll(
            () -> assertEquals(3, bag.size(), "Bag size should be 3"),
            () -> assertEquals(2, bag.count("apple"), "Apple count should be 2"),
            () -> assertEquals(1, bag.count("banana"), "Banana count should be 1")
    );
    assertAll(
            () -> assertEquals(true, bag.remove("apple"), "Apple should be removed successfully"),
            () -> assertEquals(2, bag.size(), "Bag size should be 2 after removal"),
            () -> assertEquals(1, bag.count("apple"), "Apple count should be 1 after removal")
    );
    assertAll(
            () -> assertEquals(true, bag.remove("apple"), "Apple should be removed successfully again"),
            () -> assertEquals(1, bag.size(), "Bag size should be 1"),
            () -> assertEquals(0, bag.count("apple"), "Apple count should be 0 after removal"),
            () -> assertEquals(false, bag.contains("apple"), "Bag should not contain 'apple'")
    );
    assertAll(
            () -> assertEquals(true, bag.contains("banana"), "Bag should contain 'banana'"),
            () -> assertEquals(false, bag.contains("orange"), "Bag should not contain 'orange'")
    );
    bag.insert("cherry");
    Bag<String> subBag = new Bag<>();
    subBag.insert("banana");
    assertAll(
            () -> assertEquals(true, bag.isSubBag(subBag), "SubBag should be valid"),
            () -> assertEquals(false, subBag.isSubBag(bag), "Bag is not a sub-bag of subBag")
    );

  }
}