package containers;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ZBinaryTreeTest
{
  private BinaryTree<Integer> emptyTree;
  private BinaryTree<Integer> singletonTree;
  private BinaryTree<Integer> t;
  private String toStringerResult;
  
  @Before
  public void setUp() throws Exception {
    emptyTree = new BinaryTree<>();
    singletonTree = new BinaryTree<>(3, new BinaryTree<>(), new BinaryTree<>());
    t = new BinaryTree<>(5,
                new BinaryTree<>(2,
                    new BinaryTree<>(8,
                        new BinaryTree<>(),
                        new BinaryTree<>()),
                    new BinaryTree<>()),
                new BinaryTree<>(7,
                    new BinaryTree<>(3,
                        new BinaryTree<>(),
                        new BinaryTree<>()),
                    new BinaryTree<>(4,
                        new BinaryTree<>(),
                        new BinaryTree<>(6,
                            new BinaryTree<>(),
                            new BinaryTree<>()))));
    toStringerResult = "";
    //System.out.print(t);
  }


  @Test
  public void testBinaryTreeConstructors() {
    assertEquals(0, emptyTree.size());
    assertTrue(emptyTree.isEmpty());
    assertEquals(1, singletonTree.size());
    assertFalse(singletonTree.isEmpty());
    assertEquals(7, t.size());
  }

  @Test
  public void testClear() {
    t.clear();
    assertEquals(0, t.size());
    assertTrue(t.isEmpty());
  }

  @Test
  public void testHeight() {
    assertEquals(0, emptyTree.height());
    assertEquals(0, singletonTree.height());
    assertEquals(3, t.height());
  }

  @Test
  public void testRootValue() {
    assertEquals(3, singletonTree.rootValue().intValue());
    assertEquals(5, t.rootValue().intValue());
  }

  @Test
  public void testLeftSubtree() {
    BinaryTree<Integer> t1 = t.leftSubtree();
    assertEquals(2, t1.size());
    assertEquals(1, t1.height());
    assertEquals(2, t1.rootValue().intValue());
    t1 = singletonTree.leftSubtree();
    assertEquals(0, t1.size());
    assertEquals(0, t1.height());
  }

  @Test
  public void testRightSubtree() {
    BinaryTree<Integer> t1 = t.rightSubtree();
    assertEquals(4, t1.size());
    assertEquals(2, t1.height());
    assertEquals(7, t1.rootValue().intValue());
    t1 = singletonTree.rightSubtree();
    assertEquals(0, t1.size());
    assertEquals(0, t1.height());
  }

  @Test
  public void testContains() {
    assertFalse(emptyTree.contains(5));
    assertTrue(t.contains(5));
    assertTrue(t.contains(8));
    assertTrue(t.contains(6));
    assertTrue(t.contains(3));
    assertTrue(t.contains(7));
    assertFalse(t.contains(9));
  }

  @Test
  public void testInorderVisitor() {
    emptyTree.visitInorder(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visitInorder(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visitInorder(new ToStringer());
    assertEquals("8253746", toStringerResult);
  }

  @Test
  public void testPreorderVisitor() {
    emptyTree.visitPreorder(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visitPreorder(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("5287346", toStringerResult);
  }

  @Test
  public void testPostorderVisitor() {
    emptyTree.visitPostorder(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visitPostorder(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visitPostorder(new ToStringer());
    assertEquals("8236475", toStringerResult);
  }
  
  @Test
  public void testInorderIterator() {
    String result = "";
    Iterator<Integer> iter = emptyTree.inorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("", result);

    result = "";
    iter = singletonTree.inorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("3", result);

    result = "";
    iter = t.inorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("8253746", result);
  }
  
  @Test
  public void testPreorderIterator() {
    String result = "";
    Iterator<Integer> iter = emptyTree.preorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("", result);

    result = "";
    iter = singletonTree.preorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("3", result);

    result = "";
    iter = t.preorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("5287346", result);
  }
  
  @Test
  public void testPostorderIterator() {
    String result = "";
    Iterator<Integer> iter = emptyTree.postorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("", result);

    result = "";
    iter = singletonTree.postorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("3", result);

    result = "";
    iter = t.postorderIterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("8236475", result);
  }
  
  private class ToStringer implements Visitor<Integer> {
    public ToStringer() { toStringerResult = ""; }
    public void visit(Integer value) { toStringerResult += value; }
  }
}
