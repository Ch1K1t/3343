package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;

public class AdminPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Admin Account");
    System.out.println("2. Create Shop Manager Account");
    System.out.println("3. Create Staff Account");
    System.out.println("4. Create User Account");
    System.out.println("5. Delete Account");
    System.out.println("6. Signout");
    System.out.println("7. Exit");
    System.out.println();
  }

  public void deleteAccount() {
    AccountManager accountManager = AccountManager.getInstance();

    String username = strInput("user name");
    Account account = accountManager.getAccount(username);
    if (account == null) {
      System.out.println();
      System.out.println("Account not found");
      return;
    }

    boolean isDeleted = accountManager.deleteAccount(account);
    if (isDeleted) {
      System.out.println();
      System.out.println("Account deleted");
    } else {
      System.out.println();
      System.out.println("Account deletion failed");
    }
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "1":
          createAccount("Admin");
          break;
        case "2":
          createAccount("Shop Manager");
          break;
        case "3":
          createAccount("Staff");
          break;
        case "4":
          createAccount("User");
          break;
        case "5":
          deleteAccount();
          break;
        case "6":
          System.out.println("Signout successfully");
          break;
        case "7":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("6"));
  }
}
