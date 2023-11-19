package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class AdminPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Admin Account");
    System.out.println("2. Create Shop Account");
    System.out.println("3. Create User Account");
    System.out.println("4. Signout");
    System.out.println("5. Exit");
    System.out.println();
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "1":
          createAccount("admin");
          break;
        case "2":
          createAccount("shop");
          break;
        case "3":
          createAccount("user");
          break;
        case "4":
          System.out.println("Signout successfully");
          break;
        case "5":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("4"));
  }
}
