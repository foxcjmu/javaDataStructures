package containers;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class ZHashtableTest
{

  @Test
  public void testConstructors() {
    Hashtable<String,Integer> t = new Hashtable<>();
    assertEquals(Hashtable.DEFAULT_SIZE, t.tableSize());
    t = new Hashtable<>(0);
    assertEquals(Hashtable.DEFAULT_SIZE, t.tableSize());
    t = new Hashtable<>(6);
    assertEquals(7, t.tableSize());
    t = new Hashtable<>(4436);
    assertEquals(4441, t.tableSize());
    assertEquals(0, t.size());
  }

  @Test
  public void testInsertAndGetAndClear() {
    Hashtable<String,Integer> t = new Hashtable<>(5);
    assertEquals(null, t.get("blue"));
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("six", 666);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    
    assertEquals(15, t.size());
    assertEquals(1, t.get("one").intValue());
    assertEquals(6, t.get("six").intValue());
    assertEquals(15, t.get("fifteen").intValue());
    assertEquals(4, t.get("four").intValue());
    assertEquals(11, t.get("eleven").intValue());
    assertEquals(null, t.get("red"));
    
    t.clear();
    assertEquals(0,  t.size());
    assertEquals(null, t.get("one"));
    assertEquals(null, t.get("six"));
    assertEquals(null, t.get("fifteen"));
  }

  @Test
  public void testDelete() {
    Hashtable<String,Integer> t = new Hashtable<>();
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    
    t.delete("six");
    assertEquals(null, t.get("six"));
    assertEquals(14, t.size());
    t.delete("six");
    assertEquals(null, t.get("six"));
    assertEquals(14, t.size());
    t.delete("fifteen");
    assertEquals(null, t.get("fifteen"));
    assertEquals(13, t.size());
    t.delete("five");
    assertEquals(null, t.get("five"));
    assertEquals(12, t.size());
    t.delete("one");
    assertEquals(null, t.get("five"));
    assertEquals(11, t.size());
    t.delete("two");
    assertEquals(null, t.get("five"));
    assertEquals(10, t.size());
    t.delete("three");
    assertEquals(null, t.get("five"));
    assertEquals(9, t.size());
    t.delete("four");
    assertEquals(null, t.get("five"));
    assertEquals(8, t.size());
    t.delete("seven");
    assertEquals(null, t.get("five"));
    assertEquals(7, t.size());
    t.delete("seven");
    assertEquals(null, t.get("five"));
    assertEquals(7, t.size());

    assertEquals(8, t.get("eight").intValue());
    assertEquals(9, t.get("nine").intValue());
    assertEquals(10, t.get("ten").intValue());
    assertEquals(11, t.get("eleven").intValue());
    assertEquals(12, t.get("twelve").intValue());
    assertEquals(13, t.get("thirteen").intValue());
    assertEquals(14, t.get("fourteen").intValue());
  }

  private class FillArray implements Visitor<Integer> {
    private boolean[] numbers = new boolean[15];
    public FillArray() {
      for (int i = 0; i < numbers.length; i++) numbers[i] = false;
    }

    public void mark(int i) { numbers[i] = true; }
    
    @Override
    public void visit(Integer value) { numbers[value.intValue()-1] = true; }
    
    public boolean isFilled() {
      for (int i = 0; i < numbers.length; i++) if (!numbers[i]) { return false; }
      return true;
    }
  }
    
  @Test
  public void testVisit() {
    FillArray filler = new FillArray();
    Hashtable<String,Integer> t = new Hashtable<>();
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    
    t.visit(filler);
    assertTrue(filler.isFilled());
  }

  @Test
  public void testIterators() {
    FillArray filler = new FillArray();
    Hashtable<String,Integer> t = new Hashtable<>();
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    
    for (Integer i : t) { filler.mark(i-1); }
    assertTrue(filler.isFilled());

    filler = new FillArray();
    Iterator<String> iter = t.keyIterator();
    while (iter.hasNext()) filler.mark(t.get(iter.next()).intValue()-1);
    assertTrue(filler.isFilled());
  }

  @Test
  public void testCopy() {
    Hashtable<String,Integer> t = new Hashtable<>();
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    Hashtable<String,Integer> c = t.copy();
    assertEquals(t.tableSize(),c.tableSize());
    assertEquals(t.size(),c.size());
    FillArray filler = new FillArray();
    c.visit(filler);
    assertTrue(filler.isFilled());
  }
  
  @Test
  public void testTableExpansion() {
    Hashtable<String,Integer> t = new Hashtable<>(5);
    t.insert("one", 1);
    t.insert("two", 2);
    t.insert("three", 3);
    t.insert("four", 4);
    t.insert("five", 5);
    t.insert("seven", 7);
    t.insert("eight", 8);
    t.insert("nine", 9);
    t.insert("ten", 10);
    t.insert("six", 6);
    t.insert("eleven", 11);
    t.insert("twelve", 12);
    t.insert("thirteen", 13);
    t.insert("fourteen", 14);
    t.insert("fifteen", 15);
    t.insert("sixteen", 16);
    t.insert("seventeen", 17);
    t.insert("eighteen", 18);
    t.insert("nineteen", 19);
    t.insert("twenty", 20);
    t.insert("twenty-one", 21);
    t.insert("twenty-two", 22);
    t.insert("twenty-three", 23);
    t.insert("twenty-four", 24);
    t.insert("twenty-five", 25);
    t.insert("twenty-six", 26);
    assertEquals(26, t.size());
    assertEquals(29, t.tableSize());
    assertEquals(18, t.get("eighteen").intValue());
    assertEquals(24, t.get("twenty-four").intValue());
    assertEquals(20, t.get("twenty").intValue());
    assertEquals(26, t.get("twenty-six").intValue());
  }
}
