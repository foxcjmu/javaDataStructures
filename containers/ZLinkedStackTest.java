package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZLinkedStackTest {

  @Test
  public void testLinkedStack() {
    LinkedStack<Integer> s = new LinkedStack<Integer>();
    assertTrue(s.isEmpty());
    assertEquals(0, s.size());
  }

  @Test
  public void testContainerMethods() {
    LinkedStack<Integer> s = new LinkedStack<Integer>();
    s.push(4);
    assertFalse(s.isEmpty());
    assertEquals(1, s.size());
    s.push(4);
    assertEquals(2, s.size());
    s.clear();
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
  }

  @Test
  public void testStackMethods() {
    LinkedStack<Integer> s = new LinkedStack<Integer>();
    s.push(4);
    assertEquals(4, s.top().intValue());
    s.push(6);
    assertEquals(6, s.top().intValue());
    s.push(8);
    assertEquals(8, s.top().intValue());
    assertEquals(3, s.size());
    assertEquals(8, s.pop().intValue());
    assertEquals(6, s.pop().intValue());
    assertEquals(4, s.pop().intValue());
    assertTrue(s.isEmpty());
  }
  
  @Test(expected = IllegalStateException.class)
  public void testPopPreconditions() {
    Stack<Integer> s = new LinkedStack<Integer>();
    s.pop();
  }
  
  @Test(expected = IllegalStateException.class)
  public void testTopPreconditions() {
    Stack<Integer> s = new LinkedStack<Integer>();
    s.top();
  }
}
