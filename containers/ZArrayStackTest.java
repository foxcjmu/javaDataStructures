package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZArrayStackTest {

  @Test
  public void testArrayStack() {
    ArrayStack<Integer> s = new ArrayStack<Integer>();
    assertTrue(s.isEmpty());
    assertEquals(0, s.size());
    assertEquals(s.INITIAL_SIZE, s.capacity());
  }

  @Test
  public void testArrayStackInt() {
    ArrayStack<Integer> s = new ArrayStack<Integer>(13);
    assertTrue(s.isEmpty());
    assertEquals(0, s.size());
    assertEquals(13, s.capacity());
    s = new ArrayStack<Integer>(-1);
    assertEquals(s.INITIAL_SIZE, s.capacity());
  }

  @Test
  public void testContainerMethods() {
    ArrayStack<Integer> s = new ArrayStack<Integer>();
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
    ArrayStack<Integer> s = new ArrayStack<Integer>();
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

  @Test
  public void testStackExpansion() {
    ArrayStack<Integer> s = new ArrayStack<Integer>(3);
    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);
    s.push(6);
    s.push(7);
    assertEquals(12, s.capacity());
    assertEquals(7, s.pop().intValue());
    assertEquals(6, s.pop().intValue());
    assertEquals(5, s.pop().intValue());
    assertEquals(4, s.pop().intValue());
    assertEquals(3, s.pop().intValue());
    assertEquals(2, s.pop().intValue());
    assertEquals(1, s.pop().intValue());
    assertEquals(0, s.size());
    assertEquals(12, s.capacity());
  }

  @Test(expected = IllegalStateException.class)
  public void testPopPreconditions() {
    Stack<Integer> s = new ArrayStack<Integer>();
    s.pop();
  }
  
  @Test(expected = IllegalStateException.class)
  public void testTopPreconditions() {
    Stack<Integer> s = new ArrayStack<Integer>();
    s.top();
  }
}
