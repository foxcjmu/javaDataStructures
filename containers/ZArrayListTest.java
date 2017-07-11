package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZArrayListTest
{

  @Test
  public void testConstructors() {
    ArrayList<String> s = new ArrayList<String>();
    assertEquals(ArrayList.INITIAL_SIZE, s.capacity());
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
    s = new ArrayList<String>(0);
    assertEquals(ArrayList.INITIAL_SIZE, s.capacity());
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
    s = new ArrayList<String>(13);
    assertEquals(13, s.capacity());
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
  }

  @Test public void testInsertAndDelete() {
    ArrayList<String> s = new ArrayList<String>(3);
    s.insert(0, "a");
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
    s.insert(0, "b");
    s.insert(1, "c");
    s.insert(2, "d");
    s.insert(4, "e");
    assertEquals(5, s.size());
    assertEquals(6, s.capacity());
    assertEquals("[b,c,d,a,e]",s.toString());
    
    assertEquals("b", s.delete(0));
    assertEquals(4, s.size());
    assertEquals("e", s.delete(3));
    assertEquals("d", s.delete(1));
    assertEquals("a", s.delete(1));
    assertEquals("c", s.delete(0));
    assertEquals(0, s.size());
  }

  @Test public void testClear() {
    ArrayList<String> s = new ArrayList<String>(3);
    s.insert(0, "a");
    s.insert(0, "b");
    s.insert(1, "c");
    s.insert(2, "d");
    s.insert(4, "e");
    assertEquals(5, s.size());
    assertEquals(6, s.capacity());
    assertEquals("[b,c,d,a,e]",s.toString());
    s.clear();
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
    assertEquals("[]", s.toString());
  }

  @Test public void testContainsAndGetAndPutAndIndex() {
    ArrayList<String> s = new ArrayList<String>(3);
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "b");
    s.insert(0, "a");
    assertEquals("[a,b,c,d,e]",s.toString());
    
    assertTrue(s.contains("a"));
    assertTrue(s.contains("e"));
    assertTrue(s.contains("d"));
    assertTrue(s.contains("b"));
    assertFalse(s.contains("f"));
    
    assertEquals("a", s.get(0));
    assertEquals("c", s.get(2));
    assertEquals("e", s.get(4));
    
    s.put(0, "0");
    s.put(2, "2");
    s.put(4, "4");
    assertEquals("[0,b,2,d,4]",s.toString());
    
    assertEquals(0, s.index("0"));
    assertEquals(2, s.index("2"));
    assertEquals(4, s.index("4"));
    assertEquals(-1, s.index("a"));
  }

  @Test public void testSliceAndEquals() {
    ArrayList<String> s = new ArrayList<String>(3);
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "b");
    s.insert(0, "a");
    assertEquals("[a,b,c,d,e]",s.toString());
    
    List<String> s1 = s.slice(0, 0);
    assertEquals(0, s1.size());
    assertFalse(s.equals(s1));
    s1 = s.slice(0, 1);
    assertEquals("[a]",s1.toString());
    assertFalse(s.equals(s1));
    s1 = s.slice(1, 3);
    assertEquals("[b,c]",s1.toString());
    assertFalse(s.equals(s1));
    s1 = s.slice(1, 5);
    assertEquals("[b,c,d,e]",s1.toString());
    assertFalse(s.equals(s1));
    s1 = s.slice(0, 5);
    assertEquals("[a,b,c,d,e]",s1.toString());
    assertTrue(s.equals(s1));
    s.put(0, "0");
    assertFalse(s.equals(s1));
  }

  @Test public void testIterator() {
    ArrayList<String> s = new ArrayList<String>(3);
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "b");
    s.insert(0, "a");
    assertEquals("[a,b,c,d,e]",s.toString());
    
    int i = 0;
    for (String v : s) {
      assertEquals(v, s.get(i++));
    }
  }
}
