package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.SimpleDateFormat;
import net.sf.json.JSONObject;

public class CouponManager {

  private static CouponManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  private CouponManager() {}

  public static CouponManager getInstance() {
    if (instance == null) {
      instance = new CouponManager();
    }
    return instance;
  }

  // Create new purchasable coupon
  public Coupon createCoupon(
    String couponCode,
    double intrinsicValue,
    double points,
    Shop shop,
    String type,
    String expirationDate
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
    }

    Coupon coupon = new PurchasableCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      null,
      true,
      type,
      expirationDate
    );
    this.updateCoupon(coupon);

    return coupon;
  }

  // Create new redeemable coupon
  public Coupon createCoupon(
    String couponCode,
    double intrinsicValue,
    String type,
    String expirationDate
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
    }

    Coupon coupon = new RedeemableCoupon(
      couponCode,
      intrinsicValue,
      true,
      type,
      expirationDate
    );
    this.updateCoupon(coupon);

    return coupon;
  }

  public boolean updateCoupon(Coupon coupon) {
    String type = coupon.getType();

    JSONObject jsonObject = new JSONObject();
    if (type.equals("Purchasable")) {
      jsonObject.put("couponCode", coupon.getCouponCode());
      jsonObject.put("intrinsicValue", coupon.getIntrinsicValue());
      jsonObject.put("points", coupon.getPoints());
      jsonObject.put("shop", coupon.getShop().getShopName());
      jsonObject.put(
        "owner",
        coupon.getOwner() == null ? "null" : coupon.getOwner().getUserName()
      );
      jsonObject.put("active", coupon.isActive());
      jsonObject.put("type", coupon.getType());
      jsonObject.put("expirationDate", sdf.format(coupon.getExpirationDate()));
    } else {
      jsonObject.put("couponCode", coupon.getCouponCode());
      jsonObject.put("intrinsicValue", coupon.getIntrinsicValue());
      jsonObject.put("active", coupon.isActive());
      jsonObject.put("type", coupon.getType());
      jsonObject.put("expirationDate", sdf.format(coupon.getExpirationDate()));
    }

    return jsonFileManager.modifyJSON(
      "Coupon/" + type,
      coupon.getCouponCode(),
      jsonObject
    );
  }

  public boolean deleteCoupon(Coupon coupon) {
    return jsonFileManager.deleteJSON(
      "Coupon/" + coupon.getType(),
      coupon.getCouponCode()
    );
  }

  public Coupon getCoupon(String couponCode) {
    JSONObject couponJson = jsonFileManager.searchJSON(couponCode);

    if (couponJson == null) {
      return null;
    } else {
      return extractCouponFromJson(couponJson);
    }
  }

  public Coupon extractCouponFromJson(JSONObject couponJson) {
    double intrinsicValue = couponJson.getDouble("intrinsicValue");
    boolean active = couponJson.getBoolean("active");
    String couponCode = couponJson.getString("couponCode");
    String expirationDate = couponJson.getString("expirationDate");
    String type = couponJson.getString("type");
    switch (type) {
      case "Redeemable":
        return new RedeemableCoupon(
          couponCode,
          intrinsicValue,
          active,
          type,
          expirationDate
        );
      case "Purchasable":
        ShopManager shopManager = ShopManager.getInstance();
        Shop shop = shopManager.getShop(couponJson.getString("shop"));
        AccountManager accountManager = AccountManager.getInstance();
        Account owner = accountManager.getAccount(
          couponJson.getString("owner")
        );
        double points = couponJson.getDouble("points");

        return new PurchasableCoupon(
          couponCode,
          intrinsicValue,
          points,
          shop,
          owner,
          active,
          type,
          expirationDate
        );
      default:
        return null;
    }
  }

  public String jsonToString(JSONObject jsonObject) {
    String result = "";

    String couponCode = jsonObject.getString("couponCode");
    double intrinsicValue = jsonObject.getDouble("intrinsicValue");
    boolean active = jsonObject.getBoolean("active");
    String type = jsonObject.getString("type");
    String expirationDate = jsonObject.getString("expirationDate");

    if (type.equals("Purchasable")) {
      double points = jsonObject.getDouble("points");
      String shop = jsonObject.getString("shop");
      String owner = jsonObject.getString("owner");
      result =
        "{\"couponCode\":\"" +
        couponCode +
        "\", \"intrinsicValue\":" +
        intrinsicValue +
        ", \"points\":" +
        points +
        ", \"shop\":\"" +
        shop +
        "\", \"owner\":" +
        owner +
        ", \"active\":" +
        active +
        ", \"type\":\"" +
        type +
        "\", \"expirationDate\":\"" +
        expirationDate +
        "\"}";
    } else {
      result =
        "{\"couponCode\":\"" +
        couponCode +
        "\", \"intrinsicValue\":" +
        intrinsicValue +
        ", \"active\":" +
        active +
        ", \"type\":\"" +
        type +
        "\", \"expirationDate\":\"" +
        expirationDate +
        "\"}";
    }

    return result;
  }
}
