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
import java.util.List;

public class StaffPage extends Page {

  private Account account;

  public StaffPage(String username) {
    AccountManager accountManager = AccountManager.getInstance();
    this.account = accountManager.getAccount(username);
  }

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Purchasable Coupon");
    System.out.println("2. Create Redeemable Coupon");
    System.out.println("3. Delete Coupon");
    System.out.println("4. Create Discount By Day");
    System.out.println("5. Create Discount By Month");
    System.out.println("6. Delete Discount");
    System.out.println("7. Signout");
    System.out.println("8. Exit");
    System.out.println();
  }

  public void createCoupon(String type) {
    CouponManager couponManager = CouponManager.getInstance();

    double intrinsicValue = doubleInput("coupon's intrinsic value");

    String expirationDate = afterDateInput("coupon's expiration date");

    String couponCode = strInput("coupon's code");

    if (type.equals("Purchasable")) {
      ShopManager shopManager = ShopManager.getInstance();

      Shop shop = shopManager.getShopByStaff(account.getUserName());
      shop.addPurchasableCoupon(couponCode);
      shopManager.updateShop(shop);

      double points = doubleInput("coupon's purchasing value");

      Coupon coupon = couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        shop,
        points,
        type
      );

      if (coupon != null) {
        System.out.println();
        System.out.println("Coupon created");
      } else {
        System.out.println();
        System.out.println("Coupon creation failed");
      }
    } else {
      Coupon coupon = couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        type
      );

      if (coupon != null) {
        System.out.println();
        System.out.println("Coupon created");
      } else {
        System.out.println();
        System.out.println("Coupon creation failed");
      }
    }
  }

  public void deleteCoupon() {
    CouponManager couponManager = CouponManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    String couponCode = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon == null) {
      System.out.println();
      System.out.println("Coupon not found");
      return;
    }

    Shop shop = coupon.getShop();
    shop.removePurchasableCoupon(couponCode);
    shopManager.updateShop(shop);

    boolean isDeleted = couponManager.deleteCoupon(coupon);

    if (isDeleted) {
      System.out.println();
      System.out.println("Coupon deleted");
    } else {
      System.out.println();
      System.out.println("Coupon deletion failed");
    }
  }

  public void createDiscount(String unit) {
    ShopManager shopManager = ShopManager.getInstance();
    DiscountManager discountManager = DiscountManager.getInstance();

    String discountName = strInput("discount's name");

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addDiscount(discountName);
    shopManager.updateShop(shop);

    double value = doubleInput("discount's value");

    String startDate = afterDateInput("discount's start date");

    Discount discount = null;
    if (unit.equals("day")) {
      int day = intInput("discount's duration in day");

      discount =
        discountManager.createDiscountByDay(
          discountName,
          shop,
          value,
          startDate,
          day
        );
    } else {
      int month = intInput("discount's duration in month");

      discount =
        discountManager.createDiscountByMonth(
          discountName,
          shop,
          value,
          startDate,
          month
        );
    }

    if (discount != null) {
      System.out.println();
      System.out.println("Discount created");
    } else {
      System.out.println();
      System.out.println("Discount creation failed");
    }
  }

  public void deleteDiscount() {
    DiscountManager discountManager = DiscountManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    String discountName = strInput("discount's name");
    Discount discount = discountManager.getDiscount(discountName);
    if (discount == null) {
      System.out.println();
      System.out.println("Discount not found");
      return;
    }

    Shop shop = discount.getShop();
    shop.removeDiscount(discountName);
    shopManager.updateShop(shop);

    boolean isDeleted = discountManager.deleteDiscount(discount);

    if (isDeleted) {
      System.out.println();
      System.out.println("Discount deleted");
    } else {
      System.out.println();
      System.out.println("Discount deletion failed");
    }
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
          createDiscount("day");
          break;
        case "5":
          createDiscount("month");
          break;
        case "6":
          deleteDiscount();
          break;
        case "7":
          System.out.println("Signout successfully");
          break;
        case "8":
          exit();
          break;
        default:
          System.out.println("Invalid command, please input again:");
          break;
      }
    } while (!cmd.equals("7"));
  }
}
