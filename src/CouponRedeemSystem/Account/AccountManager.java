package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountManager.
 */
public class AccountManager {

  /** The instance. */
  private static AccountManager instance;
  
  /** The json file manager. */
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  
  /** The password manager. */
  private PasswordManager passwordManager = PasswordManager.getInstance();

  /**
   * Instantiates a new account manager.
   */
  private AccountManager() {}

  /**
   * Gets the single instance of AccountManager.
   *
   * @return single instance of AccountManager
   */
  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

  /**
   * Creates the password.
   *
   * @param userName the user name
   * @param Password the password
   */
  public void createPassword(String userName, String Password) {
    passwordManager.createNewPassword(userName, Password);
  }

  /**
   * Creates the account.
   *
   * @param userName the user name
   * @param role the role
   * @param dob the dob
   * @param telNo the tel no
   * @return the account
   */
  // Create new user account
  public Account createAccount(
    String userName,
    String role,
    String dob,
    String telNo
  ) {
    Account account = new Account(userName, role, dob, telNo);
    this.updateAccount(account);

    return account;
  }

  /**
   * Creates the account.
   *
   * @param userName the user name
   * @param role the role
   * @return the account
   */
  // Create new non-user account
  public Account createAccount(String userName, String role) {
    Account account = new Account(userName, role);
    this.updateAccount(account);

    return account;
  }

  /**
   * Update account.
   *
   * @param account the account
   */
  public void updateAccount(Account account) {
    String role = account.getRole();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userName", account.getUserName());
    jsonObject.put("role", role);
    if (role.equals("User")) {
      jsonObject.put("points", account.getPoints());
      jsonObject.put("couponIDs", account.getCouponIDs());
      jsonObject.put("dateOfBirth", Util.sdf.format(account.getDateOfBirth()));
      jsonObject.put("age", account.getAge());
      jsonObject.put("telNo", account.getTelNo());
    }

    jsonFileManager.modifyJSON("Account", account.getUserName(), jsonObject);
  }

  /**
   * Delete account.
   *
   * @param account the account
   */
  public void deleteAccount(Account account) {
    jsonFileManager.deleteJSON("Account", account.getUserName());
  }

  /**
   * Gets the account.
   *
   * @param userName the user name
   * @return the account
   */
  public Account getAccount(String userName) {
    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    if (accountJson == null) {
      return null;
    } else {
      return extractAccountFromJson(accountJson);
    }
  }

  /**
   * Extract account from json.
   *
   * @param accountJson the account json
   * @return the account
   */
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
      Date dateOfBirth = Util.sdf.parse(accountJson.getString("dateOfBirth"));
      JSONArray couponIDsArray = accountJson.getJSONArray("couponIDs");
      List<String> couponIDs = new ArrayList<>();
      for (int i = 0; i < couponIDsArray.size(); i++) {
        couponIDs.add(couponIDsArray.getString(i));
      }

      return new Account(
        userName,
        role,
        points,
        couponIDs,
        dateOfBirth,
        age,
        telNo
      );
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Json to string.
   *
   * @param jsonObject the json object
   * @return the string
   */
  public String jsonToString(JSONObject jsonObject) {
    String result = "";

    String userName = jsonObject.getString("userName");
    String role = jsonObject.getString("role");
    if (!role.equals("User")) {
      result = "{\"userName\":\"" + userName + "\", \"role\":\"" + role + "\"}";
    } else {
      double points = jsonObject.getDouble("points");
      JSONArray couponIDs = jsonObject.getJSONArray("couponIDs");
      String dateOfBirth = jsonObject.getString("dateOfBirth");
      int age = jsonObject.getInt("age");
      String telNo = jsonObject.getString("telNo");

      result =
        "{\"userName\":\"" +
        userName +
        "\", \"role\":\"" +
        role +
        "\", \"points\":" +
        points +
        ", \"couponIDs\":" +
        couponIDs +
        "\", \"dateOfBirth\":\"" +
        dateOfBirth +
        "\", \"age\":" +
        age +
        ", \"telNo\":\"" +
        telNo +
        "}";
    }

    return result;
  }
}
