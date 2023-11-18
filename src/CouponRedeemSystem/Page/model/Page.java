package CouponRedeemSystem.Page.model;

import java.util.Scanner;

public abstract class Page {

  protected static Scanner s;

  public Page() {
    if (s == null) {
      s = new Scanner(System.in);
    }
  }

  public void exit() {
    System.out.println("Thank you for using Coupon Redeem System");
    System.out.println("Goodbye");
    System.out.println();
    s.close();
    System.exit(0);
  }
}
