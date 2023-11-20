package CouponRedeemSystem.Discount.model;

import CouponRedeemSystem.System.ID.IdGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;

public class Discount {

  private int id;
  private String discountName;
  private String startDate;
  private String expireDate;
  private double valueOff;
  private boolean active;

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public Discount() {}

  public Discount(
    String discountName,
    String startDate,
    String expireDate,
    double valueOff
  ) {
    this.id = IdGenerator.getInstance().getNextId("DiscountId");
    this.discountName = discountName;
    this.startDate = startDate;
    this.expireDate = expireDate;
    this.valueOff = valueOff;
    updateStatus();
  }

  public void updateStatus() {
    try {
      this.active =
        sdf.parse(startDate).compareTo(new Date()) <= 0 &&
        sdf.parse(expireDate).compareTo(new Date()) > 0;
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public JSONObject getJSONObject() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", id);
    jsonObject.put("discountName", discountName);
    jsonObject.put("startDate", startDate);
    jsonObject.put("expireDate", expireDate);
    jsonObject.put("valueOff", valueOff);
    jsonObject.put("active", active);

    return jsonObject;
  }

  public String getJSONString() {
    return getJSONObject().toString();
  }

  public boolean getActive() {
    return active;
  }

  public int getId() {
    return id;
  }

  public void disableDiscount() {
    this.active = false;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setValueOff(double valueOff) {
    this.valueOff = valueOff;
  }
}
