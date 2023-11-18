package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
  public void create(Coupon coupon, String type) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("code", coupon.getCouponCode());
    bean.set("value", coupon.getIntrinsicValue());
    bean.set("expiration_date", coupon.getExpirationDate().toString());
    bean.set("owner", null);
    bean.set("shop", coupon.getShop());
    bean.set("active", coupon.isActive());
    try {
      jsonFileManager.modifyJSON("Coupon/" + type, coupon.getCouponCode(), bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void delete(String couponCode, String type) {
    try {
      jsonFileManager.deleteJSON("Coupon/" + type, couponCode);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Coupon getCoupon(String couponCode, String type) throws IOException, ParseException {
    // Search for the JSON file
    JSONObject couponJson = JsonFileManager.searchJSON(couponCode + ".json", null);

    // Extract coupon details from JSON and return the Account object
    if (!couponJson.isEmpty()) {
        return extractCouponFromJson(couponJson, type);
    }

    // Return null if the coupon was not found
    return null;
  }

  private Coupon extractCouponFromJson(JSONObject couponJson, String type) throws ParseException {
    double value = couponJson.getDouble("value");
    boolean active = couponJson.getBoolean("active");
    String couponCode = couponJson.getString("code");
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date expirationDate = sdf.parse(couponJson.getString("expiration_date"));
    switch (type) {
      case "Redeemable": 
        return new RedeemableCoupon(value, null, expirationDate, couponCode, active);
      case "Purchasable": 
        return new PurchasableCoupon(value, null, expirationDate, couponCode, active);
    }
    return null;
  }
}
