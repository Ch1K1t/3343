package CouponRedeemSystem.System.Password;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordManager.
 */
public class PasswordManager {

  /** The instance. */
  private static PasswordManager instance;

  /** The encryption manager. */
  private EncryptionManager encryptionManager = EncryptionManager.getInstance();
  
  /** The json file manager. */
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  /**
   * Instantiates a new password manager.
   */
  private PasswordManager() {}

  /**
   * Gets the single instance of PasswordManager.
   *
   * @return single instance of PasswordManager
   */
  public static PasswordManager getInstance() {
    if (instance == null) {
      instance = new PasswordManager();
    }

    return instance;
  }

  /**
   * Gets the password ref table.
   *
   * @return the password ref table
   */
  public JSONObject getPasswordRefTable() {
    JSONObject jsonObject = jsonFileManager.searchJSON("ReferenceTable");
    if (jsonObject != null) {
      return jsonObject;
    } else {
      File file = jsonFileManager.createJSONFile("Password", "ReferenceTable");
      return jsonFileManager.convertFileTextToJSON(file);
    }
  }

  /**
   * Creates the new password.
   *
   * @param userName the user name
   * @param password the password
   */
  public void createNewPassword(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();

    String encryptedPassword = encryptionManager.encryption(password);
    jsonObject.put(userName, encryptedPassword);

    jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  /**
   * Delete password.
   *
   * @param userName the user name
   */
  public void deletePassword(String userName) {
    JSONObject jsonObject = getPasswordRefTable();

    jsonObject.remove(userName);

    jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  /**
   * Check password valid.
   *
   * @param userName the user name
   * @param password the password
   * @return the string
   */
  public String checkPasswordValid(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    Object textBeforeEncrypt = jsonObject.get(userName);
    if (textBeforeEncrypt == null) {
      return "not found";
    }
    String text = encryptionManager.decryption(textBeforeEncrypt.toString());
    if (text.equals(password)) {
      return "success";
    } else {
      return "not correct";
    }
  }
}
