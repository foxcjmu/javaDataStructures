import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class ZBracketsTest
{

  @Test
  public void testBalancedBracketsRecursive()
  {
    assertTrue((new BalancedBrackets(new StringReader("")).isBalancedRecursive()));
    assertFalse((new BalancedBrackets(new StringReader("[")).isBalancedRecursive()));
    assertTrue((new BalancedBrackets(new StringReader("[]")).isBalancedRecursive()));
    assertTrue((new BalancedBrackets(new StringReader("[][]")).isBalancedRecursive()));
    assertTrue((new BalancedBrackets(new StringReader("[[][]]")).isBalancedRecursive()));
    assertFalse((new BalancedBrackets(new StringReader("[[]]]")).isBalancedRecursive()));
    assertTrue((new BalancedBrackets(new StringReader("[[[[][]][][]][]]")).isBalancedRecursive()));
    assertFalse((new BalancedBrackets(new StringReader("[[[][]][][]][]]")).isBalancedRecursive()));
  }

  @Test
  public void testBalancedBracketsStack()
  {
    assertTrue((new BalancedBrackets(new StringReader("")).isBalancedStack()));
    assertFalse((new BalancedBrackets(new StringReader("[")).isBalancedStack()));
    assertTrue((new BalancedBrackets(new StringReader("[]")).isBalancedStack()));
    assertTrue((new BalancedBrackets(new StringReader("[][]")).isBalancedStack()));
    assertTrue((new BalancedBrackets(new StringReader("[[][]]")).isBalancedStack()));
    assertFalse((new BalancedBrackets(new StringReader("[[]]]")).isBalancedStack()));
    assertTrue((new BalancedBrackets(new StringReader("[[[[][]][][]][]]")).isBalancedStack()));
    assertFalse((new BalancedBrackets(new StringReader("[[[][]][][]][]]")).isBalancedStack()));
  }

}
