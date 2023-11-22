package CouponRedeemSystem.System.Password;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

public class PasswordManager {

  private static PasswordManager instance;

  private EncryptionManager mgr = EncryptionManager.getInstance();

  private PasswordManager() {}

  public static PasswordManager getInstance() {
    if (instance == null) {
      instance = new PasswordManager();
    }

    return instance;
  }

  public String getPasswordRefTablePath() {
    return "Password\\ReferenceTable.json";
  }

  private JSONObject getPasswordRefTable() {
    CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
    File file = mgr.searchFile("ReferenceTable");
    if (file == null) {
      file = mgr.createJson("Password", "ReferenceTable");
    }
    return mgr.convertFileTextToJSON(file);
  }

  public boolean createNewPassword(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    String encryptedPassword = mgr.encryption(password);
    jsonObject.put(userName, encryptedPassword);

    return CRSJsonFileManager
      .getInstance()
      .modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  public boolean checkPasswordValid(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    String textBeforeEncrypt = jsonObject.getString(userName);
    if (textBeforeEncrypt == null) {
      System.out.println("Account is not found!");
      return false;
    }
    String text = mgr.decryption(textBeforeEncrypt);
    if (text.equals(password)) {
      return true;
    } else {
      System.out.println("Password is not correct!");
      return false;
    }
  }
}
