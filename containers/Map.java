package containers;

import java.util.Iterator;

/**
 * A Map holds key-value pairs. Maps are also called tables, dictionaries, or associative arrays.
 * @author C. Fox
 *
 * @param <K> the key type
 * @param <T> the value type
 */
public interface Map<K,T> extends Collection<T> {
  /**
   * Fetch the value associated with the key, or null if there is none.
   * @param key used for searching
   * @return the value associated with key, or null
   */
  T get(K key);
  
  /**
   * Add (or replace) a key-value pair in the map.
   * @param key the key searched for
   * @param value the value associated with the key
   */
  void insert(K key, T value);
  
  /**
   * Remove the key-value pair with the given key, or do nothing if the key is absent.
   * @param key the key searched for
   */
  void delete(K key);
  
  /**
   * Determine whether a key-value pair with the give key is in this map.
   * @param key the key searched for
   * @return true iff a pair with key is in the map
   */
  boolean hasKey(K key);

  /**
   * Return an iterator over the keys in the map.
   * @return an iterator over keys (not values)
   */
  Iterator<K> keyIterator();
  
  /**
   * Determine whether this map and a given map have exactly the same key-value pairs.
   * @param m the map compared
   * @return true iff this map has exactly the same keys and values as m
   */
  boolean isEqual(Map<K,T> m);
}
