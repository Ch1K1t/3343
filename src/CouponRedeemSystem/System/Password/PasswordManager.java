package CouponRedeemSystem.System.Password;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

public class PasswordManager {

  private static PasswordManager instance;

  private EncryptionManager encryptionManager = EncryptionManager.getInstance();
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private PasswordManager() {}

  public static PasswordManager getInstance() {
    if (instance == null) {
      instance = new PasswordManager();
    }

    return instance;
  }

  public JSONObject getPasswordRefTable() {
    JSONObject jsonObject = jsonFileManager.searchJSON("ReferenceTable");
    if (jsonObject != null) {
      return jsonObject;
    } else {
      File file = jsonFileManager.createJSONFile("Password", "ReferenceTable");
      return jsonFileManager.convertFileTextToJSON(file);
    }
  }

  public boolean createNewPassword(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    String encryptedPassword = encryptionManager.encryption(password);
    jsonObject.put(userName, encryptedPassword);

    return jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  public boolean deletePassword(String userName) {
    JSONObject jsonObject = getPasswordRefTable();
    System.out.println(jsonObject.get(userName));
    if (jsonObject.get(userName) == null) {
      return false;
    }

    jsonObject.remove(userName);

    return jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  public boolean checkPasswordValid(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    Object textBeforeEncrypt = jsonObject.get(userName);
    if (textBeforeEncrypt == null) {
      System.out.println("Account is not found!");
      return false;
    }
    String text = encryptionManager.decryption(textBeforeEncrypt.toString());
    if (text.equals(password)) {
      return true;
    } else {
      System.out.println("Password is not correct!");
      return false;
    }
  }
}
