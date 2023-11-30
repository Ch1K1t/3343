package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Signin");
    System.out.println("2. Signup");
    System.out.println("3. Exit");
    System.out.println();
  }

  public void execute() {
    // Replace switch statement to improve readability
    System.out.println("Welcome to Coupon Redeem System");
    String cmd;
    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("1", () -> new SigninPage().execute());
    cmdMap.put("2", () -> createAccount("User"));
    cmdMap.put("3", this::exit);
    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();
      cmdExecute(cmdMap, cmd);
    } while (true);
  }
}
