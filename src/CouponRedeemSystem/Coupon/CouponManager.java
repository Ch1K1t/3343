package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.SimpleDateFormat;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

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
    String expirationDate,
    Shop shop,
    Double points,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
    }

    Coupon coupon = new PurchasableCoupon(
      intrinsicValue,
      shop,
      expirationDate,
      couponCode,
      true,
      points,
      type
    );

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("type", coupon.getType());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    bean.set("active", coupon.isActive());
    bean.set("owner", null);
    bean.set("shop", coupon.getShop().getShopName());
    bean.set("points", coupon.getPoints());

    boolean isSuccess = jsonFileManager.modifyJSON(
      "Coupon/" + type,
      couponCode,
      bean
    );

    if (isSuccess) {
      return coupon;
    } else {
      return null;
    }
  }

  // Create new redeemable coupon
  public Coupon createCoupon(
    String couponCode,
    double intrinsicValue,
    String expirationDate,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
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
    bean.set("type", coupon.getType());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    bean.set("active", coupon.isActive());

    boolean isSuccess = jsonFileManager.modifyJSON(
      "Coupon/" + type,
      couponCode,
      bean
    );

    if (isSuccess) {
      return coupon;
    } else {
      return null;
    }
  }

  public boolean deleteCoupon(Coupon coupon) {
    return jsonFileManager.deleteJSON(
      "Coupon/" + coupon.getType(),
      coupon.getCouponCode()
    );
  }

  public boolean updateCoupon(Coupon coupon) {
    String type = coupon.getType();

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    bean.set("active", coupon.isActive());
    bean.set("type", type);
    if (type.equals("Purchasable")) {
      bean.set("owner", coupon.getOwner().getUserName());
      bean.set("shop", coupon.getShop().getShopName());
      bean.set("points", coupon.getPoints());
    }

    return jsonFileManager.modifyJSON(
      "Coupon/" + type,
      coupon.getCouponCode(),
      bean
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

  private Coupon extractCouponFromJson(JSONObject couponJson) {
    double intrinsicValue = couponJson.getDouble("intrinsicValue");
    boolean active = couponJson.getBoolean("active");
    String couponCode = couponJson.getString("couponCode");
    String expirationDate = couponJson.getString("expirationDate");
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
  }

  public void generateDemoCoupon() {
    ShopManager shopManager = ShopManager.getInstance();
    Shop shop1 = shopManager.getShop("shop1");
    createCoupon("P1", 1.0, "11/11/2025", shop1, 1.0, "Purchasable");
    Shop shop2 = shopManager.getShop("shop2");
    createCoupon("P2", 1.0, "11/11/2025", shop2, 1.0, "Purchasable");
    createCoupon("R1", 1.0, "11/11/2025", "Redeemable");
    createCoupon("R2", 1.0, "11/11/2025", "Redeemable");
  }
}
