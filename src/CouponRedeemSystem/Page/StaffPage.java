package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

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
      CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
      ShopManager shopManager = ShopManager.getInstance();

      Shop shop = null;
      File[] fileArr = new File("Data/Shop").listFiles();
      for (File file : fileArr) {
        JSONObject jsonObject = jsonFileManager.convertFileTextToJSON(file);
        if (jsonObject.getJSONArray("staffs").contains(account.getUserName())) {
          shop = shopManager.getShop(jsonObject.getString("shopName"));
          shop.addPurchasableCoupons(couponCode);
          shopManager.updateShop(shop);
          break;
        }
      }

      double points = doubleInput("coupon's purchasing value");

      couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        shop,
        points,
        type
      );
    } else {
      couponManager.createCoupon(
        couponCode,
        intrinsicValue,
        expirationDate,
        type
      );
    }
  }

  public void deleteCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    String couponCode = strInput("coupon's code");

    couponManager.deleteCoupon(couponCode);
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
