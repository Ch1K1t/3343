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
import CouponRedeemSystem.System.Util.Util;
import java.util.Date;
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
    System.out.println("1. Check Coupon");
    System.out.println("2. Create Purchasable Coupon");
    System.out.println("3. Delete Coupon");
    System.out.println("4. Check Discount");
    System.out.println("5. Create Discount By Day");
    System.out.println("6. Delete Discount");
    System.out.println("7. Signout");
    System.out.println("8. Exit");
    System.out.println();
  }

  public void checkCoupon() {
    ShopManager shopManager = ShopManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    Shop shop = shopManager.getShopByStaff(account.getUserName());

    boolean noCoupon = true;
    List<String> couponList = shop.getPurchasableCouponList();
    for (String s : couponList) {
      Coupon coupon = couponManager.getCoupon(s);
      if (coupon.getExpirationDate().before(Util.today)) continue;
      noCoupon = false;
      System.out.println("Coupon Code: " + coupon.getCouponCode());
      System.out.println(
        "Coupon Intrinsic Value: " + coupon.getIntrinsicValue()
      );
      System.out.println("Coupon Points: " + coupon.getPoints());
      System.out.println(
        "Coupon Expiration Date: " + Util.sdf.format(coupon.getExpirationDate())
      );
    }

    if (noCoupon) {
      System.out.println("There is no coupon");
    }
  }

  public void createPurchasableCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    double intrinsicValue = doubleInput("coupon's intrinsic value");

    String expirationDate = afterDateInput("coupon's expiration date");

    String couponCode = strInput("coupon's code");

    ShopManager shopManager = ShopManager.getInstance();

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addPurchasableCoupon(couponCode);
    shopManager.updateShop(shop);

    double points = doubleInput("coupon's purchasing value");

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      "Purchasable",
      expirationDate
    );

    System.out.println();
    System.out.println("Coupon created");
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

  public void checkDiscount() {
    ShopManager shopManager = ShopManager.getInstance();
    DiscountManager discountManager = DiscountManager.getInstance();

    Shop shop = shopManager.getShopByStaff(account.getUserName());

    boolean noDiscount = true;
    List<String> discountList = shop.getDiscountList();
    for (String s : discountList) {
      Discount discount = discountManager.getDiscount(s);
      if (!discount.validateTime()) continue;
      noDiscount = false;
      System.out.println("Discount Name: " + discount.getDiscountName());
      System.out.println("Discount Value: " + discount.getValue());
      System.out.println(
        "Discount Start Date: " + Util.sdf.format(discount.getStartDate())
      );
      System.out.println(
        "Discount Expire Date: " + Util.sdf.format(discount.getExpireDate())
      );
      System.out.println();
    }

    if (noDiscount) {
      System.out.println("There is no discount");
    }
  }

  public void createDiscount() {
    ShopManager shopManager = ShopManager.getInstance();
    DiscountManager discountManager = DiscountManager.getInstance();

    String discountName = strInput("discount's name");

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addDiscount(discountName);
    shopManager.updateShop(shop);

    double value = doubleInput("discount's value");

    String startDate = afterDateInput("discount's start date");

    int day = intInput("discount's duration in day");

    discountManager.createDiscount(discountName, shop, value, startDate, day);

    System.out.println();
    System.out.println("Discount created");
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
          checkCoupon();
          break;
        case "2":
          createPurchasableCoupon();
          break;
        case "3":
          deleteCoupon();
          break;
        case "4":
          checkDiscount();
          break;
        case "5":
          createDiscount();
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
