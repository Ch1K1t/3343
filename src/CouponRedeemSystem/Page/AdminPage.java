package CouponRedeemSystem.Page;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.model.Shop;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminPage extends Page {

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
    try {
      System.out.println();
      System.out.println("Please input the coupon's intrinsic value:");

      String value;
      boolean isDouble;
      do {
        value = s.nextLine();
        isDouble = value.matches("[\\d.]+");
        if (!isDouble) {
          System.out.println("Invalid value, please input again:");
        }
      } while (!isDouble);
      double intrinsicValue = Double.parseDouble(value);

      System.out.println();
      System.out.println("Please input the coupon's shop:");
      Shop shop = null;

      System.out.println();
      System.out.println(
        "Please input the coupon's expiration date (dd/MM/yyyy):"
      );
      String expirationDateString;
      boolean isDate;
      boolean isAfterToday;
      Date expirationDate;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
      do {
        expirationDateString = s.nextLine();
        isDate =
          expirationDateString.matches(
            "^((0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|2[0-1])[0-9]{2})$"
          );
        expirationDate = sdf.parse(expirationDateString);
        isAfterToday = expirationDate.after(new Date());

        if (!isDate || !isAfterToday) {
          System.out.println("Invalid date, please input again:");
        }
      } while (!isDate || !isAfterToday);

      System.out.println();
      System.out.println("Please input the coupon's code:");
      String couponCode = s.nextLine();

      CouponManager couponManager = CouponManager.getInstance();
      String result = "";
      if (type == "Purchasable") {
        System.out.println();
        System.out.println("Please input the coupon's purchasing value:");
        String pointsStr;
        boolean isPointsDouble;
        do {
          pointsStr = s.nextLine();
          isPointsDouble = value.matches("[\\d.]+");
          if (!isPointsDouble) {
            System.out.println("Invalid value, please input again:");
          }
        } while (!isDouble);
        double points = Double.parseDouble(pointsStr);
        couponManager.create(
          couponCode,
          intrinsicValue,
          expirationDate,
          shop,
          type,
          points
        );
      } else {
        couponManager.create(
          couponCode,
          intrinsicValue,
          expirationDate,
          shop,
          type
        );
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void deleteCoupon() {
    System.out.println();
    System.out.println("Please input the coupon's code:");
    String couponCode = s.nextLine();

    CouponManager couponManager = CouponManager.getInstance();

    couponManager.delete(couponCode);
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

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
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("4"));
  }
}
