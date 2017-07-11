package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZArrayQueueTest {

  @Test
  public void testArrayQueue() {
    ArrayQueue<Integer> q = new ArrayQueue<Integer>();
    assertTrue(q.isEmpty());
    assertEquals(0, q.size());
    assertEquals(q.INITIAL_SIZE, q.capacity());
  }

  @Test
  public void testArrayQueueInt() {
    ArrayQueue<Integer> q = new ArrayQueue<Integer>(13);
    assertTrue(q.isEmpty());
    assertEquals(0, q.size());
    assertEquals(13, q.capacity());
    q = new ArrayQueue<Integer>(-1);
    assertEquals(q.INITIAL_SIZE, q.capacity());
  }

  @Test
  public void testContainerMethods() {
    Queue<Integer> q = new ArrayQueue<Integer>();
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
    Queue<Integer> q = new ArrayQueue<Integer>();
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

  @Test
  public void testQueueExpansion() {
    ArrayQueue<Integer> q = new ArrayQueue<Integer>(3);
    q.enter(1);
    q.enter(2);
    q.enter(3);
    q.enter(4);
    q.enter(5);
    q.enter(6);
    q.enter(7);
    assertEquals(12, q.capacity());
    assertEquals(1, q.leave().intValue());
    assertEquals(2, q.leave().intValue());
    assertEquals(3, q.leave().intValue());
    assertEquals(4, q.leave().intValue());
    assertEquals(5, q.leave().intValue());
    assertEquals(6, q.leave().intValue());
    assertEquals(7, q.leave().intValue());
    assertEquals(0, q.size());
    assertEquals(12, q.capacity());
  }
  
  @Test(expected = IllegalStateException.class)
  public void testLeavePreconditions() {
    Queue<Integer> q = new ArrayQueue<Integer>();
    q.leave();
  }
  
  @Test(expected = IllegalStateException.class)
  public void testFrontPreconditions() {
    Queue<Integer> q = new ArrayQueue<Integer>();
    q.front();
  }
}
