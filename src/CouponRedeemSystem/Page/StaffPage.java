package CouponRedeemSystem.Page;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.model.Shop;

public class StaffPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Purchasable Coupon");
    System.out.println("2. Create Redeemable Coupon");
    System.out.println("3. Delete Coupon");
    System.out.println("4. Signout");
    System.out.println("5. Exit");
    System.out.println();
  }

  public void createCoupon(String type) {
    CouponManager couponManager = CouponManager.getInstance();

    double intrinsicValue = doubleInput("coupon's intrinsic value");

    String expirationDate = afterDateInput("coupon's expiration date");

    String couponCode = strInput("coupon's code");

    if (type.equals("Purchasable")) {
      // Search shop by coupon code
      Shop shop = null;

      double points = doubleInput("coupon's purchasing value");

      couponManager.create(
        couponCode,
        intrinsicValue,
        expirationDate,
        shop,
        points,
        type
      );
    } else {
      couponManager.create(couponCode, intrinsicValue, expirationDate, type);
    }
  }

  public void deleteCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    String couponCode = strInput("coupon's code");

    couponManager.delete(couponCode);
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine();
      switch (cmd) {
        case "1":
          createCoupon("Purchasable");
          break;
        case "2":
          createCoupon("Redeemable");
          break;
        case "3":
          deleteCoupon();
          break;
        case "4":
          System.out.println("Signout successfully");
          break;
        case "5":
          exit();
          break;
        default:
          System.out.println("Invalid command, please input again:");
          break;
      }
    } while (!cmd.equals("4"));
  }
}
