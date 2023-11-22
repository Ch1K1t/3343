package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
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
      ShopManager shopManager = ShopManager.getInstance();

      List<Shop> shopList = shopManager.getShopList();
      Shop shop = null;
      for (Shop s : shopList) {
        if (s.getStaffList().contains(account.getUserName())) {
          shop = s;
          shop.addPurchasableCoupon(couponCode);
          shopManager.updateShop(shop);
          break;
        }
      }

      double points = doubleInput("coupon's purchasing value");

      boolean isCreated = couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        shop,
        points,
        type
      );

      if (isCreated) {
        System.out.println();
        System.out.println("Coupon created");
      } else {
        System.out.println();
        System.out.println("Coupon creation failed");
      }
    } else {
      boolean isCreated = couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        type
      );

      if (isCreated) {
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

    String couponCode = strInput("coupon's code");

    boolean isDeleted = couponManager.deleteCoupon(couponCode);

    if (isDeleted) {
      System.out.println();
      System.out.println("Coupon deleted");
    } else {
      System.out.println();
      System.out.println("Coupon deletion failed");
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
