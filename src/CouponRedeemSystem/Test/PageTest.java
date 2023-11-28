package CouponRedeemSystem.Test;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Test.model.MainTest;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class PageTest extends MainTest {

  @Rule
  public TextFromStandardInputStream systemIn = TextFromStandardInputStream.emptyStandardInputStream();

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void strInputTest() {
    String fieldName = "test field";
    String input = "test";
    systemIn.provideLines(input);

    Page Page = new HomePage();
    String result = Page.strInput(fieldName);
    String expectedOutput = "\r\nPlease input the " + fieldName + ":\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
    System.setIn(System.in);
  }

  @Test
  public void strInputMultipleTest() {
    String fieldName = "test field";
    String input = "test";
    systemIn.provideLines("", input);

    Page Page = new HomePage();
    String result = Page.strInput(fieldName);
    String expectedOutput =
      "\r\nPlease input the " +
      fieldName +
      ":\r\nPlease input the " +
      fieldName +
      ":\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void intInputTest() {
    String fieldName = "test field";
    String input = "1";
    systemIn.provideLines(input);

    Page Page = new HomePage();
    int result = Page.intInput(fieldName);
    String expectedOutput = "\r\nPlease input the " + fieldName + ":\r\n";

    Assert.assertEquals(Integer.parseInt(input), result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void intInputMultipleTest() {
    String fieldName = "test field";
    String input = "1";
    systemIn.provideLines("", input);

    Page Page = new HomePage();
    int result = Page.intInput(fieldName);
    String expectedOutput =
      "\r\nPlease input the " +
      fieldName +
      ":\r\nInvalid value, please input again:\r\n";

    Assert.assertEquals(Integer.parseInt(input), result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }
}
