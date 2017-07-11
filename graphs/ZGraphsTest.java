package graphs;

import static org.junit.Assert.*;

import org.junit.Test;

import containers.List;

import org.junit.Before;

public class ZGraphsTest {
  private Graph g;
  private Graph h;
  private Graph k;

  @Before
  public void setUp() throws Exception {
    // g is connected
    g = new LinkedGraph(10);
    g.addEdge(0, 3);
    g.addEdge(2, 9);
    g.addEdge(1, 7);
    g.addEdge(3, 6);
    g.addEdge(1, 5);
    g.addEdge(4, 1);
    g.addEdge(1, 3);
    g.addEdge(5, 2);
    g.addEdge(2, 8);
    g.addEdge(3, 2);
    g.addEdge(4, 3);
    g.addEdge(5, 4);
    g.addEdge(6, 5);

    // h has two connected components
    h = new LinkedGraph(10);
    h.addEdge(0, 3);
    h.addEdge(2, 9);
    h.addEdge(1, 7);
    h.addEdge(3, 6);
    h.addEdge(1, 5);
    h.addEdge(4, 1);
    h.addEdge(8, 9);
    h.addEdge(2, 6);

    k = new ArrayGraph(6);
    k.addEdge(0, 1);
    k.addEdge(1, 4);
    k.addEdge(1, 2);
    k.addEdge(2, 3);
    k.addEdge(2, 5);
    k.addEdge(3, 1);
  }

  @Test
  public void testDFS() {
    Marker visitor = new Marker(10);
    Graphs.DFS(g, 0, visitor);
    assertTrue(visitor.isMarkedOnce());
  }

  @Test
  public void testStatckDFS() {
    Marker visitor = new Marker(10);
    Graphs.stackDFS(g, 0, visitor);
    assertTrue(visitor.isMarkedOnce());
  }

  @Test
  public void testBFS() {
    Marker visitor = new Marker(10);
    Graphs.BFS(g, 0, visitor);
    assertTrue(visitor.isMarkedOnce());
  }

  @Test
  public void testIsPath() {
    assertTrue(Graphs.isPath(h, 7, 5));
    assertTrue(Graphs.isPath(h, 0, 8));
    assertFalse(Graphs.isPath(h, 7, 2));
  }

  @Test
  public void testShortestPath() {
    assertEquals(null, Graphs.shortestPath(h, 7, 2));
    List<Integer> result = Graphs.shortestPath(k, 0, 5);
    assertEquals(0, result.get(0).intValue());
    assertEquals(1, result.get(1).intValue());
    assertEquals(2, result.get(2).intValue());
    assertEquals(5, result.get(3).intValue());
  }

  @Test
  public void testIsConnected() {
    assertTrue(Graphs.isConnected(g));
    assertFalse(Graphs.isConnected(h));
    assertTrue(Graphs.isConnected(k));
  }

  @Test
  public void testSpanningTree() {
    assertEquals(null, Graphs.spanningTree(h));
    Graph m = Graphs.spanningTree(k);
    assertTrue(m.isEdge(0, 1));
    assertTrue(m.isEdge(2, 1));
    assertTrue(m.isEdge(3, 2));
    assertTrue(m.isEdge(2, 5));
    assertTrue(m.isEdge(1, 4));
  }

  /***********************************************/
  
  private class Marker implements EdgeVisitor {
    private int[] visitCount;

    public Marker(int n) {
      visitCount = new int[n];
    }

    @Override
    public void visit(Graph g, int v, int w) {
      visitCount[w]++;
    }
    
    public boolean isMarkedOnce() {
      for (int k : visitCount) {
        if (k != 1) return false;
      }
      return true;
    }
  }
}
