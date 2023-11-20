package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
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
    double intrinsicValue,
    Date expirationDate,
    Shop shop,
    Double points,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      System.out.println("Coupon code " + couponCode + " already exists");
    }

    Coupon coupon = new PurchasableCoupon(
      intrinsicValue,
      null,
      expirationDate,
      couponCode,
      true,
      points,
      type
    );
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", coupon.getExpirationDate().toString());
    bean.set("owner", coupon.getOwner());
    bean.set("shop", coupon.getShop());
    bean.set("active", coupon.isActive());
    bean.set("type", coupon.getType());
    bean.set("points", coupon.getPoints());

    jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);
    System.out.println("Coupon created");
  }

  // Create json record for redeemable coupon
  public void create(
    String couponCode,
    double intrinsicValue,
    Date expirationDate,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      System.out.println("Coupon code " + couponCode + " already exists");
    }

    Coupon coupon = new RedeemableCoupon(
      intrinsicValue,
      expirationDate,
      couponCode,
      true,
      type
    );
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", coupon.getExpirationDate().toString());
    bean.set("owner", coupon.getOwner());
    bean.set("shop", coupon.getShop());
    bean.set("active", coupon.isActive());
    bean.set("type", coupon.getType());

    jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);
    System.out.println("Coupon created");
  }

  public void delete(String couponCode) {
    JSONObject jsonObject = jsonFileManager.searchJSON(couponCode);
    if (jsonObject == null) {
      System.out.println("Coupon code " + couponCode + " does not exist");
      return;
    }
    String type = jsonObject.getString("type");

    jsonFileManager.deleteJSON("Coupon/" + type, couponCode);
    System.out.println("Coupon deleted");
  }

  public Coupon getCoupon(String couponCode) {
    JSONObject couponJson = jsonFileManager.searchJSON(couponCode);

    if (couponJson == null) {
      System.out.println("Coupon " + couponCode + " does not exist");
      return null;
    } else {
      // Extract coupon details from JSON and return the Account object
      return extractCouponFromJson(couponJson);
    }
  }

  private Coupon extractCouponFromJson(JSONObject couponJson) {
    try {
      double intrinsicValue = couponJson.getDouble("intrinsicValue");
      boolean active = couponJson.getBoolean("active");
      String couponCode = couponJson.getString("couponCode");
      SimpleDateFormat sdf = new SimpleDateFormat(
        "EEE MMM dd HH:mm:ss zzz yyyy",
        Locale.ENGLISH
      );
      Date expirationDate = sdf.parse(couponJson.getString("expirationDate"));
      String type = couponJson.getString("type");
      switch (type) {
        case "Redeemable":
          return new RedeemableCoupon(
            intrinsicValue,
            expirationDate,
            couponCode,
            active,
            type
          );
        case "Purchasable":
          double points = couponJson.getDouble("points");
          return new PurchasableCoupon(
            intrinsicValue,
            null,
            expirationDate,
            couponCode,
            active,
            points,
            type
          );
        default:
          return null;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void generateDemoCoupon() {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
      Date expirationDate = sdf.parse("11/11/2025");
      create(
        "P1",
        Double.parseDouble("1"),
        expirationDate,
        null,
        Double.parseDouble("1"),
        "Purchasable"
      );
      create(
        "P2",
        Double.parseDouble("1"),
        expirationDate,
        null,
        Double.parseDouble("1"),
        "Purchasable"
      );
      create("R1", Double.parseDouble("1"), expirationDate, "Redeemable");
      create("R2", Double.parseDouble("1"), expirationDate, "Redeemable");
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
