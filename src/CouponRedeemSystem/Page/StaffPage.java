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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    System.out.println("5. Create Discount");
    System.out.println("6. Delete Discount");
    System.out.println("7. Signout");
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
      System.out.println();
      System.out.println("Coupon Code: " + coupon.getCouponCode());
      System.out.println(
        "Coupon Intrinsic Value: " + coupon.getIntrinsicValue()
      );
      System.out.println(
        "Coupon Purchasing Value: " + coupon.getPurchasingValue()
      );
      System.out.println(
        "Coupon Expiration Date: " + Util.sdf.format(coupon.getExpirationDate())
      );
    }

    if (noCoupon) {
      System.out.println();
      System.out.println("There is no coupon");
    }
  }

  public void createPurchasableCoupon() {
    ShopManager shopManager = ShopManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    String couponCode = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon != null) {
      System.out.println();
      System.out.println("Coupon already exists");
      return;
    }

    double intrinsicValue = doubleInput("coupon's intrinsic value");
    double purchasingValue = doubleInput("coupon's purchasing value");

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addPurchasableCoupon(couponCode);
    shopManager.updateShop(shop);

    String expirationDate = afterDateInput("coupon's expiration date");

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      purchasingValue,
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

    couponManager.deleteCoupon(coupon);

    System.out.println();
    System.out.println("Coupon deleted");
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
      System.out.println();
      System.out.println("Discount Name: " + discount.getDiscountName());
      System.out.println("Discount Value: " + discount.getValue());
      System.out.println(
        "Discount Start Date: " + Util.sdf.format(discount.getStartDate())
      );
      System.out.println(
        "Discount End Date: " + Util.sdf.format(discount.getEndDate())
      );
    }

    if (noDiscount) {
      System.out.println();
      System.out.println("There is no discount");
    }
  }

  public void createDiscount() {
    ShopManager shopManager = ShopManager.getInstance();
    DiscountManager discountManager = DiscountManager.getInstance();

    String discountName = strInput("discount's name");
    Discount discount = discountManager.getDiscount(discountName);
    if (discount != null) {
      System.out.println();
      System.out.println("Discount already exists");
      return;
    }

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addDiscount(discountName);
    shopManager.updateShop(shop);

    double value = doubleInput("discount's value");

    String startDate = beforeDateInput("discount's start date");

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

    discountManager.deleteDiscount(discount);

    System.out.println();
    System.out.println("Discount deleted");
  }

  public void execute() {
    String cmd;
    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("1", () -> checkCoupon());
    cmdMap.put("2", () -> createPurchasableCoupon());
    cmdMap.put("3", () -> deleteCoupon());
    cmdMap.put("4", () -> checkDiscount());
    cmdMap.put("5", () -> createDiscount());
    cmdMap.put("6", () -> deleteDiscount());
    cmdMap.put("7", () -> System.out.println("Signout successfully"));
    cmdMap.put("8", this::exit);

    do {
      getInstruction();
      cmd = s.nextLine();
      cmdExecute(cmdMap, cmd);
    } while (!cmd.equals("7"));
  }
}
