package CouponRedeemSystem.Page;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.model.Shop;
import java.util.Date;
import java.util.regex.Pattern;

public class CouponManagerPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please input the command e.g. !CreatePurchasableCoupon:");
    System.out.println("1. CreatePurchasableCoupon");
    System.out.println("2. CreateRedeemableCoupon");
    System.out.println("3. DeleteCoupon");
    System.out.println("4. Signout");
    System.out.println("5. Exit");
    System.out.println();
  }

  public void createCoupon(String type) {
    System.out.println("Please input the coupon's intrinsic value:");

    String value;
    boolean isDouble;
    do {
      value = s.nextLine();
      isDouble = Pattern.matches("[\\d.]+", value);
      if (!isDouble) {
        System.out.println("Invalid value, please input again:");
      }
    } while (!isDouble);
    double intrinsicValue = Double.parseDouble(value);

    System.out.println("Please input the coupon's shop:");
    Shop shop = null;

    Date expirationDate = new Date();

    System.out.println("Please input the coupon's code:");
    String couponCode = s.nextLine();

    PurchasableCoupon coupon = new PurchasableCoupon(
      intrinsicValue,
      shop,
      expirationDate,
      couponCode,
      true
    );

    CouponManager couponManager = CouponManager.getInstance();
    couponManager.create(coupon, type);
  }

  public Coupon searchCoupon() {
    System.out.println("Please input the coupon's code:");
    String couponCode = s.nextLine();
    Coupon coupon = null;
    return coupon;
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "!CreatePurchasableCoupon":
          createCoupon("Purchasable");
          break;
        case "!CreateRedeemableCoupon":
          createCoupon("Redeemable");
          break;
        case "!deletecoupon":
          System.out.println("Coupon deleted");
          break;
        case "!signout":
          System.out.println("Signout successfully");
          break;
        case "!exit":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("!signout"));
  }
}
