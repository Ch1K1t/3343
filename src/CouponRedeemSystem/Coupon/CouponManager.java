package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
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
  public void createCoupon(
    String couponCode,
    double intrinsicValue,
    String expirationDate,
    Shop shop,
    Double points,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      System.out.println();
      System.out.println("Coupon code " + couponCode + " already exists");
      return;
    }

    Coupon coupon = new PurchasableCoupon(
      intrinsicValue,
      shop.getShopName(),
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
    bean.set("owner", coupon.getOwner());
    bean.set("shop", coupon.getShop());
    bean.set("points", coupon.getPoints());

    jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);

    System.out.println();
    System.out.println("Coupon created");
  }

  // Create new redeemable coupon
  public void createCoupon(
    String couponCode,
    double intrinsicValue,
    String expirationDate,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      System.out.println();
      System.out.println("Coupon code " + couponCode + " already exists");
      return;
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

    jsonFileManager.modifyJSON("Coupon/" + type, couponCode, bean);

    System.out.println();
    System.out.println("Coupon created");
  }

  public void deleteCoupon(String couponCode) {
    Coupon coupon = getCoupon(couponCode);
    if (coupon == null) {
      System.out.println("Coupon code " + couponCode + " does not exist");
      return;
    }

    jsonFileManager.deleteJSON("Coupon/" + coupon.getType(), couponCode);

    System.out.println();
    System.out.println("Coupon deleted");
  }

  public void updateCoupon(Coupon coupon) {
    String type = coupon.getType();

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    bean.set("active", coupon.isActive());
    bean.set("type", type);
    if (type.equals("Purchasable")) {
      bean.set("owner", coupon.getOwner());
      bean.set("shop", coupon.getShop());
      bean.set("points", coupon.getPoints());
    }

    jsonFileManager.modifyJSON("Coupon/" + type, coupon.getCouponCode(), bean);

    System.out.println();
    System.out.println("Coupon updated");
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
    createCoupon("P1", 1.0, "11/11/2025", null, 1.0, "Purchasable");
    createCoupon("P2", 1.0, "11/11/2025", null, 1.0, "Purchasable");
    createCoupon("R1", 1.0, "11/11/2025", "Redeemable");
    createCoupon("R2", 1.0, "11/11/2025", "Redeemable");
  }
}
