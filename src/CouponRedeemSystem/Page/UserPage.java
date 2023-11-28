package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import java.util.Date;
import java.util.List;

public class UserPage extends Page {

  private Account account;

  public UserPage(String username) {
    AccountManager accountManager = AccountManager.getInstance();
    this.account = accountManager.getAccount(username);
  }

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Check Remaining Points");
    System.out.println("2. Purchase Coupon");
    System.out.println("3. Redeem Coupon");
    System.out.println("4. Use Coupon");
    System.out.println("5. Signout");
    System.out.println("6. Exit");
    System.out.println();
  }

  public void checkRemainingPoints() {
    System.out.println();
    System.out.println("Your remaining points is: " + account.getPoints());
  }

  public void getPurchasableCouponList() {
    CouponManager couponManager = CouponManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    System.out.println();
    System.out.println("Your balance is " + account.getPoints());

    System.out.println();
    System.out.println("The available coupons are:");
    List<Shop> shopList = shopManager.getShopList();
    for (Shop shop : shopList) {
      boolean hasCouponToPurchase = false;
      List<String> couponList = shop.getPurchasableCouponList();
      for (String s : couponList) {
        Coupon coupon = couponManager.getCoupon(s);
        if (coupon.getExpirationDate().before(new Date())) continue;
        if (coupon.getOwner() != null) continue;
        hasCouponToPurchase = true;
        break;
      }
      if (!hasCouponToPurchase) continue;

      System.out.println("Shop " + shop.getShopName() + ":");
      int discountTotal = 0;
      List<String> discountList = shop.getDiscountList();
      for (String s : discountList) {
        DiscountManager discountManager = DiscountManager.getInstance();
        Discount discount = discountManager.getDiscount(s);
        if (discount.validateTime()) {
          discountTotal += discount.getValue();
        }
      }
      if (discountTotal > 0) {
        System.out.println(
          "This shop is holding a discount event, for each coupon, it will be discounted by " +
          discountTotal +
          " points"
        );
      }

      for (String s : couponList) {
        Coupon coupon = couponManager.getCoupon(s);
        if (coupon.getExpirationDate().before(new Date())) continue;
        if (coupon.getOwner() != null) continue;

        if (discountTotal > 0) {
          System.out.println(
            String.format("%-15s", "Code: " + coupon.getCouponCode()) +
            String.format("%-30s", "Original Points: " + coupon.getPoints()) +
            "After Discount: " +
            (
              coupon.getPoints() - discountTotal < 1
                ? 1
                : coupon.getPoints() - discountTotal
            )
          );
        } else {
          System.out.println(
            String.format("%-15s", "Code: " + coupon.getCouponCode()) +
            "Required Points: " +
            coupon.getPoints()
          );
        }
      }
      System.out.println();
    }
  }

  public void getResultMessage(boolean isSuccess, String action) {
    if (isSuccess) {
      System.out.println();
      System.out.println(action + " successfully");
    } else {
      System.out.println();
      System.out.println(action + " failed");
    }
  }

  public void purchaseCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    getPurchasableCouponList();

    String couponCode = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon == null || coupon.getOwner() != null) {
      System.out.println();
      System.out.println("Coupon not found");
      return;
    } else if (!coupon.getType().equals("Purchasable")) {
      System.out.println();
      System.out.println("This coupon is not purchasable");
      return;
    } else if (coupon.getPoints() > account.getPoints()) {
      System.out.println();
      System.out.println("Insufficient points");
      return;
    } else if (coupon.getExpirationDate().before(new Date())) {
      System.out.println();
      System.out.println("Coupon has expired");
      return;
    }

    boolean isSuccess = account.pointsToCoupon(coupon);
    getResultMessage(isSuccess, "Purchase");
  }

  public void redeemCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    String couponID = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponID);
    if (coupon == null) {
      System.out.println();
      System.out.println("Coupon not found");
      return;
    } else if (!coupon.isActive()) {
      System.out.println();
      System.out.println("Coupon has been used!");
      return;
    } else if (coupon.getExpirationDate().before(new Date())) {
      System.out.println();
      System.out.println("Coupon has expired!");
      return;
    }
    boolean isSuccess = account.couponToPoints(coupon);
    getResultMessage(isSuccess, "Redeem");
  }

  public boolean getOwnedCouponList() {
    CouponManager couponManager = CouponManager.getInstance();

    System.out.println();
    System.out.println("Your owned coupons are:");
    List<String> couponList = account.getCouponIDs();
    if (couponList.size() == 0) {
      System.out.println("You have no coupon");
      return false;
    }
    for (String s : couponList) {
      Coupon coupon = couponManager.getCoupon(s);
      if (coupon.getExpirationDate().before(new Date()) && coupon.isActive()) {
        continue;
      }

      System.out.println(
        String.format("%-15s", "Code: " + coupon.getCouponCode()) +
        "Intrinsic Value: " +
        coupon.getIntrinsicValue()
      );
    }
    return true;
  }

  public void useCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    if (!getOwnedCouponList()) {
      return;
    }

    String couponID = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponID);
    if (coupon == null) {
      System.out.println();
      System.out.println("Coupon not found");
      return;
    } else if (!coupon.isActive()) {
      System.out.println();
      System.out.println("Coupon has been used!");
      return;
    } else if (coupon.getExpirationDate().before(new Date())) {
      System.out.println();
      System.out.println("Coupon has expired!");
      return;
    }
    boolean isSuccess = account.useCoupon(coupon);
    getResultMessage(isSuccess, "Use");
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine();

      switch (cmd) {
        case "1":
          checkRemainingPoints();
          break;
        case "2":
          purchaseCoupon();
          break;
        case "3":
          redeemCoupon();
          break;
        case "4":
          useCoupon();
          break;
        case "5":
          System.out.println("Signout successfully");
          break;
        case "6":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("5"));
  }
}
