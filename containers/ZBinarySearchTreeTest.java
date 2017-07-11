package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZBinarySearchTreeTest {
  private String toStringerResult;

  @Test
  public void testAdd() {
    BinarySearchTree<Integer> t = new BinarySearchTree<>();
    t.insert(7);
    t.insert(3);
    t.insert(1);
    t.insert(2);
    t.insert(12);
    t.insert(9);
    t.insert(1);
    t.insert(14);
    assertEquals(7, t.size());
    assertEquals(3, t.height());
    assertEquals(7,t.rootValue().intValue());
    assertEquals(3,t.leftSubtree().rootValue().intValue());
    assertEquals(12,t.rightSubtree().rootValue().intValue());
    assertEquals(1,t.leftSubtree().leftSubtree().rootValue().intValue());
    assertEquals(2,t.leftSubtree().leftSubtree().rightSubtree().rootValue().intValue());
    t.visitInorder(new ToStringer());
    assertEquals("123791214", toStringerResult);
  }

  @Test
  public void testContainsAndGet() {
    BinarySearchTree<Integer> t = new BinarySearchTree<>();
    assertFalse(t.contains(7));
    t.insert(7);
    t.insert(3);
    t.insert(1);
    t.insert(2);
    t.insert(12);
    t.insert(9);
    t.insert(1);
    t.insert(14);
    assertTrue(t.contains(7));
    assertEquals(7, t.get(7).intValue());
    assertTrue(t.contains(1));
    assertEquals(1, t.get(1).intValue());
    assertTrue(t.contains(2));
    assertEquals(2, t.get(2).intValue());
    assertTrue(t.contains(9));
    assertEquals(9, t.get(9).intValue());
    assertTrue(t.contains(12));
    assertEquals(12, t.get(12).intValue());
    assertFalse(t.contains(8));
    assertEquals(null, t.get(8));
  }

  @Test
  public void testRemove() {
    BinarySearchTree<Integer> t = new BinarySearchTree<>();
    assertFalse(t.contains(7));
    t.insert(7);
    t.insert(3);
    t.insert(1);
    t.insert(2);
    t.insert(12);
    t.insert(9);
    t.insert(1);
    t.insert(14);
    t.insert(13);
    t.delete(8);
    t.visitInorder(new ToStringer());
    assertEquals("12379121314", toStringerResult);
    t.delete(2);
    t.visitInorder(new ToStringer());
    assertEquals("1379121314", toStringerResult);
    t.insert(2);
    t.delete(9);
    t.visitInorder(new ToStringer());
    assertEquals("1237121314", toStringerResult);
    t.insert(9);
    t.delete(1);
    t.visitInorder(new ToStringer());
    assertEquals("2379121314", toStringerResult);
    t.delete(14);
    t.visitInorder(new ToStringer());
    assertEquals("23791213", toStringerResult);
    t.delete(3);
    t.visitInorder(new ToStringer());
    assertEquals("2791213", toStringerResult);
    t.delete(7);
    t.visitInorder(new ToStringer());
    assertEquals("291213", toStringerResult);
    t.delete(9);
    t.visitInorder(new ToStringer());
    assertEquals("21213", toStringerResult);
    t.delete(12);
    t.visitInorder(new ToStringer());
    assertEquals("213", toStringerResult);
    t.delete(13);
    t.visitInorder(new ToStringer());
    assertEquals("2", toStringerResult);
    t.delete(2);
    t.visitInorder(new ToStringer());
    assertEquals("", toStringerResult);
    assertTrue(t.isEmpty());
  }
  
  private class ToStringer implements Visitor<Integer> {
    public ToStringer() { toStringerResult = ""; }
    public void visit(Integer value) { toStringerResult += value; }
  }
}
