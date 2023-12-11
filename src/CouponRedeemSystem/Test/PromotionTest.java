package CouponRedeemSystem.Test;

import CouponRedeemSystem.Promotion.model.Promotion;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PromotionTest extends MainTest {

  @Before
  @After
  public void reset() {
    Util.clearSystem();
  }

  @Test
  public void createPromotionTest() throws ParseException {
    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    String expectedOutput =
      "Promotion{promotionName=\"" +
      promotionName +
      "\", shop=" +
      shop +
      ", startDate=" +
      Util.sdf.parse(startDate) +
      ", endDate=" +
      Util.sdf.parse(endDate) +
      ", value=" +
      value +
      "}";

    Assert.assertEquals(expectedOutput, promotion.toString());
  }

  @Test
  public void updatePromotionTest() throws ParseException {
    Shop shop = shopManager.createShop(shopName);
    promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    JSONObject promotionJson = jsonFileManager.searchJSON(promotionName);

    String expectedOutput =
      "{\"promotionName\":\"" +
      promotionName +
      "\", \"shop\":\"" +
      shopName +
      "\", \"startDate\":\"" +
      startDate +
      "\", \"endDate\":\"" +
      endDate +
      "\", \"value\":" +
      value +
      "}";

    Assert.assertEquals(
      expectedOutput,
      promotionManager.jsonToString(promotionJson)
    );
  }

  @Test
  public void deletePromotionTest() {
    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    promotionManager.deletePromotion(promotion);
    Promotion promotion2 = promotionManager.getPromotion(promotionName);

    Assert.assertEquals(null, promotion2);
  }

  @Test
  public void getPromotionTest() {
    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    Promotion promotion2 = promotionManager.getPromotion(promotionName);

    Assert.assertEquals(promotion.toString(), promotion2.toString());
  }

  @Test
  public void getPromotionTestFail() {
    Promotion promotion = promotionManager.getPromotion(promotionName);

    Assert.assertEquals(null, promotion);
  }

  @Test
  public void extractPromotionFromJsonTest() {
    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    JSONObject promotionJson = jsonFileManager.searchJSON(promotionName);
    Promotion promotion2 = promotionManager.extractPromotionFromJson(
      promotionJson
    );

    Assert.assertEquals(promotion.toString(), promotion2.toString());
  }

  @Test
  public void validateTimeTest() {
    String startDateStr = Util.sdf.format(DateUtils.addDays(Util.today, -1));

    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDateStr,
      day
    );

    Assert.assertEquals(true, promotion.validateTime());
  }

  @Test
  public void validateTimeTestFail() {
    String startDate = Util.sdf.format(DateUtils.addDays(Util.today, 1));

    Shop shop = shopManager.createShop(shopName);
    Promotion promotion = promotionManager.createPromotion(
      promotionName,
      shop,
      value,
      startDate,
      day
    );

    Assert.assertEquals(false, promotion.validateTime());
  }
}
