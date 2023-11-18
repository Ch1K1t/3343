package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class AccountManager {

  private static AccountManager instance;
  CRSJsonFileManager JsonFileManager = CRSJsonFileManager.getInstance();

  private AccountManager() {}

  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

  public void create(Account account) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("age", account.getAge());
    bean.set("telNo", account.getTelNo());
    bean.set("points", account.getPoints());
    bean.set("dateOfBirth", account.getDateOfBirth().toString());
    bean.set("couponIDs", account.getCouponIDs());

    try {
      JsonFileManager.modifyJSON("Account", account.getUserName(), bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Delete existing account
  public void delete(Account account) {
    try {
      JsonFileManager.deleteJSON("Account", account.getUserName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Update existing account
  public void update(Account account) {
    // Delete the original JSON file
    delete(account);

    // Save the updated account details
    create(account);
  }

  public Account getAccount(String userName)
    throws IOException, ParseException {
    // Search for the JSON file
    JSONObject accountJson = JsonFileManager.searchJSON(
      userName + ".json",
      null
    );

    // Extract account details from JSON and return the Account object
    if (!accountJson.isEmpty()) {
      return extractAccountFromJson(accountJson);
    }

    // Return null if the account was not found
    return null;
  }

  private Account extractAccountFromJson(JSONObject accountJson)
    throws ParseException {
    String userName = accountJson.getString("userName");
    int age = accountJson.getInt("age");
    int telNo = accountJson.getInt("telNo");
    double points = accountJson.getDouble("points");
    String dateOfBirth = accountJson.getString("dateOfBirth");
    JSONArray couponIDsArray = accountJson.getJSONArray("couponIDs");
    List<String> couponIDs = new ArrayList<>();
    for (int i = 0; i < couponIDsArray.size(); i++) {
      couponIDs.add(couponIDsArray.getString(i));
    }

    return new Account(userName, age, telNo, points, dateOfBirth, couponIDs);
  }
}
