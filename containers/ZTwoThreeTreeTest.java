package containers;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ZTwoThreeTreeTest
{
  private TwoThreeTree<Integer> t;
  private TwoThreeTree<Integer> emptyTree;
  private TwoThreeTree<Integer> singletonTree;
  private String toStringerResult;
  
  @Before
  public void setUp() throws Exception {
    emptyTree = new TwoThreeTree<>();
    singletonTree = new TwoThreeTree<>();
    singletonTree.insert(3);
    t = new TwoThreeTree<>();
    t.insert(30);
    t.insert(40);
    t.insert(25);
    t.insert(25);
    t.insert(50);
    t.insert(20);
    t.insert(22);
    t.insert(27);
    t.insert(24);
    t.insert(35);
    t.insert(10);
    t.insert(45);
    toStringerResult = "";
  }


  @Test
  public void testTwoThreeTreeConstructor() {
    TwoThreeTree<Integer> t = new TwoThreeTree<>();
    assertEquals(0, t.size());
    assertTrue(t.isEmpty());
    assertFalse(t.contains(8));
    assertEquals(null, t.get(8));
  }

  @Test
  public void testSpecial() {
    TwoThreeTree<Integer> t = new TwoThreeTree<>();
    t.insert(30);
    t.insert(50);
    t.insert(40);
    t.insert(60);
    t.insert(70);
    t.insert(55);
    t.insert(59);
    System.out.println(t);
  }
  @Test
  public void testClear() {
    t.clear();
    assertEquals(0, t.size());
    assertTrue(t.isEmpty());
  }

  @Test
  public void testAddAndHeight() {
    TwoThreeTree<Integer> t = new TwoThreeTree<>();
    assertEquals(0, t.height());
    t.insert(30);
    assertEquals(0, t.height());
    t.insert(40);
    assertEquals(0, t.height());
    t.insert(25);
    assertEquals(1, t.height());
    t.insert(25);
    assertEquals(1, t.height());
    t.insert(50);
    assertEquals(1, t.height());
    t.insert(20);
    assertEquals(1, t.height());
    t.insert(22);
    assertEquals(1, t.height());
    t.insert(27);
    assertEquals(1, t.height());
    t.insert(24);
    assertEquals(2, t.height());
    t.insert(35);
    assertEquals(2, t.height());
    t.insert(10);
    assertEquals(2, t.height());
    t.insert(45);
    assertEquals(2, t.height());
    t.insert(5);
    assertEquals(2, t.height());
    t.insert(2);
    assertEquals(2, t.height());
    t.insert(3);
    assertEquals(2, t.height());
    t.insert(40);
    assertEquals(2, t.height());
    t.insert(60);
    assertEquals(3, t.height());
    assertFalse(t.isEmpty());
    assertEquals(15, t.size());
  }

  @Test
  public void testContainsAndGet() {
    assertTrue(t.contains(25));
    assertTrue(t.contains(10));
    assertTrue(t.contains(20));
    assertTrue(t.contains(22));
    assertTrue(t.contains(24));
    assertTrue(t.contains(30));
    assertTrue(t.contains(40));
    assertTrue(t.contains(45));
    assertTrue(t.contains(35));
    assertTrue(t.contains(27));
    assertFalse(t.contains(5));
    assertFalse(t.contains(15));
    assertFalse(t.contains(21));
    assertFalse(t.contains(23));
    assertFalse(t.contains(28));
    assertFalse(t.contains(33));
    assertEquals(25, t.get(25).intValue());
    assertEquals(10, t.get(10).intValue());
    assertEquals(20, t.get(20).intValue());
    assertEquals(22, t.get(22).intValue());
    assertEquals(24, t.get(24).intValue());
    assertEquals(30, t.get(30).intValue());
    assertEquals(40, t.get(40).intValue());
    assertEquals(45, t.get(45).intValue());
    assertEquals(35, t.get(35).intValue());
    assertEquals(27, t.get(27).intValue());
    assertEquals(null, t.get(5));
    assertEquals(null, t.get(15));
    assertEquals(null, t.get(21));
    assertEquals(null, t.get(23));
    assertEquals(null, t.get(28));
    assertEquals(null, t.get(33));
  }

  @Test
  public void testDelete() {
    assertEquals(11, t.size());
    t.delete(45);
    t.visit(new ToStringer());
    assertEquals("10202224252730354050", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("25221020243040273550", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(10, t.size());
    t.delete(50);
    t.visit(new ToStringer());
    assertEquals("102022242527303540", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("252210202430273540", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(9, t.size());
    t.delete(50);
    t.visit(new ToStringer());
    assertEquals("102022242527303540", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("252210202430273540", toStringerResult);
    assertEquals(9, t.size());
    assertEquals(2, t.height());
    t.delete(40);
    t.visit(new ToStringer());
    assertEquals("1020222425273035", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("2522102024302735", toStringerResult);
    t.visit(new ToStringer());
    assertEquals(8, t.size());
    assertEquals(2, t.height());
    t.delete(35);
    t.visit(new ToStringer());
    assertEquals("10202224252730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("22251020242730", toStringerResult);
    assertEquals(7, t.size());
    assertEquals(1, t.height());
    t.delete(24);
    t.visit(new ToStringer());
    assertEquals("102022252730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("202510222730", toStringerResult);
    assertEquals(6, t.size());
    t.delete(24);
    t.visit(new ToStringer());
    assertEquals("102022252730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("202510222730", toStringerResult);
    assertEquals(6, t.size());
    t.delete(22);
    t.visit(new ToStringer());
    assertEquals("1020252730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("2027102530", toStringerResult);
    assertEquals(5, t.size());
    t.delete(25);
    t.visit(new ToStringer());
    assertEquals("10202730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("20102730", toStringerResult);
    assertEquals(4, t.size());
    t.delete(10);
    assertEquals(3, t.size());
    t.delete(10);
    t.visit(new ToStringer());
    assertEquals("202730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("272030", toStringerResult);
    assertEquals(3, t.size());
    t.delete(20);
    t.visit(new ToStringer());
    assertEquals("2730", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("2730", toStringerResult);
    assertEquals(2, t.size());
    assertEquals(0, t.height());
    t.delete(27);
    t.visit(new ToStringer());
    assertEquals("30", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("30", toStringerResult);
    assertEquals(1, t.size());
    t.delete(30);
    t.visit(new ToStringer());
    assertEquals("", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("", toStringerResult);
    assertEquals(0, t.size());
    assertEquals(0, t.height());
    assertTrue(t.isEmpty());
    t.delete(30);
    assertEquals(0, t.size());

    t.insert(30);
    t.insert(40);
    t.insert(50);
    t.insert(10);
    t.insert(60);
    t.insert(70);
    t.delete(70);
    t.visit(new ToStringer());
    assertEquals("1030405060", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("3050104060", toStringerResult);
    assertEquals(1, t.height());
    assertEquals(5, t.size());
    t.insert(45);
    t.delete(60);
    t.delete(10);
    t.visit(new ToStringer());
    assertEquals("30404550", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("45304050", toStringerResult);
    assertEquals(1, t.height());
    assertEquals(4, t.size());
    t.insert(10);
    t.insert(20);
    t.insert(60);
    t.insert(15);
    t.insert(25);
    t.insert(35);
    t.delete(30);
    t.visit(new ToStringer());
    assertEquals("101520253540455060", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("351510202545405060", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(9, t.size());
    t.insert(5);
    t.insert(8);
    t.insert(12);
    t.insert(18);
    t.insert(70);
    t.delete(35);
    t.visit(new ToStringer());
    assertEquals("581012151820254045506070", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("154085101220182560455070", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(13, t.size());
    t.insert(55);
    t.delete(12);
    t.visit(new ToStringer());
    assertEquals("581015182025404550556070", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("154085102018255060455570", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(13, t.size());
    t.delete(8);
    t.visit(new ToStringer());
    assertEquals("51015182025404550556070", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("20501551018402545605570", toStringerResult);
    assertEquals(2, t.height());
    assertEquals(12, t.size());
  }
  
  @Test
  public void testInorderVisitor() {
    emptyTree.visit(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visit(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visit(new ToStringer());
    assertEquals("1020222425273035404550", toStringerResult);
  }

  @Test
  public void testPreorderVisitor() {
    emptyTree.visitPreorder(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visitPreorder(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visitPreorder(new ToStringer());
    assertEquals("2522102024304027354550", toStringerResult);
  }

  @Test
  public void testPostorderVisitor() {
    emptyTree.visitPostorder(new ToStringer());
    assertEquals("", toStringerResult);
    singletonTree.visitPostorder(new ToStringer());
    assertEquals("3", toStringerResult);
    t.visitPostorder(new ToStringer());
    assertEquals("1020242227354550304025", toStringerResult);
  }
  
  @Test
  public void testInorderIterator() {
    String result = "";
    Iterator<Integer> iter = emptyTree.iterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("", result);

    result = "";
    iter = singletonTree.iterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("3", result);

    result = "";
    iter = t.iterator();
    while (iter.hasNext()) result += iter.next();
    assertEquals("1020222425273035404550", result);
  }
  
  private class ToStringer implements Visitor<Integer> {
    public ToStringer() { toStringerResult = ""; }
    public void visit(Integer value) { toStringerResult += value; }
  }
}
