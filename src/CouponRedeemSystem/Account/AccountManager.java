package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

  private AccountManager instance;
	CRSJsonFileManager JsonFileManager = CRSJsonFileManager.getInstance();

  private AccountManager() {}

  public AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

	// Save account details to account folder
	public void saveAccountDetailsToJsonFolder(Account account) {
		JSONObject accountJson = convertAccountToJson(account);

		// Combine the directory path and file name to create the file path
		String userNameString = account.getUserName();
        String filePath =  "Data/Account/" + userNameString;

        // Write the JSONObject to the file
        try {
            JsonFileManager.modifyJSON(userNameString, filePath, accountJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	// Convert account to JSONObject
    private JSONObject convertAccountToJson(Account account) {

        JSONObject accountJson = new JSONObject();
		accountJson.put("userName", account.getUserName());
        accountJson.put("age", account.getAge());
        accountJson.put("telNo", account.getTelNo());
        accountJson.put("points", account.getPoints());
        accountJson.put("dateOfBirth", account.getDateOfBirth());

        // Convert list of coupons to JSONArray
        JSONArray couponsIDArray = new JSONArray();
        for (String couponId : account.getCouponIDs()) {
            // Add coupon details to couponJson
            couponsIDArray.add(couponId);
        }
        accountJson.put("coupons", couponsIDArray);

        return accountJson;
    }

	// Load account details from JSON file
	private Account loadAccountDetailsFromFolder(String UserName) {
		try {
			JSONObject accountJson = JsonFileManager.searchJSON("Data/Account/"+UserName, null);
			if (!accountJson.isEmpty()) {
				return extractAccountFromJson(accountJson);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Extract the JSON to an Account object
    private Account extractAccountFromJson(JSONObject accountJson) {
		String userName = accountJson.getString("userName");
        int age = accountJson.getInt("age");
        int telNo = accountJson.getInt("telNo");
        double points = accountJson.getDouble("points");
        String dateOfBirth = accountJson.getString("dateOfBirth"); 
		JSONArray couponIDs = accountJson.getJSONArray("coupons");
		List<String> couponIDList = new ArrayList<>();
        for (int i = 0; i < couponIDs.size(); i++) {
            couponIDList.add(couponIDs.getString(i));
        }
        // You may need to handle parsing exceptions based on your specific needs
        try {
            return new Account(userName, age, telNo, points, dateOfBirth,couponIDList);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your needs
        }
    }

}
