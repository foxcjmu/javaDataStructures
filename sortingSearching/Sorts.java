package sortingSearching;

import java.util.Arrays;

/**
 * This class is a container for a collection of static sorting methods.
 * @author C. Fox
 */
public class Sorts {
  
  /**
   * Determine whether an int array is sorted from least to greatest.
   * 
   * @param a the array tested
   * @return true iff a is sorted
   */
  public static boolean isSorted(int[] a) {
    for (int i = 1; i < a.length; i++)
      if (a[i] < a[i-1]) return false;
    return true;
  }
  
  /**
   * Determine whether an array of Comparable objects is sorted from least to greatest.
   * 
   * @param a the array tested
   * @return true iff a is sorted
   */
  public static <T extends Comparable<T>> boolean isSorted(T[] a) {
    for (int i = 1; i < a.length; i++)
      if (a[i].compareTo(a[i-1]) < 0) return false;
    return true;
  }
  
  /**
   * Sort an int array using bubble sort.
   * 
   * @param a the array sorted
   */
  public static void bubbleSort(int[] a) {
    for (int j = a.length - 1; 0 < j; j--) {
      for (int i = 0; i < j; i++) {
        if (a[i+1] < a[i]) {
          int tmp = a[i];
          a[i] = a[i + 1];
          a[i + 1] = tmp;
        }
      }
    }
  }

  /**
   * Sort an int array using selection sort.
   * 
   * @param a the array sorted
   */
  public static void selectionSort(int[] a) {
    for (int j = 0; j < a.length-1; j++) {
      int minIndex = j;
      for (int i = j+1; i < a.length; i++)
        if (a[i] < a[minIndex]) minIndex = i;
      int tmp = a[j];
      a[j] = a[minIndex];
      a[minIndex] = tmp;
    }
  }
  
  /**
   * Sort an int array using insertion sort.
   * 
   * @param a the array sorted
   */
  public static void insertionSort(int[] a) {
    for (int j = 1; j < a.length; j++) {
      int element = a[j];
      int i;
      for (i = j; 0 < i && element < a[i-1]; i--) a[i] = a[i-1];
      a[i] = element;
    }
  }
  
  /**
   * Sort an array of Comparable objects using insertion sort.
   * 
   * @param a the array sorted
   */
  public static <T extends Comparable<T>> void insertionSort(T[] a) {
    for (int j = 1; j < a.length; j++) {
      T element = a[j];
      int i;
      for (i = j; 0 < i && element.compareTo(a[i-1]) < 0; i--) a[i] = a[i-1];
      a[i] = element;
    }
  }
  
  /**
   * Sort an int array using Shell sort.
   * 
   * @param a the array sorted
   */
  public static void shellSort(int[] a) {
    int h = 1;
    while (h < a.length/9) h = 3*h + 1;

    while (0 < h) {
      for (int j = h; j < a.length; j++) {
        int element = a[j];
        int i;
        for (i = j; h <= i && element < a[i-h]; i -= h) a[i] = a[i-h];
        a[i] = element;
      }
      h /= 3;
    }
  }
  
  /**
   * Sort an int array using mergesort.
   * 
   * @param a the array sorted
   */
  public static void mergeSort(int[] a) {
    int[] auxiliary = Arrays.copyOf(a, a.length);
    mergeInto(a, auxiliary, 0, a.length);
  }

  /**
   * Merge sorted upper and a lower halves of a source array segment into a parallel destination 
   * array segment. Note that the halves of the source segment must be sorted for this to work.
   * 
   * @param dst array where the result is placed
   * @param src array whose upper and lower segment halves are sorted
   * @param lb the start of the parallel array segments
   * @param length the size of the parallel array segments
   */
  private static void mergeInto(int[] dst, int[] src, int lo, int length) {
    if (length < 2) return;
    int loLength = length/2;
    int hiLength = length-loLength;
    int hi = lo+loLength;
    mergeInto(src, dst, lo, loLength);
    mergeInto(src, dst, hi, hiLength);
    int j = lo;
    int k = hi;
    for (int i = lo; i < lo+length; i++) {
      if (j < hi && k < hi+hiLength) {
        if (src[j] < src[k]) dst[i] = src[j++];
        else                 dst[i] = src[k++];
      }
      else if (j < hi) dst[i] = src[j++];
      else             dst[i] = src[k++];
    }
  }

  /**
   * Sort an int array using quicksort with the median-of-three improvement.
   * 
   * @param a the array sorted
   */
  public static void quicksort(int[] a) {
    quicksortSublist(a, 0, a.length-1);
  }

  /**
   * Recursively sort a sub-list of an array using basic quicksort.
   * 
   * @param a the array containing the sub-list
   * @param lb index of the first element of the sub-list
   * @param ub index of the last element of the sub-list
   */
  private static void quicksortSublist(int[] a, int lb, int ub) {
    if (ub <= lb) return;
    int pivot = a[ub];
    int i = lb-1;
    int j = ub;
    while (i < j) {
      do { i++; } while (a[i] < pivot);
      do { j--; } while (lb < j && pivot < a[j]);
      int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
    int tmp = a[i];
    a[ub] = a[j];
    a[j] = tmp;
    a[i] = pivot;
    quicksortSublist(a, lb, i-1);
    quicksortSublist(a, i+1, ub);
  }
  
  /**
   * Sort an int array using quicksort with the median-of-three improvement.
   * 
   * @param a the array sorted
   */
  public static void qsort(int[] a) {
    qsortSublist(a, 0, a.length-1);
  }

  /**
   * Recursively sort a sub-list of an array using quicksort with the
   * median-of-three improvement.
   * 
   * @param a the array containing the sub-list
   * @param lb index of the first element of the sub-list
   * @param ub index of the last element of the sub-list
   */
  private static void qsortSublist(int[] a, int lb, int ub) {
    if (ub <= lb) return;
    int m = (ub+lb)/2;
    if (a[m]  < a[lb]) { int tmp = a[m]; a[m] = a[lb]; a[lb] = tmp; }
    if (a[ub] < a[m])  { int tmp = a[ub]; a[ub] = a[m]; a[m] = tmp; }
    if (a[m]  < a[lb]) { int tmp = a[m]; a[m] = a[lb]; a[lb] = tmp; }
    if (ub-lb < 3) return;
    int pivot = a[m];
    int tmp = a[ub-1]; a[ub-1] = a[m]; a[m] = tmp;
    int i = lb;
    int j = ub-1;
    while (i < j) {
      do { i++; } while (a[i] < pivot);
      do { j--; } while (lb < j && pivot < a[j]);
      tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
    tmp = a[i];
    a[ub-1] = a[j];
    a[j] = tmp;
    a[i] = pivot;
    qsortSublist(a, lb, i-1);
    qsortSublist(a, i+1, ub);
  }
  
  /**
   * Sort an int array using heapsort with.
   * 
   * @param a the array sorted
   */
  public static void heapsort(int[] a) {
    if (a.length < 2) return;
    int maxIndex = a.length-1;
    for (int i = (maxIndex-1)/2; 0 <= i; i--) siftDown(a, i, maxIndex);
    while (true) {
      int tmp = a[0]; a[0] = a[maxIndex]; a[maxIndex] = tmp;
      maxIndex--;
      if (maxIndex <= 0) break;
      siftDown(a, 0, maxIndex);
    }
  }

  /**
   * Make a tree into a heap from its root, assuming the subtrees of the root
   * are already heaps. The tree is stored between elements i and maxIndex of a.
   * 
   * @param a the array holding the heap data
   * @param root the root of the sub-tree made into a heap
   * @param maxIndex the last element in the portion of a made into a heap
   */
  private static void siftDown(int[] a, int i, int maxIndex) {
    int tmp = a[i];
    for (int j = 2*i+1; j <= maxIndex; j = 2*i+1) {
      if (j < maxIndex && a[j] < a[j+1]) j++;
      if (a[j] <= tmp) break;
      a[i] = a[j];
      i = j;
    }
    a[i] = tmp;
  }
  
}
