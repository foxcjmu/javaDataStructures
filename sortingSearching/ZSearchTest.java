package sortingSearching;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ZSearchTest
{
  static int SIZE = 10000;
  private int[] a = new int[SIZE];
  private int[] aSorted;

  @Before
  public void setUp() throws Exception {
    Random oracle = new Random();
    for (int i = 0; i < a.length; i++) {
      a[i] = (Math.abs(oracle.nextInt())) % (2*SIZE);
    }
    aSorted = Arrays.copyOf(a, a.length);
    Arrays.sort(aSorted);
  }

  @Test
  public void testSequentialSearch() {
    assertEquals(0, Searches.sequentialSearch(a, a[0]));
    a[a.length-1] = 2*SIZE;
    assertEquals(a.length-1, Searches.sequentialSearch(a, 2*SIZE));
    assertEquals(-1, Searches.sequentialSearch(a, -1));
  }

  @Test
  public void testBinarySearchRecursive() {
    aSorted[0] = -1;
    assertEquals(0, Searches.binarySearchRecursive(aSorted, aSorted[0]));
    aSorted[aSorted.length-1] = 2*SIZE;
    assertEquals(aSorted.length-1, Searches.binarySearchRecursive(aSorted, 2*SIZE));
    assertEquals(-1, Searches.binarySearchRecursive(aSorted, -100));
  }

  @Test
  public void testBinarySearch() {
    aSorted[0] = -1;
    assertEquals(0, Searches.binarySearch(aSorted, aSorted[0]));
    aSorted[aSorted.length-1] = 2*SIZE;
    assertEquals(aSorted.length-1, Searches.binarySearch(aSorted, 2*SIZE));
    assertEquals(-1, Searches.binarySearch(aSorted, -100));
  }

}
