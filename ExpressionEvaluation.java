/**
 * Prefix, postfix, and infix expression evaluation using stacks or recursion.
 * 
 * @author C. Fox
 * @version 6/2016
 */
import java.io.IOException;
import java.io.Reader;

import containers.LinkedStack;
import containers.Stack;

/**
 * Container for infix, postfix, and prefix expression evaluation methods that also 
 * provides a framework for reading an input stream.
 *
 * The constructor accepts a Reader that is assumed to contain a single expression. The
 * evaluation methods evaluate the expression in this input stream and return its value
 * or raise and IOException or an IllegalArgumentExpression if the input expression is
 * ill-formed.
 * 
 * Well-formed expressions
 * - have values that are a single digit
 * - have operators that are one of +, *, -, or /
 * - only contain parentheses if infix expressions
 * - contain no other characters (in particular, no whitespace)
 */
public class ExpressionEvaluation
{
  private static final int EOS = -1;  // more informative than -1
  private Reader source;              // character stream holding an expression
  private int ch;                     // the current input character

  /**
   * Accept an input stream that will be evaluated as either an infix, postfix, or
   * prefix expression.
   * 
   * @param src contains the evaluated expression with no whitespace
   */
  public ExpressionEvaluation(Reader src) {
    source = src;
    try {
      ch = source.read();
    } catch (IOException e) {
      ch = EOS;
    }
  }

  /**********************************
    
   Prefix expressions have the form:
   exp -> digit | op exp exp
   op -> + | * | - | /

  ***********************************/ 

  /**
   * Evaluate the input stream as a prefix expression using a stack.
   * 
   * @return the result of a well-formed prefix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalPrefixStack() throws Exception {
    Stack<Integer> opStack = new LinkedStack<Integer>();
    Stack<Integer> argStack = new LinkedStack<Integer>();
    while (ch != EOS) {
      if (Character.isDigit(ch)) {
        int num = ch - '0';
        argStack.push(num);
        while (!opStack.isEmpty() && opStack.top() == 'v') {
          opStack.pop();
          if (opStack.isEmpty()) throw new IllegalArgumentException("Too few operators");
          int op = opStack.pop();
          if (argStack.isEmpty()) throw new IllegalArgumentException("Missing right argument");
          int arg2 = argStack.pop();
          if (argStack.isEmpty()) throw new IllegalArgumentException("Missing left argument");
          int arg1 = argStack.pop();
          argStack.push( applyOp(op, arg1, arg2) );
        }
        if (!opStack.isEmpty() && opStack.top() != 'v') opStack.push((int)'v');
      } else {
        opStack.push(ch);
      }
      ch = source.read();
    }
    if (!opStack.isEmpty()) throw new IllegalArgumentException("Too many operators");
    if (argStack.isEmpty()) throw new IllegalArgumentException("Too few arguments");
    int result = argStack.pop();
    if (!argStack.isEmpty()) throw new IllegalArgumentException("Too many arguments");
    return result;
  }

  /**
   * Evaluate the input stream as a prefix expression using recursion.
   * 
   * @return the result of a well-formed prefix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalPrefixRecursive() throws Exception {
    int result = evalPrefix();
    if (ch == EOS) return result;
    throw new IllegalArgumentException("Extra argument "+(char)ch);
  }
  
  private int evalPrefix() throws Exception {
    if (ch == EOS)
      throw new IllegalArgumentException("Missing argument");
    else if (Character.isDigit(ch)) {
      int number = ch - '0';
      ch = source.read();
      return number;
    } else {
      int op = ch;
      ch = source.read();
      return applyOp(op, evalPrefix(), evalPrefix());
    }
  }

  /**********************************
    
   Postfix expressions have the form:
   exp -> digit | exp exp op
   op -> + | * | - | /

  ***********************************/ 

  /**
   * Evaluate the input stream as a postfix expression using a stack.
   * 
   * @return the result of a well-formed postfix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalPostfixStack() throws Exception {
    Stack<Integer> stack = new LinkedStack<Integer>();
    while (ch != EOS) {
      if (Character.isDigit(ch)) stack.push(ch-'0');
      else {
        if (stack.isEmpty()) throw new IllegalArgumentException("Missing right argument");
        int arg2 = stack.pop();
        if (stack.isEmpty()) throw new IllegalArgumentException("Missing left argument");
        int arg1 = stack.pop();
        stack.push( applyOp(ch, arg1, arg2) );
      }
      ch = source.read();
    }
    if (stack.isEmpty()) throw new IllegalArgumentException("Too few arguments");
    int result = stack.pop();
    if (!stack.isEmpty()) throw new IllegalArgumentException("Too many arguments");
    return result;
  }

  /**
   * Evaluate the input stream as a postfix expression using recursion.
   * 
   * @return the result of a well-formed postfix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalPostfixRecursive() throws Exception {
    int result = evalPostfix();
    if (ch == EOS) return result;
    throw new IllegalArgumentException("Extra argument "+(char)ch);
  }
  
  private int evalPostfix() throws Exception {
    if (!Character.isDigit(ch)) throw new IllegalArgumentException("Missing argument");
    int arg1 = ch - '0';
    ch = source.read();
    while (Character.isDigit(ch)) {
      int arg2 = evalPostfix();
      if (!isOperator(ch)) throw new IllegalArgumentException("Misisng operator");
      arg1 = applyOp(ch, arg1, arg2);
      ch = source.read();
    }
    return arg1;
  }

  /***********************************
    
   Infix expressions have the form:
   exp -> digit | exp op exp | ( exp )
   op -> + | * | - | /
   
   All operators are left-associative
   and have the same precedence.

  ************************************/ 

  /**
   * Evaluate the input stream as an infix expression using a stack.
   * 
   * @return the result of a well-formed infix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalInfixStack() throws Exception {
    Stack<Integer> opStack = new LinkedStack<Integer>();
    Stack<Integer> argStack = new LinkedStack<Integer>();
    
    if (ch == EOS) throw new IllegalArgumentException("Missing expression");
    while (ch != EOS) {
      if (isOperator(ch)) opStack.push(ch);
      else if (ch == '(') opStack.push((int)'(');
      else if (ch == ')') {
        if (opStack.isEmpty() || opStack.pop() != '(') throw new IllegalArgumentException("Extra right parenthesis");
        if (!opStack.isEmpty() && isOperator(opStack.top())) {
          if (argStack.size() < 2) throw new IllegalArgumentException("Missing argument");
          int arg2 = argStack.pop();
          argStack.push( applyOp(opStack.pop(),argStack.pop(),arg2) );
        }
      }
      else if (Character.isDigit(ch)) {
        if (!opStack.isEmpty() && isOperator(opStack.top())) {
          if (argStack.isEmpty()) throw new IllegalArgumentException("Missing argument");
          argStack.push( applyOp(opStack.pop(), argStack.pop(), ch-'0') );
        } else argStack.push(ch-'0');
      }
      else throw new IllegalArgumentException("Missing argument");
      ch = source.read();
    }
    if (!opStack.isEmpty()) throw new IllegalArgumentException("Too many operators");
    if (argStack.isEmpty()) throw new IllegalArgumentException("Missing arguments");
    int result = argStack.pop();
    if (!argStack.isEmpty()) throw new IllegalArgumentException("Too many arguments");
    return result;
  }

  /**
   * Evaluate the input stream as an infix expression using recursion.
   * 
   * @return the result of a well-formed infix expression
   * @throws IOException if there is a problem with the stream or
   *         IllegalArgumentException if the expression is ill-formed
   */
  public int evalInfixRecursive() throws Exception {
    int result = evalInfix();
    if (ch == EOS) return result;
    throw new IllegalArgumentException("Extra argument "+(char)ch);
  }
  
  private int evalInfix() throws Exception {
    int accumulator = evalTerm();
    while (isOperator(ch)) {
      int op = ch;
      ch = source.read();
      accumulator = applyOp(op, accumulator, evalTerm());
    }
    return accumulator;
  }
  
  private int evalTerm() throws Exception {
    if (!Character.isDigit(ch) && ch != '(') throw new IllegalArgumentException("Missing argument");
    int result;
    if (Character.isDigit(ch)) result = ch - '0';
    else { // parenthesized expression
      ch = source.read();
      result = evalInfix();
      if (ch != ')') throw new IllegalArgumentException("Missing right parenthesis "+ (char)ch);
    }
    ch = source.read();
    return result;
  }

  private boolean isOperator(int ch) {
    return (ch == '+' || ch == '-' || ch == '*' || ch == '/');
  }

  private int applyOp(int op, int arg1, int arg2) throws Exception {
    switch (op) {
    case '+': return arg1 + arg2;
    case '-': return arg1 - arg2;
    case '*': return arg1 * arg2;
    case '/': return arg1 / arg2;
    default: throw new IllegalArgumentException("Unknown symbol " + (char)op);
    }
  }
}
