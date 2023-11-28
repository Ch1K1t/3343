package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AccountManager {

  private static AccountManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private PasswordManager passwordManager = PasswordManager.getInstance();

  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  private AccountManager() {}

  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

  public boolean createPassword(String userName, String Password) {
    JSONObject jsonObject = jsonFileManager.searchJSON(userName);
    if (jsonObject != null) {
      return false;
    }

    return passwordManager.createNewPassword(userName, Password);
  }

  // Create new user account
  public Account createAccount(
    String userName,
    String role,
    String telNo,
    String dob
  ) {
    Account account = new Account(userName, role, telNo, dob);
    this.updateAccount(account);

    return account;
  }

  // Create new non-user account
  public Account createAccount(String userName, String role) {
    Account account = new Account(userName, role);
    this.updateAccount(account);

    return account;
  }

  public boolean updateAccount(Account account) {
    String role = account.getRole();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userName", account.getUserName());
    jsonObject.put("role", role);
    if (role.equals("User")) {
      jsonObject.put("points", account.getPoints());
      jsonObject.put("couponIDs", account.getCouponIDs());
      jsonObject.put("age", account.getAge());
      jsonObject.put("telNo", account.getTelNo());
      jsonObject.put("dateOfBirth", sdf.format(account.getDateOfBirth()));
    }

    return jsonFileManager.modifyJSON(
      "Account",
      account.getUserName(),
      jsonObject
    );
  }

  public boolean deleteAccount(Account account) {
    return jsonFileManager.deleteJSON("Account", account.getUserName());
  }

  public Account getAccount(String userName) {
    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    if (accountJson == null) {
      return null;
    } else {
      return extractAccountFromJson(accountJson);
    }
  }

  public Account extractAccountFromJson(JSONObject accountJson) {
    try {
      String userName = accountJson.getString("userName");
      String role = accountJson.getString("role");
      if (!role.equals("User")) {
        return new Account(userName, role);
      }

      int age = accountJson.getInt("age");
      String telNo = accountJson.getString("telNo");
      double points = accountJson.getDouble("points");
      Date dateOfBirth = sdf.parse(accountJson.getString("dateOfBirth"));
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
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public String jsonToString(JSONObject jsonObject) {
    String result = "";

    String userName = jsonObject.getString("userName");
    String role = jsonObject.getString("role");
    if (!role.equals("User")) {
      result = "{\"userName\":\"" + userName + "\", \"role\":\"" + role + "\"}";
    } else {
      int age = jsonObject.getInt("age");
      String telNo = jsonObject.getString("telNo");
      double points = jsonObject.getDouble("points");
      String dateOfBirth = jsonObject.getString("dateOfBirth");
      JSONArray couponIDs = jsonObject.getJSONArray("couponIDs");

      result =
        "{\"userName\":\"" +
        userName +
        "\", \"role\":\"" +
        role +
        "\", \"age\":" +
        age +
        ", \"telNo\":\"" +
        telNo +
        "\", \"dateOfBirth\":\"" +
        dateOfBirth +
        "\", \"points\":" +
        points +
        ", \"couponIDs\":" +
        couponIDs +
        "}";
    }

    return result;
  }
}
