package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.Password.PasswordManager;

public class SigninPage extends Page {

  public void execute() {
    System.out.println();
    System.out.println("Please input your username:");
    String username = s.nextLine();

    System.out.println();
    System.out.println("Please input your password:");
    String password = s.nextLine();

    PasswordManager passwordManager = PasswordManager.getInstance();
    if (!passwordManager.checkPasswordValid(username, password)) return;

    AccountManager accountManager = AccountManager.getInstance();
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
        new StaffPage().execute();
        break;
    }
  }
}
