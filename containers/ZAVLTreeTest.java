package containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZAVLTreeTest {
  private String toStringerResult;

  @Test
  public void testAdd() {
    AVLTree<Integer> t = new AVLTree<>();
    assertEquals(0, t.height());
    assertEquals(0, t.balance());
    t.insert(7);
    assertEquals(0, t.height());
    assertEquals(0, t.balance());
    t.insert(3);
    assertEquals(1, t.height());
    assertEquals(1, t.balance());
    t.insert(1);
    assertEquals(1, t.height());
    assertEquals(0, t.balance());
    t.insert(2);
    assertEquals(2, t.height());
    assertEquals(1, t.balance());
    t.insert(12);
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(9);
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(1);
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(14);
    assertEquals(3, t.height());
    assertEquals(-1, t.balance());
    t.insert(13);
    assertEquals(3, t.height());
    assertEquals(-1, t.balance());
    t.insert(30);
    assertEquals(3, t.height());
    assertEquals(-1, t.balance());
    t.insert(5);
    assertEquals(3, t.height());
    assertEquals(0, t.balance());
    t.visitInorder(new ToStringer());
    assertEquals("12357912131430", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("93127513121430", toStringerResult);
    assertEquals(5,t.leftSubtree().rightSubtree().leftSubtree().rootValue().intValue());
  }

  @Test
  public void testContainsAndGet() {
    AVLTree<Integer> t = new AVLTree<>();
    assertFalse(t.contains(7));
    assertEquals(null, t.get(7));
    t.insert(7);
    t.insert(3);
    t.insert(1);
    t.insert(2);
    t.insert(12);
    t.insert(9);
    t.insert(14);
    t.insert(13);
    t.insert(5);
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
    AVLTree<Integer> t = new AVLTree<>();
    assertEquals("Empty tree\n", t.toString());
    t.delete(5);
    t.insert(7);
    t.insert(3);
    t.insert(6);
    t.insert(2);
    t.insert(12);
    t.insert(5);
    t.insert(9);
    t.insert(1);
    t.insert(14);
    t.visitPreorder(new ToStringer());
    assertEquals("63215971214", toStringerResult);
    t.delete(0);
    t.visitPreorder(new ToStringer());
    assertEquals("63215971214", toStringerResult);
    t.delete(100);
    t.visitPreorder(new ToStringer());
    assertEquals("63215971214", toStringerResult);
    assertEquals(5,t.leftSubtree().rightSubtree().rootValue().intValue());
    assertEquals(3, t.height());
    assertEquals(0, t.balance());
    t.delete(14);
    t.visitPreorder(new ToStringer());
    assertEquals("632159712", toStringerResult);
    assertEquals(12,t.rightSubtree().rightSubtree().rootValue().intValue());
    assertEquals(3, t.height());
    assertEquals(1, t.balance());
    t.delete(1);
    t.visitPreorder(new ToStringer());
    assertEquals("63259712", toStringerResult);
    assertEquals(2,t.leftSubtree().leftSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(1);
    t.insert(14);
    t.delete(2);
    t.visitPreorder(new ToStringer());
    assertEquals("6315971214", toStringerResult);
    assertEquals(1,t.leftSubtree().leftSubtree().rootValue().intValue());
    assertEquals(3, t.height());
    assertEquals(-1, t.balance());
    t.delete(12);
    t.visitPreorder(new ToStringer());
    assertEquals("63159714", toStringerResult);
    assertEquals(14,t.rightSubtree().rightSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.delete(3);
    t.visitPreorder(new ToStringer());
    assertEquals("6519714", toStringerResult);
    assertEquals(5,t.leftSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.delete(6);
    t.visitPreorder(new ToStringer());
    assertEquals("751914", toStringerResult);
    assertEquals(1,t.leftSubtree().leftSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(8);
    t.insert(12);
    t.delete(5);
    t.visitPreorder(new ToStringer());
    assertEquals("97181412", toStringerResult);
    assertEquals(8,t.leftSubtree().rightSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(5);
    t.delete(9);
    t.visitPreorder(new ToStringer());
    assertEquals("71512814", toStringerResult);
    assertEquals(8,t.rightSubtree().leftSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.insert(10);
    t.delete(1);
    t.visitPreorder(new ToStringer());
    assertEquals("875121014", toStringerResult);
    assertEquals(10,t.rightSubtree().leftSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(0, t.balance());
    t.delete(5);
    t.delete(7);
    t.visitPreorder(new ToStringer());
    assertEquals("1281014", toStringerResult);
    assertEquals(10,t.leftSubtree().rightSubtree().rootValue().intValue());
    assertEquals(2, t.height());
    assertEquals(1, t.balance());
    t.delete(12);
    t.visitPreorder(new ToStringer());
    assertEquals("10814", toStringerResult);
    assertEquals(1, t.height());
    assertEquals(0, t.balance());
    t.delete(14);
    t.visitPreorder(new ToStringer());
    assertEquals("108", toStringerResult);
    assertEquals(1, t.height());
    assertEquals(1, t.balance());
    t.delete(10);
    t.visitPreorder(new ToStringer());
    assertEquals("8", toStringerResult);
    assertEquals(0, t.height());
    assertEquals(0, t.balance());
    t.delete(8);
    t.visitPreorder(new ToStringer());
    assertEquals(0, t.height());
    assertEquals(0, t.balance());
    assertTrue(t.isEmpty());
  }
  
  private class ToStringer implements Visitor<Integer> {
    public ToStringer() { toStringerResult = ""; }
    public void visit(Integer value) { toStringerResult += value; }
  }
}
