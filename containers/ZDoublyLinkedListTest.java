package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZDoublyLinkedListTest
{

  @Test
  public void testConstructors() {
    List<String> s = new DoublyLinkedList<String>();
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
  }

  @Test public void testInsertAndDeleteAndContains() {
    List<String> s = new DoublyLinkedList<String>();
    assertFalse(s.contains("f"));
    s.insert(0, "a");
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
    s.insert(0, "b");
    s.insert(1, "c");
    s.insert(2, "d");
    s.insert(4, "e");
    assertEquals(5, s.size());
    assertEquals("[b,c,d,a,e]",s.toString());
    assertTrue(s.contains("d"));
    assertTrue(s.contains("c"));
    assertTrue(s.contains("a"));
    assertTrue(s.contains("e"));
    assertTrue(s.contains("b"));
    assertFalse(s.contains("f"));
    
    assertEquals("b", s.delete(0));
    assertEquals(4, s.size());
    assertEquals("e", s.delete(3));
    assertEquals("d", s.delete(1));
    assertEquals("a", s.delete(1));
    assertEquals("c", s.delete(0));
    assertEquals(0, s.size());
  }

  @Test public void testClear() {
    List<String> s = new DoublyLinkedList<String>();
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "a");
    s.clear();
    assertTrue(s.isEmpty());
    assertEquals(0, s.size());
  }
  
  @Test public void testGetAndPutAndIndex() {
    List<String> s = new DoublyLinkedList<String>();
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "b");
    s.insert(0, "a");
    assertEquals("[a,b,c,d,e]",s.toString());
    
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
    List<String> s = new DoublyLinkedList<String>();
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
    List<String> s = new DoublyLinkedList<String>();
    s.insert(0, "e");
    s.insert(0, "d");
    s.insert(0, "c");
    s.insert(0, "b");
    s.insert(0, "a");
    assertEquals("[a,b,c,d,e]",s.toString());
    
    int i = 0;
    for (String v : s) assertEquals(s.get(i++), v);
  }
}
