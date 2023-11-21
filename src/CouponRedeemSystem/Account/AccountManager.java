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

    jsonFileManager.modifyJSON("Account", account.getUserName(), bean);
  }

  // Create new user account
  public void createAccInfo(
    String userName,
    String role,
    int age,
    String telNo,
    String dob
  ) {
    Account account = new Account(userName, role, age, telNo, dob);
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());
    bean.set("age", account.getAge());
    bean.set("telNo", account.getTelNo());
    bean.set("points", account.getPoints());
    bean.set(
      "dateOfBirth",
      new SimpleDateFormat("dd/MM/yyyy").format(account.getDateOfBirth())
    );
    bean.set("couponIDs", account.getCouponIDs());

    jsonFileManager.modifyJSON("Account", userName, bean);
    System.out.println();
    System.out.println("Account created");
  }

  // Create new admin and shop manager account
  public void createAccInfo(String userName, String role) {
    Account account = new Account(userName, role);
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());

    jsonFileManager.modifyJSON("Account", userName, bean);
    System.out.println();
    System.out.println("Account created");
  }

  // Delete existing account
  public void delete(String userName) {
    Account account = getAccount(userName);
    jsonFileManager.deleteJSON("Account", account.getUserName());
  }

  // Update existing account
  public void update(String userName) {
    Account account = getAccount(userName);

    // Delete the original JSON file
    delete(userName);

    // Save the updated account details
    createAccInfo(account);
  }

  public Account getAccount(String userName) {
    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    if (accountJson == null) {
      System.out.println("Account " + userName + " does not exist");
      return null;
    } else {
      // Extract account details from JSON and return the Account object
      return extractAccountFromJson(accountJson);
    }
  }

  private Account extractAccountFromJson(JSONObject accountJson) {
    try {
      String userName = accountJson.getString("userName");
      String role = accountJson.getString("role");
      if (!role.equals("User")) {
        return new Account(userName, role);
      }
      int age = accountJson.getInt("age");
      String telNo = accountJson.getString("telNo");
      double points = accountJson.getDouble("points");
      Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy")
        .parse(accountJson.getString("dateOfBirth"));
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

  public boolean createAccount(String userName, String Password) {
    JSONObject jsonObject = jsonFileManager.searchJSON(userName);
    if (jsonObject != null) {
      System.out.println("User " + userName + " already exists");
      return false;
    }
    passwordManager.createNewPassword(userName, Password);
    return true;
  }

  public void generateDemoAccount() {
    boolean notExist;
    notExist = createAccount("admin", "admin");
    if (notExist) {
      createAccInfo("admin", "Admin");
    }
    notExist = createAccount("shop", "shop");
    if (notExist) {
      createAccInfo("shop", "Shop Manager");
    }
    notExist = createAccount("staff", "staff");
    if (notExist) {
      createAccInfo("staff", "Staff");
    }
    notExist = createAccount("user", "user");
    if (notExist) {
      createAccInfo("user", "User", 20, "12345678", "01/01/2000");
    }
  }
}
