package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class CouponManager {

  private static CouponManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private CouponManager() {}

  public static CouponManager getInstance() {
    if (instance == null) {
      instance = new CouponManager();
    }
    return instance;
  }

  // Create json record for purchasable coupon
  public void create(
    String couponCode,
    double value,
    Date expirationDate,
    Shop shop,
    String type,
    Double points
  ) {
    try {
      JSONObject json;
      json = jsonFileManager.searchJSON(couponCode + ".json");
      if (json != null) {
        System.out.println("Coupon code " + couponCode + " already exists");
      }

      LazyDynaBean bean = new LazyDynaBean();
      bean.set("code", couponCode);
      bean.set("value", value);
      bean.set("expiration_date", expirationDate.toString());
      bean.set("owner", null);
      bean.set("shop", shop);
      bean.set("active", true);
      bean.set("type", type);
      bean.set("points", points);

      jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);
      System.out.println("Coupon created");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error");
    }
  }

  // Create json record for redeemable coupon
  public void create(
    String couponCode,
    double value,
    Date expirationDate,
    Shop shop,
    String type
  ) {
    try {
      JSONObject json;
      json = jsonFileManager.searchJSON(couponCode + ".json");
      if (json != null) {
        System.out.println("Coupon code " + couponCode + " already exists");
      }

      LazyDynaBean bean = new LazyDynaBean();
      bean.set("code", couponCode);
      bean.set("value", value);
      bean.set("expiration_date", expirationDate.toString());
      bean.set("shop", shop);
      bean.set("active", true);
      bean.set("type", type);

      jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);
      System.out.println("Coupon created");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error");
    }
  }

  public void delete(String couponCode) {
    try {
      JSONObject jsonObject = jsonFileManager.searchJSON(couponCode + ".json");
      if (jsonObject == null) {
        System.out.println("Coupon code " + couponCode + " does not exist");
      }
      String type = jsonObject.getString("type");

      jsonFileManager.deleteJSON("Coupon/" + type, couponCode);
      System.out.println("Coupon deleted");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error");
    }
  }

  public Coupon getCoupon(String couponCode) {
    // Search for the JSON file
    try {
      JSONObject couponJson = jsonFileManager.searchJSON(couponCode + ".json");

      // Extract coupon details from JSON and return the Account object
      if (!couponJson.isEmpty()) {
        return extractCouponFromJson(couponJson);
      }

      // Return null if the coupon was not found
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Coupon extractCouponFromJson(JSONObject couponJson) {
    try {
      double value = couponJson.getDouble("value");
      boolean active = couponJson.getBoolean("active");
      String couponCode = couponJson.getString("code");
      SimpleDateFormat sdf = new SimpleDateFormat(
        "EEE MMM dd HH:mm:ss zzz yyyy",
        Locale.ENGLISH
      );
      Date expirationDate = sdf.parse(couponJson.getString("expiration_date"));
      switch (couponJson.getString("type")) {
        case "Redeemable":
          return new RedeemableCoupon(
            value,
            null,
            expirationDate,
            couponCode,
            active
          );
        case "Purchasable":
          double points = couponJson.getDouble("points");
          return new PurchasableCoupon(
            value,
            null,
            expirationDate,
            couponCode,
            active,
            points
          );
        default:
          return null;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
