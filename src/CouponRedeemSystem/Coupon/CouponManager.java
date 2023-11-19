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

  private static final CRSJsonFileManager JsonFileManager = null;
  private static CouponManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private CouponManager() {}

  public static CouponManager getInstance() {
    if (instance == null) {
      instance = new CouponManager();
    }
    return instance;
  }

  // Create json record
  public String create(
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
        return "Coupon code " + couponCode + " already exists";
      }

      LazyDynaBean bean = new LazyDynaBean();
      bean.set("code", couponCode);
      bean.set("value", value);
      bean.set("expiration_date", expirationDate.toString());
      bean.set("owner", null);
      bean.set("shop", shop);
      bean.set("active", true);
      bean.set("type", type);

      jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);
      return "Coupon created";
    } catch (IOException e) {
      e.printStackTrace();
      return "Error";
    }
  }

  public String delete(String couponCode) {
    try {
      JSONObject jsonObject = jsonFileManager.searchJSON(couponCode + ".json");
      if (jsonObject == null) {
        return "Coupon code " + couponCode + " does not exist";
      }
      String type = jsonObject.getString("type");

      jsonFileManager.deleteJSON("Coupon/" + type, couponCode);
      return "Coupon deleted";
    } catch (IOException e) {
      e.printStackTrace();
      return "Error";
    }
  }

  public Coupon getCoupon(String couponCode, String type)
    throws IOException, ParseException {
    // Search for the JSON file
    JSONObject couponJson = JsonFileManager.searchJSON(couponCode + ".json");

    // Extract coupon details from JSON and return the Account object
    if (!couponJson.isEmpty()) {
      return extractCouponFromJson(couponJson);
    }

    // Return null if the coupon was not found
    return null;
  }

  private Coupon extractCouponFromJson(JSONObject couponJson)
    throws ParseException {
    double value = couponJson.getDouble("value");
    boolean active = couponJson.getBoolean("active");
    String couponCode = couponJson.getString("code");
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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
        return new PurchasableCoupon(
          value,
          null,
          expirationDate,
          couponCode,
          active
        );
    }
    return null;
  }
}
