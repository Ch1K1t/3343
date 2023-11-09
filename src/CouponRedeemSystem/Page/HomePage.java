package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;
import java.util.Scanner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HomePage implements Page {

  public static void getInstruction() {
    System.out.println("Please input the command e.g. !login:");
    System.out.println("1. Signin");
    System.out.println("2. Signup");
    System.out.println("3. Exit");
    System.out.println();
  }

  @Override
  public void execute() {
    Scanner s = new Scanner(System.in);

    System.out.println("Welcome to Coupon Redeem System");
    String cmd;
    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "!signin":
          System.out.println("Signin");
          break;
        case "!signup":
          System.out.println("Signup");
          break;
        case "!exit":
          System.out.println("Exit");
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("!exit"));

    s.close();
  }
}
