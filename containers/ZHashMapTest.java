package containers;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ZHashMapTest {
    private HashMap<String,Integer>emptyMap;
    private HashMap<String,Integer>singletonMap;
    private HashMap<String,Integer>m1;

  @Before
  public void setUp() throws Exception {
    emptyMap = new HashMap<>();
    singletonMap = new HashMap<>();
    singletonMap.insert("one", 1);
    m1 = new HashMap<>();
    m1.insert("two",  2);
    m1.insert("three",3);
    m1.insert("four", 4);
    m1.insert("five", 5);
    m1.insert("six",  6);
  }

  @Test
  public void testHashMap() {
    HashMap<String,Integer>m = new HashMap<>();
    assertTrue(m.isEmpty());
    assertEquals(0, m.size());
    m = new HashMap<>(0);
    assertEquals(Hashtable.DEFAULT_SIZE, m.tableSize());
    m = new HashMap<>(300);
    assertEquals(307, m.tableSize());
  }

  @Test
  public void testContains() {
    assertFalse(emptyMap.contains(1));
    assertTrue(singletonMap.contains(1));
    assertTrue(m1.contains(2));
    assertTrue(m1.contains(3));
    assertTrue(m1.contains(4));
    assertTrue(m1.contains(5));
    assertTrue(m1.contains(6));
    assertFalse(m1.contains(1));
  }

  @Test
  public void testIterator() {
    boolean check[] = new boolean[5];
    for (int i = 0; i < check.length; i++) check[i] = false;
    Iterator<Integer> iter;
    for (Integer v : m1) check[v.intValue()-2] = true;
    for (int i = 0; i < check.length; i++) assertTrue(check[i]);
  }

  @Test
  public void testSize() {
    assertEquals(1, singletonMap.size());
    assertEquals(5, m1.size());
  }

  @Test
  public void testIsEmpty() {
    assertFalse(singletonMap.isEmpty());
    assertFalse(m1.isEmpty());
  }

  @Test
  public void testClear() {
    singletonMap.clear();
    m1.clear();
    assertTrue(singletonMap.isEmpty());
    assertTrue(m1.isEmpty());
  }

  @Test
  public void testGet() {
    assertEquals(null, emptyMap.get("one"));
    assertEquals(1, singletonMap.get("one").intValue());
    assertEquals(6, m1.get("six").intValue());
    assertEquals(2, m1.get("two").intValue());
    assertEquals(5, m1.get("five").intValue());
    assertEquals(4, m1.get("four").intValue());
    assertEquals(3, m1.get("three").intValue());
    assertEquals(null, m1.get("seven"));
  }

  @Test
  public void testDelete() {
    m1.delete("six");
    assertEquals(4, m1.size());
    assertFalse(m1.contains(6));
    m1.delete("two");
    assertEquals(3, m1.size());
    assertFalse(m1.contains(2));
    m1.delete("four");
    assertEquals(2, m1.size());
    assertFalse(m1.contains(4));
    m1.delete("four");
    assertEquals(2, m1.size());
    assertFalse(m1.contains(4));
    m1.delete("five");
    assertEquals(1, m1.size());
    assertFalse(m1.contains(5));
    m1.delete("three");
    assertEquals(0, m1.size());
    assertFalse(m1.contains(3));
  }

  @Test public void testHasKey() {
    assertFalse(emptyMap.hasKey("one"));
    assertTrue(singletonMap.hasKey("one"));
    assertTrue(m1.hasKey("six"));
    assertTrue(m1.hasKey("two"));
    assertTrue(m1.hasKey("five"));
    assertTrue(m1.hasKey("four"));
    assertTrue(m1.hasKey("three"));
    assertFalse(m1.hasKey("seven"));
  }

  private class FillArray implements Visitor<Integer> {
    private boolean[] numbers = new boolean[5];
    public FillArray() {
      for (int i = 0; i < numbers.length; i++) numbers[i] = false;
    }

    public void mark(int i) { numbers[i] = true; }
    
    @Override
    public void visit(Integer value) { numbers[value.intValue()-1] = true; }
    
    public boolean isFilled() {
      for (int i = 0; i < numbers.length; i++) if (!numbers[i]) { return false; }
      return true;
    }
  }
  
  @Test
  public void testKeyIterator() {
    Iterator<String> iter = m1.keyIterator();
    FillArray filler = new FillArray();
    while (iter.hasNext()) filler.mark(m1.get(iter.next())-2);
    assertFalse(iter.hasNext());
    assertTrue(filler.isFilled());
  }

  @Test
  public void testIsEqual() {
    assertTrue(m1.isEqual(m1));
    assertFalse(m1.isEqual(emptyMap));
    assertFalse(emptyMap.isEqual(m1));
    assertFalse(m1.isEqual(singletonMap));
    assertFalse(singletonMap.isEqual(m1));
    assertTrue(emptyMap.isEqual(emptyMap));
  }

}
