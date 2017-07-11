package sortingSearching;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ZSortTest
{
  static int SIZE = 10000;
  private int[] a = new int[SIZE];
  private int[] aSorted;
  private Integer[] b = new Integer[SIZE];

  @Before
  public void setUp() throws Exception {
    Random oracle = new Random();
    for (int i = 0; i < a.length; i++) {
      a[i] = (Math.abs(oracle.nextInt())) % (2*SIZE);
      b[i] = new Integer(a[i]);
    }
    aSorted = Arrays.copyOf(a, a.length);
    Arrays.sort(aSorted);
  }

  @Test
  public void testBubbleSort() {
    Sorts.bubbleSort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testSelectionSort() {
    Sorts.selectionSort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testInsertionSort() {
    Sorts.insertionSort(a);
    assertTrue(Arrays.equals(a, aSorted));
    Sorts.insertionSort(b);
    assertTrue(Sorts.isSorted(b));
  }

  @Test
  public void testShellSort() {
    Sorts.shellSort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testMergeSort() {
    Sorts.mergeSort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testQuicksort() {
    Sorts.quicksort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testQsort() {
    Sorts.qsort(a);
    assertTrue(Arrays.equals(a, aSorted));
    Sorts.qsort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

  @Test
  public void testHeapsort() {
    assertFalse(Sorts.isSorted(a));
    Sorts.heapsort(a);
    assertTrue(Arrays.equals(a, aSorted));
  }

}
