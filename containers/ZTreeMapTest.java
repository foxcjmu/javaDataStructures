package containers;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ZTreeMapTest {
    private TreeMap<String,Integer>emptyMap;
    private  TreeMap<String,Integer>singletonMap;
    private TreeMap<String,Integer>m1;

  @Before
  public void setUp() throws Exception {
    emptyMap = new TreeMap<>();
    singletonMap = new TreeMap<>();
    singletonMap.insert("one", 1);
    m1 = new TreeMap<>();
    m1.insert("two",  2);
    m1.insert("three",3);
    m1.insert("four", 4);
    m1.insert("five", 5);
    m1.insert("six",  6);
  }

  @Test
  public void testTreeMap() {
    TreeMap<String,Integer>m = new TreeMap<>();
    assertTrue(m.isEmpty());
    assertEquals(0, m.size());
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

  @Test
  public void testKeyIterator() {
    Iterator<String> iter = m1.keyIterator();
    assertEquals("five",iter.next());
    assertEquals("four",iter.next());
    assertEquals("six", iter.next());
    assertEquals("three",iter.next());
    assertEquals("two", iter.next());
    assertFalse(iter.hasNext());
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
