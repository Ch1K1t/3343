package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.io.IOException;
import java.text.ParseException;

public class SigninPage extends Page {

  public void execute() {
    try {
      System.out.println();
      System.out.println("Please input your username:");
      String username = s.nextLine();
      System.out.println();
      System.out.println("Please input your password:");
      String password = s.nextLine();

      PasswordManager passwordManager = PasswordManager.getInstance();
      boolean isValidAcc = passwordManager.checkPasswordValid(
        username,
        password
      );

      if (!isValidAcc) {
        System.out.println("Invalid username or password");
        return;
      }

      AccountManager accountManager = AccountManager.getInstance();
      Account account = accountManager.getAccount(username);
      String role = account.getRole();

      switch (role) {
        case "user":
          new UserPage(username).execute();
          break;
        case "admin":
          new AdminPage().execute();
          break;
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
}
