package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.Password.PasswordManager;

public class SigninPage extends Page {

  public Account signin() {
    PasswordManager passwordManager = PasswordManager.getInstance();
    AccountManager accountManager = AccountManager.getInstance();

    String userName = strInput("user name");
    String password = strInput("password");

    switch (passwordManager.checkPasswordValid(userName, password)) {
      case "not found":
        System.out.println();
        System.out.println("Account is not found!");
        return null;
      case "not correct":
        System.out.println();
        System.out.println("Password is not correct!");
        return null;
      case "success":
        System.out.println();
        System.out.println("Signin successfully");
        Account account = accountManager.getAccount(userName);
        return account;
      default:
        return null;
    }
  }

  public void execute() {
    Account account = signin();
    if (account == null) return;

    String role = account.getRole();

    switch (role) {
      case "User":
        new UserPage(account.getUserName()).execute();
        break;
      case "Admin":
        new AdminPage().execute();
        break;
      case "Shop Manager":
        new ShopManagerPage().execute();
        break;
      case "Staff":
        new StaffPage(account.getUserName()).execute();
        break;
    }
  }
}
