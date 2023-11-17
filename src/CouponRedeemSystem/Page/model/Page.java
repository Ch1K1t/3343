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
    s.close();
    System.exit(0);
  }
}
