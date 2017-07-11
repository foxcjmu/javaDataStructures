package containers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ZTreeSetTest {
  private TreeSet<Integer> emptySet;
  private TreeSet<Integer> singletonSet;
  private TreeSet<Integer> s1;
  private TreeSet<Integer> s2;
  private TreeSet<Integer> s3;
  

  @Before
  public void setUp() throws Exception {
    emptySet = new TreeSet<>();
    singletonSet = new TreeSet<>();
    singletonSet.insert(3);
    s1 = new TreeSet<>();
    s2 = new TreeSet<>();
    s3 = new TreeSet<>();
    s1.insert(1);
    s1.insert(2);
    s1.insert(3);
    s1.insert(4);
    s1.insert(5);
    s1.insert(6);
    s1.insert(7);
    s1.insert(8);
    s1.insert(9);
    s1.insert(10);
    s1.insert(11);
    s1.insert(12);
    s2.insert(2);
    s2.insert(3);
    s2.insert(5);
    s2.insert(7);
    s2.insert(11);
    s3.insert(2);
    s3.insert(4);
    s3.insert(6);
    s3.insert(8);
    s3.insert(10);
    s3.insert(12);
  }

  @Test
  public void testToString() {
    assertEquals("[]", emptySet.toString());
    assertEquals("[3]", singletonSet.toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s1.toString());
    assertEquals("[2,3,5,7,11]", s2.toString());
    assertEquals("[2,4,6,8,10,12]", s3.toString());
  }

  @Test
  public void testCollectionMethods() {
    assertTrue(emptySet.isEmpty());
    assertFalse(singletonSet.isEmpty());
    assertEquals(0, emptySet.size());
    assertEquals(1, singletonSet.size());
    assertEquals(12, s1.size());
    assertEquals(5, s2.size());
    assertEquals(6, s3.size());
    s3.clear();
    assertTrue(s3.isEmpty());
    assertEquals(0, s3.size());
    assertFalse(emptySet.contains(3));
    assertTrue(singletonSet.contains(3));
    assertFalse(singletonSet.contains(5));
    assertFalse(s1.contains(19));
    assertTrue(s1.contains(1));
    assertTrue(s1.contains(2));
    assertTrue(s1.contains(3));
    assertTrue(s1.contains(4));
    assertTrue(s1.contains(5));
    int i = 1;
    for (Integer v : s1) assertEquals(i++, v.intValue());
  }


  @Test
  public void TestRemove() {
    s1.delete(6);
    assertEquals("[1,2,3,4,5,7,8,9,10,11,12]", s1.toString());
    s1.delete(6);
    assertEquals("[1,2,3,4,5,7,8,9,10,11,12]", s1.toString());
    s1.delete(3);
    assertEquals("[1,2,4,5,7,8,9,10,11,12]", s1.toString());
    s1.delete(4);
    assertEquals("[1,2,5,7,8,9,10,11,12]", s1.toString());
    assertEquals(9, s1.size());
  }

  @Test
  public void TestSubset() {
    assertTrue(emptySet.isSubset(emptySet));
    assertTrue(emptySet.isSubset(singletonSet));
    assertTrue(emptySet.isSubset(s1));
    assertTrue(singletonSet.isSubset(s1));
    assertFalse(singletonSet.isSubset(emptySet));
    assertTrue(s2.isSubset(s1));
    assertTrue(s3.isSubset(s1));
    assertFalse(s1.isSubset(s2));
    assertFalse(s1.isSubset(s3));
  }

  @Test
  public void TestIsEqual() {
    assertTrue(emptySet.isEqual(emptySet));
    assertTrue(singletonSet.isEqual(singletonSet));
    assertTrue(s2.isEqual(s2));
    assertFalse(emptySet.isEqual(s1));
    assertFalse(s1.isEqual(emptySet));
    s1.delete(1);
    s1.delete(4);
    s1.delete(6);
    s1.delete(8);
    assertFalse(s2.isEqual(s1));
    assertFalse(s1.isEqual(s2));
    s1.delete(9);
    s1.delete(10);
    s1.delete(12);
    assertTrue(s2.isEqual(s1));
    assertTrue(s1.isEqual(s2));
  }

  @Test
  public void TestIntersection() {
    assertEquals("[]", emptySet.intersection(emptySet).toString());
    assertEquals("[]", emptySet.intersection(s1).toString());
    assertEquals("[]", s1.intersection(emptySet).toString());
    assertEquals("[3]", s1.intersection(singletonSet).toString());
    assertEquals("[3]", singletonSet.intersection(s1).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s1.intersection(s1).toString());
    assertEquals("[2,3,5,7,11]", s1.intersection(s2).toString());
    assertEquals("[2,3,5,7,11]", s2.intersection(s1).toString());
    assertEquals("[2]", s2.intersection(s3).toString());
    assertEquals("[2]", s3.intersection(s2).toString());
  }

  @Test
  public void TestUnion() {
    assertEquals("[]", emptySet.union(emptySet).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", emptySet.union(s1).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s1.union(emptySet).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s2.union(s1).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s1.union(s2).toString());
    assertEquals("[2,3,4,5,6,7,8,10,11,12]", s3.union(s2).toString());
    assertEquals("[2,3,4,5,6,7,8,10,11,12]", s2.union(s3).toString());
  }

  @Test
  public void TestComplement() {
    assertEquals("[]", emptySet.complement(emptySet).toString());
    assertEquals("[]", emptySet.complement(s1).toString());
    assertEquals("[1,2,3,4,5,6,7,8,9,10,11,12]", s1.complement(emptySet).toString());
    assertEquals("[]", s1.complement(s1).toString());
    assertEquals("[1,4,6,8,9,10,12]", s1.complement(s2).toString());
    assertEquals("[]", s2.complement(s1).toString());
    assertEquals("[1,3,5,7,9,11]", s1.complement(s3).toString());
    assertEquals("[3,5,7,11]", s2.complement(s3).toString());
  }
}