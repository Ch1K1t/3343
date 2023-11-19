package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class AccountManager {

  private static AccountManager instance;
  CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  PasswordManager passwordManager = PasswordManager.getInstance();

  private AccountManager() {}

  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

  public void createAccInfo(Account account) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());
    bean.set("age", account.getAge());
    bean.set("telNo", account.getTelNo());
    bean.set("points", account.getPoints());
    bean.set("dateOfBirth", account.getDateOfBirth().toString());
    bean.set("couponIDs", account.getCouponIDs());

    try {
      jsonFileManager.modifyJSON("Account", account.getUserName(), bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createAccInfo(
    String userName,
    String role,
    int age,
    int telNo,
    String dob
  ) throws ParseException {
    Account account = new Account(userName, role, age, telNo, dob);
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());
    bean.set("age", account.getAge());
    bean.set("telNo", account.getTelNo());
    bean.set("points", account.getPoints());
    bean.set("dateOfBirth", account.getDateOfBirth().toString());
    bean.set("couponIDs", account.getCouponIDs());

    try {
      jsonFileManager.modifyJSON("Account", userName, bean);
      System.out.println("Account created");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error");
    }
  }

  // Delete existing account
  public void delete(String userName) {
    try {
      Account account = getAccount(userName);
      jsonFileManager.deleteJSON("Account", account.getUserName());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // Update existing account
  public void update(String userName) {
    try {
      Account account = getAccount(userName);

      // Delete the original JSON file
      delete(userName);

      // Save the updated account details
      createAccInfo(account);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  public Account getAccount(String userName)
    throws IOException, ParseException {
    // Search for the JSON file
    JSONObject accountJson = jsonFileManager.searchJSON(userName + ".json");

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
    String role = accountJson.getString("role");
    int age = accountJson.getInt("age");
    int telNo = accountJson.getInt("telNo");
    double points = accountJson.getDouble("points");
    String dateOfBirth = accountJson.getString("dateOfBirth");
    JSONArray couponIDsArray = accountJson.getJSONArray("couponIDs");
    List<String> couponIDs = new ArrayList<>();
    for (int i = 0; i < couponIDsArray.size(); i++) {
      couponIDs.add(couponIDsArray.getString(i));
    }

    return new Account(
      userName,
      role,
      age,
      telNo,
      dateOfBirth,
      points,
      couponIDs
    );
  }

  public void createAccount(String userName, String Password)
    throws IOException {
    passwordManager.createNewPassword(userName, Password);
  }
}
