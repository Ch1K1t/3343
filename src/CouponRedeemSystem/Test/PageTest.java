package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.SigninPage;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class PageTest extends MainTest {

  private final Shop shop = shopManager.createShop(shopName);

  @Rule
  public TextFromStandardInputStream systemIn = TextFromStandardInputStream.emptyStandardInputStream();

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @After
  public void reset() {
    Page.setS(null);
    if (
      passwordManager.checkPasswordValid(userName, password).equals("success")
    ) {
      passwordManager.deletePassword(userName);
    }
    Account account = accountManager.getAccount(userName);
    if (account != null) {
      accountManager.deleteAccount(account);
    }
    Shop shop = shopManager.getShop(shopName);
    if (shop != null) {
      shopManager.deleteShop(shop);
    }
  }

  @Test
  public void strInputTest() {
    String input = "test";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.strInput(fieldName);
    String expectedOutput = "\r\nPlease input the " + fieldName + ":\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void strInputMultipleTest() {
    String input = "test";
    systemIn.provideLines("", input);

    Page page = new HomePage();
    String result = page.strInput(fieldName);
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
    String input = "1";
    systemIn.provideLines(input);

    Page page = new HomePage();
    int result = page.intInput(fieldName);
    String expectedOutput = "\r\nPlease input the " + fieldName + ":\r\n";

    Assert.assertEquals(Integer.parseInt(input), result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void intInputMultipleTest() {
    String input = "a\r\n1";
    systemIn.provideLines(input);

    Page page = new HomePage();
    int result = page.intInput(fieldName);
    String expectedOutput =
      "\r\nPlease input the " +
      fieldName +
      ":\r\nInvalid value, please input again:\r\n";

    Assert.assertEquals(1, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void doubleInputTest() {
    String input = "1.0";
    systemIn.provideLines(input);

    Page page = new HomePage();
    double result = page.doubleInput(fieldName);
    String expectedOutput = "\r\nPlease input the " + fieldName + ":\r\n";

    Assert.assertEquals(Double.parseDouble(input), result, 0.0);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void doubleInputMultipleTest() {
    String input = "a\r\n1.0";
    systemIn.provideLines(input);

    Page page = new HomePage();
    double result = page.doubleInput(fieldName);
    String expectedOutput =
      "\r\nPlease input the " +
      fieldName +
      ":\r\nInvalid value, please input again:\r\n";

    Assert.assertEquals(1.0, result, 0.0);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void telInputTest() {
    String input = "12345678";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.telInput();
    String expectedOutput = "\r\nPlease input telephone number:\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void telInputMultipleTest() {
    String input = "a\r\n12345678";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.telInput();
    String expectedOutput =
      "\r\nPlease input telephone number:\r\nPlease input a 8 digit telephone number:\r\n";

    Assert.assertEquals("12345678", result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void beforeDateInputTest() {
    String input = "01/01/2000";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.beforeDateInput(fieldName);
    String expectedOutput =
      "\r\nPlease input " + fieldName + " (dd/MM/yyyy):\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void beforeDateInputMultipleTest() {
    String input = "a\r\n01/01/2100\r\n01/01/2000";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.beforeDateInput(fieldName);
    String expectedOutput =
      "\r\nPlease input " +
      fieldName +
      " (dd/MM/yyyy):\r\nInvalid date format, please input again:\r\nDate must be before today, please input again:\r\n";

    Assert.assertEquals("01/01/2000", result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void afterDateInputTest() {
    String input = "01/01/2100";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.afterDateInput(fieldName);
    String expectedOutput =
      "\r\nPlease input " + fieldName + " (dd/MM/yyyy):\r\n";

    Assert.assertEquals(input, result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void afterDateInputMultipleTest() {
    String input = "a\r\n01/01/2000\r\n01/01/2100";
    systemIn.provideLines(input);

    Page page = new HomePage();
    String result = page.afterDateInput(fieldName);
    String expectedOutput =
      "\r\nPlease input " +
      fieldName +
      " (dd/MM/yyyy):\r\nInvalid date format, please input again:\r\nDate must be after today, please input again:\r\n";

    Assert.assertEquals("01/01/2100", result);
    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create User account
  @Test
  public void createAccountTest() {
    systemIn.provideLines(userName, password, age, telNo, dateOfBirth);

    Page page = new HomePage();
    page.createAccount("User");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nPlease input the age:\r\n" +
      "\r\nPlease input telephone number:\r\n" +
      "\r\nPlease input date of birth (dd/MM/yyyy):\r\n" +
      "\r\nAccount created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create Staff account with correct shop name
  @Test
  public void createAccountTest2() {
    systemIn.provideLines(userName, password, shopName);

    Page page = new HomePage();
    page.createAccount("Staff");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nPlease input the shop name:\r\n" +
      "\r\nAccount created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create Staff account with incorrect shop name
  @Test
  public void createAccountTest3() {
    systemIn.provideLines(userName, password, "incorrectShop", shopName);

    Page page = new HomePage();
    page.createAccount("Staff");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nPlease input the shop name:\r\n" +
      "\r\nShop incorrectShop does not exist!\r\n" +
      "\r\nPlease input the shop name:\r\n" +
      "\r\nAccount created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create Admin account
  @Test
  public void createAccountTest4() {
    systemIn.provideLines(userName, password);

    Page page = new HomePage();
    page.createAccount("Admin");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nAccount created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create Shop Manager account
  @Test
  public void createAccountTest5() {
    systemIn.provideLines(userName, password);

    Page page = new HomePage();
    page.createAccount("Shop Manager");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nAccount created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  // create existing account
  @Test
  public void createAccountTestFail() {
    passwordManager.createNewPassword(userName, password);
    systemIn.provideLines(userName, password);

    Page page = new HomePage();
    page.createAccount("User");
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nUser already exists\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void signinTest() {
    passwordManager.createNewPassword(userName, password);
    systemIn.provideLines(userName, password);

    SigninPage page = new SigninPage();
    page.signin();
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nSignin successfully\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void signinTestFail() {
    systemIn.provideLines(userName, password);

    SigninPage page = new SigninPage();
    page.signin();
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nAccount is not found!\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void signinTestFail2() {
    passwordManager.createNewPassword(userName, password);
    systemIn.provideLines(userName, "incorrectPassword");

    SigninPage page = new SigninPage();
    page.signin();
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" +
      "\r\nPlease input the password:\r\n" +
      "\r\nPassword is not correct!\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void deleteAccountTest() {
    accountManager.createAccount(userName, "Admin");
    systemIn.provideLines(userName);

    AdminPage page = new AdminPage();
    page.deleteAccount();
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" + "\r\nAccount deleted\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void deleteAccountTestFail() {
    systemIn.provideLines(userName);

    AdminPage page = new AdminPage();
    page.deleteAccount();
    String expectedOutput =
      "\r\nPlease input the user name:\r\n" + "\r\nAccount not found\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }

  @Test
  public void createRedeemableCouponTest() {
    systemIn.provideLines("couponCode", "10.0", "11/11/2025");

    AdminPage page = new AdminPage();
    page.createRedeemableCoupon();
    String expectedOutput =
      "\r\nPlease input the coupon's code:\r\n" +
      "\r\nPlease input the coupon's intrinsic value:\r\n" +
      "\r\nPlease input coupon's expiration date (dd/MM/yyyy):\r\n" +
      "\r\nCoupon created\r\n";

    Assert.assertEquals(expectedOutput, systemOutRule.getLog());
  }
}
