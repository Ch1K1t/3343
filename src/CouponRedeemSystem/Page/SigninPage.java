package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.util.HashMap;
import java.util.Map;

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
    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("User", () -> new UserPage(account.getUserName()).execute());
    cmdMap.put("Admin", () -> new AdminPage().execute());
    cmdMap.put("Shop Manager", () -> new ShopManagerPage().execute());
    cmdMap.put("Staff", () -> new StaffPage(account.getUserName()).execute());

    Runnable command = cmdMap.get(role);
    if (command != null) {
      command.run();
    } else {
      System.out.println("Unknown role");
    }
  }
}
