import java.util.HashMap;
import java.util.Map;

class Bag<T> {
   private Map<T,Integer> map;
   private int totalsize;

  Bag() {
    this.map=new HashMap<>();
    this.totalsize=0;
  }

  /**
   *  method, which inserts an item 𝑡 into the bag
   * @param t
   */
  void insert(T t) {
    map.put(t, map.getOrDefault(t,0)+1);
    totalsize++;
  }

  /**
   * method, which removes an item 𝑡 from the bag, meaning
   * its associated count is decremented by one. If 𝑡 does not exist, the method returns false,
   * and otherwise returns true.
   * @param t
   * @return bool
   */
  boolean remove(T t) {
    if (!map.containsKey(t)){
      return false;
    }
    int count = map.get(t);
    if (count == 1){
      map.remove(t);
    }else{
      map.put(t,count-1);
    }
    totalsize--;
    return true;
  }

  /**
   * method that returns the number of items in the bag.
   * @return int
   */
  int size() {
    return totalsize;
  }

  /**
   *  method that returns the respective quantity of item 𝑡 in the bag.
   * @param t
   * @return int
   */
  int count(T t) {
    return map.getOrDefault(t,0);
  }

  /**
   *  method that returns whether the bag contains t.
   * @param t
   * @return bool
   */
  boolean contains(T t) {
    return map.containsKey(t);
  }

  /**
   * method that determines whether 𝑏 is a sub-bag
   * of this bag. A sub-bag 𝑏1 is a sub-bag of 𝑏2 if, for every element 𝑖 ∈ 𝑏1, then 𝑖 ∈ 𝑏2 and
   * count(𝑏1 [𝑖]) ≤ count(𝑏2 [𝑖]).
   * @param b
   * @return bool
   */
  boolean isSubBag(Bag<T> b) {
    for (Map.Entry<T, Integer> entry: b.map.entrySet()) {
      T key = entry.getKey();
      int value = entry.getValue();
      if (this.count(key)<value){
        return false;
      }
    }
    return true;
  }
}
