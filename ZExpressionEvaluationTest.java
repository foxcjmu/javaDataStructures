import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class ZExpressionEvaluationTest
{

  @Test
  public void testEvalPrefixRecursive() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalPrefixRecursive());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("+35"))).evalPrefixRecursive());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("*+352"))).evalPrefixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("/*+52-757"))).evalPrefixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("-+1+2+34+++2222"))).evalPrefixRecursive());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("+1++2-52+23"))).evalPrefixRecursive());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixRecursiveNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("*3"))).evalPrefixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixRecursiveExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("*340"))).evalPrefixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixRecursiveNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("$30"))).evalPrefixRecursive();
  }

  @Test
  public void testEvalPrefixStack() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalPrefixStack());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("+35"))).evalPrefixStack());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("*+352"))).evalPrefixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("/*+52-757"))).evalPrefixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("-+1+2+34+++2222"))).evalPrefixStack());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("+1++2-52+23"))).evalPrefixStack());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixStackNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("*3"))).evalPrefixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixStackExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("*340"))).evalPrefixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPrefixStackNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("$30"))).evalPrefixStack();
  }

  @Test
  public void testEvalPostfixStack() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalPostfixStack());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("35+"))).evalPostfixStack());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("35+2*"))).evalPostfixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("52+75-*7/"))).evalPostfixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("1234+++22+2+2+-"))).evalPostfixStack());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("1252-+23+++"))).evalPostfixStack());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixStackNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*"))).evalPostfixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixStackExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("340*"))).evalPostfixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixStackNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("30$"))).evalPostfixStack();
  }

  @Test
  public void testEvalPostfixRecursive() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalPostfixRecursive());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("35+"))).evalPostfixRecursive());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("35+2*"))).evalPostfixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("52+75-*7/"))).evalPostfixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("1234+++22+2+2+-"))).evalPostfixRecursive());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("1252-+23+++"))).evalPostfixRecursive());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixRecursiveNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*"))).evalPostfixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixRecursiveExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("340*"))).evalPostfixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalPostfixRecursiveNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("30$"))).evalPostfixRecursive();
  }

  @Test
  public void testEvalInfixRecursive() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalInfixRecursive());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("3+5"))).evalInfixRecursive());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("3+5*2"))).evalInfixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("5+2*(7-5)/7"))).evalInfixRecursive());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("1+2+3+4-(2+2+2+2)"))).evalInfixRecursive());
      assertEquals(3, (new ExpressionEvaluation(new StringReader("((1)+(2))"))).evalInfixRecursive());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("1+((2+(5-2))+(2+3))"))).evalInfixRecursive());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixRecursiveNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*"))).evalInfixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixRecursiveExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*40"))).evalInfixRecursive();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixRecursiveNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("3$0"))).evalInfixRecursive();
  }

  @Test
  public void testEvalInfixStack() {
    try {
      assertEquals(3, (new ExpressionEvaluation(new StringReader("3"))).evalInfixStack());
      assertEquals(8, (new ExpressionEvaluation(new StringReader("3+5"))).evalInfixStack());
      assertEquals(16, (new ExpressionEvaluation(new StringReader("3+5*2"))).evalInfixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("5+2*(7-5)/7"))).evalInfixStack());
      assertEquals(2, (new ExpressionEvaluation(new StringReader("1+2+3+4-(2+2+2+2)"))).evalInfixStack());
      assertEquals(3, (new ExpressionEvaluation(new StringReader("((1)+(2))"))).evalInfixStack());
      assertEquals(11, (new ExpressionEvaluation(new StringReader("1+((2+(5-2))+(2+3))"))).evalInfixStack());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixStackNoParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*"))).evalInfixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixStackExtraParam() throws Exception {
    (new ExpressionEvaluation(new StringReader("3*40"))).evalInfixStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvalInfixStackNoop() throws Exception {
    (new ExpressionEvaluation(new StringReader("3$0"))).evalInfixStack();
  }
}