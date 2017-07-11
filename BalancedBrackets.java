import java.io.IOException;
import java.io.Reader;

import containers.LinkedStack;
import containers.Stack;

public class BalancedBrackets {
  private static final int EOS = -1;
  private Reader source;
  private int ch;           // the current input character

  public BalancedBrackets(Reader src) {
    source = src;
    try {
      ch = source.read();
    } catch (IOException e) {
      ch = EOS;
    }
  }

  public boolean isBalancedRecursive() {
    return isBalanced() && ch == EOS;
  }
  
  private boolean isBalanced() {
    try {
      if (ch == EOS) return true;
      if (ch != '[') return false;
      ch = source.read();
      if (ch == '[')
      if (!isBalanced()) return false;
      if (ch != ']') return false;
      ch = source.read();
      if (ch == '[') return isBalanced();
      return true;
    } catch (IOException e) {
      return false;
    }
  }
  
  public boolean isBalancedStack() {
    Stack<Integer> s = new LinkedStack<Integer>();
    try {
      while (ch != EOS) {
        switch (ch) {
        case '[':
          s.push(ch);
          break;
        case ']':
          if (s.isEmpty()) return false;
          s.pop();
          break;
        default: return false;
        }
        ch = source.read();
      }
      return s.isEmpty();
    } catch (IOException e) {
      return false;
    }
  }
}
