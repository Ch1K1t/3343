package CouponRedeemSystem.Discount;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class DiscountManager {
	private static DiscountManager instance;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private DiscountManager() {
		
	}
	
	public static DiscountManager getInstance() {
		if (instance == null) {
			instance = new DiscountManager();
		}
		
		return instance;
	}
	
	public Discount createDiscountByDay(String discountName, Date startDate, int day, double valueOff) throws ParseException, IOException {
		Date startDateInSDF = sdf.parse(startDate.toString());
		Date expireDate = sdf.parse(DateUtils.addDays(startDate, day).toString());
		Discount discount = new Discount(discountName, startDateInSDF, expireDate, valueOff);
		
		storeNewDiscount(discount);
		
		return discount;
	}
	
	public Discount createDiscountByMonth(String discountName, Date startDate, int day, double valueOff) throws ParseException, IOException {
		Date startDateInSDF = sdf.parse(startDate.toString());
		Date expireDate = sdf.parse(DateUtils.addDays(startDate, day).toString());
		Discount discount = new Discount(discountName, startDateInSDF, expireDate, valueOff);
		
		storeNewDiscount(discount);
		
		return discount;
	}
	
	public void updateActiveDiscountList() throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		JSONObject discountJson = mgr.searchJSON("Discount List.json");
		JSONObject activeDiscountJsonObject = new JSONObject();
		for (Object keyObj: discountJson.keySet()) {
			String key = (String) keyObj;
			Discount discount = (Discount) JSONObject.toBean(discountJson.getJSONObject(key), Discount.class);
			boolean oldStatus = discount.getActive();
			discount.updateStatus();
			boolean newStatus = discount.getActive();
			if (oldStatus != newStatus) {
				modifyDiscount(key, discount);
			}
			if (newStatus) {
				activeDiscountJsonObject.put(key, discount.getJSONObject());
			}
		}
		mgr.modifyJSON("Discount", "Active Discount List", activeDiscountJsonObject);
	}
	
	private void storeNewDiscount(Discount discount) throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		JSONObject discountJsonObject = discount.getJSONObject();
		mgr.modifyJSON("Discount", "Discount List", discountJsonObject);
	}
	
	private void modifyDiscount(String id, Discount discount) throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		JSONObject discountJson = mgr.searchJSON("Discount List.json");
		discountJson.put(id, discount.getJSONObject());
	}
}
