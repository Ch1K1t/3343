package CouponRedeemSystem.Discount.model;

import java.io.IOException;
import java.util.Date;

import CouponRedeemSystem.System.ID.IdGenerator;
import net.sf.json.JSONObject;

public class Discount {
	private int id;
	private String discountName;
	private Date startDate;
	private Date expireDate;
	private double valueOff;
	private boolean active;
	
	public Discount(String discountName, Date startDate, Date expireDate, double valueOff) throws IOException {
		this.id = IdGenerator.getInstance().getNextId("DiscountId");
		this.discountName = discountName;
		this.startDate = startDate;
		this.expireDate = expireDate;
		this.valueOff = valueOff;
		updateStatus();
	}
	
	public void updateStatus() {
		this.active = startDate.compareTo(new Date()) >= 0 && expireDate.compareTo(new Date()) < 0;
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
	
	public void disableDiscount() {
		this.active = false;
	}
}
