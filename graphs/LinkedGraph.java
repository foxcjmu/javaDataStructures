package graphs;

import java.util.Iterator;
import containers.LinkedList;

/**
 * Undirected graphs implemented using an adjacency list. Vertices are numbers 0 to n-1.
 * All edges {v,w} are recorded twice in the data structure.
 * @author C. Fox
 */
public class LinkedGraph implements Graph {
  private Object[] adjacent;  // lists of vertices with edges to i
  private int nodes;          // vertex count
  private int edges;          // edge count

  /**
   * Make a graph with n nodes and no edges.
   * @param n how many vertices in the graph 
   */
  public LinkedGraph(int n) {
    if (n < 0) throw new IllegalArgumentException("Cannot have less than 0 nodes in a graph");
    nodes = n;
    edges = 0;
    adjacent = new Object[nodes];
    for (int i = 0; i < nodes; i++) adjacent[i] = new LinkedList<Integer>();
  }

  @Override
  public int edges() { return edges; }

  @Override
  public int vertices() { return nodes; }

  @Override
  public void addEdge(int v, int w) {
    if (v < 0 || w < 0 || nodes <= v || nodes <= w) return;
    ((LinkedList<Integer>)adjacent[v]).insert(0, w);
    ((LinkedList<Integer>)adjacent[w]).insert(0, v);
    edges++;
  }

  @Override
  public boolean isEdge(int v, int w) {
    if (v < 0 || w < 0 || nodes <= v || nodes <= w) return false;
    return ((LinkedList<Integer>)adjacent[v]).contains(w);
  }

  @Override
  public Iterator<Integer> iterator(int v) {
    if (v < 0 || nodes <= v) {
      LinkedList<Integer> empty = new LinkedList<>();
      return empty.iterator();
    }
    return ((LinkedList<Integer>)adjacent[v]).iterator();
  }
  
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("Vertex count: ").append(nodes).append('\n')
          .append("Edge count: ").append(edges).append('\n');
    for (int v = 0; v < nodes; v++) {
      Iterator<Integer> iter = iterator(v);
      while (iter.hasNext()) {
        int w = iter.next();
        result.append("{").append(v).append(", ").append(w).append("} ");
      }
      result.append('\n');
    }
    return result.toString();
  }
}
