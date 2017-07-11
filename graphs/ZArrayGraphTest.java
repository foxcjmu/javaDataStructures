package graphs;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class ZArrayGraphTest
{

  @Test
  public void testConstructor() {
    Graph g = new ArrayGraph(0);
    assertEquals(0, g.edges());
    assertEquals(0, g.vertices());
    g = new ArrayGraph(5);
    assertEquals(0, g.edges());
    assertEquals(5, g.vertices());
  }

  @Test
  public void testAddEdges() {
    Graph g = new ArrayGraph(10);
    assertEquals(0, g.edges());
    assertEquals(10, g.vertices());
    g.addEdge(0,0);
    assertEquals(0, g.edges());
    assertFalse(g.isEdge(0, 0));
    g.addEdge(0,10);
    assertEquals(0, g.edges());
    assertFalse(g.isEdge(0, 10));
    g.addEdge(0,1);
    assertEquals(1, g.edges());
    assertTrue(g.isEdge(0, 1));
    assertTrue(g.isEdge(1, 0));
  }

  @Test
  public void testIterator() {
    Graph g = new ArrayGraph(10);
    Iterator<Integer> it = g.iterator(0);
    assertFalse(it.hasNext());
    g.addEdge(0,1);
    g.addEdge(0,3);
    g.addEdge(0,5);
    g.addEdge(0,7);
    g.addEdge(0,9);
    int i = 1;
    for (Iterator<Integer> iter = g.iterator(0); iter.hasNext(); ) {
      assertEquals(i, iter.next().intValue());
      i += 2;
    }
  }

}
