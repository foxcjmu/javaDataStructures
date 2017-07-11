package sortingSearching;

/**
 * This class is a container for a collection of static searching methods.
 * @author C. Fox
 */
public class Searches {

  /**
   * Find an int key in an int array by looking at each element in turn.
   * @param a the array searched
   * @param key what is searched for
   * @return the index of the key in a, or -1 if the key is not present
   */
  public static int sequentialSearch(int[] a, int key) {
    for (int i = 0; i < a.length; i++) if (a[i] == key) return i;
    return -1;
  }

  /**
   * Find a key of Comparable type in an array of Comparable type by looking at each element in turn.
   * @param a the array searched
   * @param key what is searched for
   * @return the index of the key in a, or -1 if the key is not present
   */
  public static <T extends Comparable<T>> int sequentialSearch(T[] a, T key) {
    for (int i = 0; i < a.length; i++) if (a[i].compareTo(key) == 0) return i;
    return -1;
  }

  /**
   * Find an int key in an int array using a recursive binary search.
   * The array must be sorted.
   * @param a the array searched
   * @param key what is searched for
   * @return the index of the key in a, or -1 if the key is not present
   */
  public static int binarySearchRecursive(int[] a, int key) {
    return binSearch(key, a, 0, a.length-1);
  }
  
  /**
   * Find a key of Comparable type in an array of Comparable type using a recursive binary search.
   * The array must be sorted.
   * @param a the array searched
   * @param key what is searched for
   * @return the index of the key in a, or -1 if the key is not present
   */
  public static <T extends Comparable<T>> int binarySearchRecursive(T[] a, T key) {
    return binSearch(key, a, 0, a.length-1);
  }
  
  public static int binarySearch(int[] a, int key) {
    int lo = 0;
    int hi = a.length-1;
    while (lo <= hi) {
      int m = (lo+hi) / 2;
      if (key == a[m]) return m;
      if (key < a[m]) hi = m-1;
      else            lo = m+1;
    }
    return -1;
  }
  
  /*** Private Methods ***/
  
  /**
   * This helper function does the real work of recursive binary search. It is needed
   * to pass in the bounds of the unsearched portion of the array.
   * @param key what is searched for
   * @param a the array searched
   * @param lo lo index of the unsearched portion of the array
   * @param hi hi index of the unsearched portion of the array
   * @return the index of the key in a, or -1 if the key is not present
   */
  private static int binSearch(int key, int[] a, int lo, int hi) {
    if (hi < lo) return -1;
    int m = (lo+hi) / 2;
    if (key == a[m]) return m;
    if (key < a[m]) return binSearch(key, a, lo, m-1);
    return binSearch(key, a, m+1, hi);
  }
  
  /**
   * This helper function does the real work of recursive binary search. It is needed
   * to pass in the bounds of the unsearched portion of the array.
   * @param key what is searched for
   * @param a the array searched
   * @param lo lo index of the unsearched portion of the array
   * @param hi hi index of the unsearched portion of the array
   * @return the index of the key in a, or -1 if the key is not present
   */
  private static <T extends Comparable<T>> int binSearch(T key, T[] a, int lo, int hi) {
    if (hi < lo) return -1;
    int m = (lo+hi) / 2;
    int comparison = key.compareTo(a[m]);
    if (comparison == 0) return m;
    if (comparison < 0) return binSearch(key, a, lo, m-1);
    return binSearch(key, a, m+1, hi);
  }
  
}
