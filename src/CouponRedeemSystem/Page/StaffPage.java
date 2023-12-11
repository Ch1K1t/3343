package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Promotion.PromotionManager;
import CouponRedeemSystem.Promotion.model.Promotion;
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
    System.out.println("4. Check Promotion");
    System.out.println("5. Create Promotion");
    System.out.println("6. Delete Promotion");
    System.out.println("7. Signout");
    System.out.println();
  }

  public void checkCoupon() {
    ShopManager shopManager = ShopManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    Shop shop = shopManager.getShopByStaff(account.getUserName());

    boolean noCoupon = true;
    List<String> couponList = shop.getCouponList();
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
    shop.addCoupon(couponCode);
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
    shop.removeCoupon(couponCode);
    shopManager.updateShop(shop);

    couponManager.deleteCoupon(coupon);

    System.out.println();
    System.out.println("Coupon deleted");
  }

  public void checkPromotion() {
    ShopManager shopManager = ShopManager.getInstance();
    PromotionManager promotionManager = PromotionManager.getInstance();

    Shop shop = shopManager.getShopByStaff(account.getUserName());

    boolean noPromotion = true;
    List<String> promotionList = shop.getPromotionList();
    for (String s : promotionList) {
      Promotion promotion = promotionManager.getPromotion(s);
      if (!promotion.validateTime()) continue;
      noPromotion = false;
      System.out.println();
      System.out.println("Promotion Name: " + promotion.getPromotionName());
      System.out.println("Promotion Value: " + promotion.getValue());
      System.out.println(
        "Promotion Start Date: " + Util.sdf.format(promotion.getStartDate())
      );
      System.out.println(
        "Promotion End Date: " + Util.sdf.format(promotion.getEndDate())
      );
    }

    if (noPromotion) {
      System.out.println();
      System.out.println("There is no promotion");
    }
  }

  public void createPromotion() {
    ShopManager shopManager = ShopManager.getInstance();
    PromotionManager promotionManager = PromotionManager.getInstance();

    String promotionName = strInput("promotion's name");
    Promotion promotion = promotionManager.getPromotion(promotionName);
    if (promotion != null) {
      System.out.println();
      System.out.println("Promotion already exists");
      return;
    }

    Shop shop = shopManager.getShopByStaff(account.getUserName());
    shop.addPromotion(promotionName);
    shopManager.updateShop(shop);

    double value = doubleInput("promotion's value");

    String startDate = beforeDateInput("promotion's start date");

    int day = intInput("promotion's duration in day");

    promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    System.out.println();
    System.out.println("Promotion created");
  }

  public void deletePromotion() {
    PromotionManager promotionManager = PromotionManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    String promotionName = strInput("promotion's name");
    Promotion promotion = promotionManager.getPromotion(promotionName);
    if (promotion == null) {
      System.out.println();
      System.out.println("Promotion not found");
      return;
    }

    Shop shop = promotion.getShop();
    shop.removePromotion(promotionName);
    shopManager.updateShop(shop);

    promotionManager.deletePromotion(promotion);

    System.out.println();
    System.out.println("Promotion deleted");
  }

  public void execute() {
    String cmd;
    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("1", () -> checkCoupon());
    cmdMap.put("2", () -> createPurchasableCoupon());
    cmdMap.put("3", () -> deleteCoupon());
    cmdMap.put("4", () -> checkPromotion());
    cmdMap.put("5", () -> createPromotion());
    cmdMap.put("6", () -> deletePromotion());
    cmdMap.put("7", () -> System.out.println("Signout successfully"));

    do {
      getInstruction();
      cmd = s.nextLine();
      cmdExecute(cmdMap, cmd);
    } while (!cmd.equals("7"));
  }
}
