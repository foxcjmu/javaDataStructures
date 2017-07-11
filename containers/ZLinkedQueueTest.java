package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZLinkedQueueTest {

  @Test
  public void testLinkedQueue() {
    LinkedQueue<Integer> q = new LinkedQueue<Integer>();
    assertTrue(q.isEmpty());
    assertEquals(0, q.size());
  }

  @Test
  public void testContainerMethods() {
    LinkedQueue<Integer> q = new LinkedQueue<Integer>();
    q.enter(4);
    assertFalse(q.isEmpty());
    assertEquals(1, q.size());
    q.enter(4);
    assertEquals(2, q.size());
    q.clear();
    assertEquals(0, q.size());
    assertTrue(q.isEmpty());
  }

  @Test
  public void testQueueMethods() {
    LinkedQueue<Integer> q = new LinkedQueue<Integer>();
    q.enter(4);
    assertEquals(4, q.front().intValue());
    q.enter(6);
    assertEquals(4, q.front().intValue());
    q.enter(8);
    assertEquals(4, q.front().intValue());
    assertEquals(3, q.size());
    assertEquals(4, q.leave().intValue());
    assertEquals(6, q.leave().intValue());
    assertEquals(8, q.leave().intValue());
    assertTrue(q.isEmpty());
  }
  
  @Test(expected = IllegalStateException.class)
  public void testLeavePreconditions() {
    Queue<Integer> q = new LinkedQueue<Integer>();
    q.leave();
  }
  
  @Test(expected = IllegalStateException.class)
  public void testFrontPreconditions() {
    Queue<Integer> q = new LinkedQueue<Integer>();
    q.front();
  }
}