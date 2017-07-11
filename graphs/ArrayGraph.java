package graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Undirected graphs implemented using an adjacency matrix. Vertices are numbers 0 to n-1.
 * All edges {v,w} are recorded twice in the matrix.
 * @author C. Fox
 */
public class ArrayGraph implements Graph {
  private int nodes;            // vertiex count
  private int edges;            // edge count
  private boolean[][] matrix;   // matrix[v][w] == true iff {v,w} is an edge

  /**
   * Make a graph with n nodes and no edges.
   * @param n how many vertices in the graph
   */
  public ArrayGraph(int n) {
    if (n < 0) throw new IllegalArgumentException("Graphs cannot have negative sizes");
    nodes = n;
    matrix = new boolean[nodes][nodes];
    for (int i = 0; i < nodes; i++)
      for (int j = 0; j < nodes; j++) matrix[i][j] = false;
  }

  @Override
  public int edges() { return edges; }

  @Override
  public int vertices() { return nodes; }

  @Override
  public void addEdge(int v, int w) {
    if (v < 0 || w < 0 || nodes <= v || nodes <= w || v == w) return;
    matrix[v][w] = matrix[w][v] = true;
    edges++;
  }

  @Override
  public boolean isEdge(int v, int w) {
    if (v < 0 || w < 0 || nodes <= v || nodes <= w) return false;
    return matrix[v][w];
  }

  @Override
  public Iterator<Integer> iterator(int v) { return new EdgeIterator(v); }

  /**
   * Return in iterator that traverses all the vertices w such that {v,w} is an edge.
   */
  private class EdgeIterator implements Iterator<Integer> {
    private int v;
    private int w;
    
    /**
     * Arrange to iterator over all edges from vertex x
     * @param x the vertex involved in the iterated edges
     */
    public EdgeIterator(int x) {
      v = x;
      w = nodes;
      if (v < 0 || nodes <= v) return;
      for (int i = 0; i < nodes; i++) {
        if (matrix[v][i]) {
          w = i;
          break;
        }
      }
    }

    @Override
    public boolean hasNext() { return w < nodes; }

    @Override
    public Integer next() {
      if (nodes <= w) throw new NoSuchElementException();
      int result = w;
      w = nodes;
      for (int i = result+1; i < nodes; i++) {
        if (matrix[v][i]) {
          w = i;
          break;
        }
      }
      return result;
    }
  } // EdgeIterator
  
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
