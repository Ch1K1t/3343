package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.Password.PasswordManager;

public class SigninPage extends Page {

  public void execute() {
    PasswordManager passwordManager = PasswordManager.getInstance();
    AccountManager accountManager = AccountManager.getInstance();

    String username = strInput("user name");

    String password = strInput("password");

    if (!passwordManager.checkPasswordValid(username, password)) return;
    System.out.println();
    System.out.println("Signin successfully");

    Account account = accountManager.getAccount(username);
    String role = account.getRole();

    switch (role) {
      case "User":
        new UserPage(username).execute();
        break;
      case "Admin":
        new AdminPage().execute();
        break;
      case "Shop Manager":
        new ShopManagerPage().execute();
        break;
      case "Staff":
        new StaffPage(username).execute();
        break;
    }
  }
}
