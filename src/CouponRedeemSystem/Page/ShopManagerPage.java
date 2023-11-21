package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

public class ShopManagerPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("1. Create Shop");
    System.out.println("2. Delete Shop");
    System.out.println("3. Create Staff Account");
    System.out.println("4. Delete Staff Account");
    System.out.println("5. Signout");
    System.out.println("6. Exit");
    System.out.println();
  }

  public void createShop() {
    ShopManager shopManager = ShopManager.getInstance();

    String shopName = strInput("shop name");
    shopManager.createShop(shopName);
  }

  public void deleteShop() {
    ShopManager shopManager = ShopManager.getInstance();

    String shopName = strInput("shop name");
    shopManager.deleteShop(shopName);
  }

  public void deleteAccount() {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    AccountManager accountManager = AccountManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    String username = strInput("staff name");
    Account account = accountManager.getAccount(username);
    if (account != null) {
      if (account.getRole().equals("Staff")) {
        File[] fileArr = new File("Data/Shop").listFiles();
        for (File file : fileArr) {
          JSONObject jsonObject = jsonFileManager.convertFileTextToJSON(file);
          if (jsonObject.getJSONArray("staffs").contains(username)) {
            Shop shop = shopManager.getShop(jsonObject.getString("shopName"));
            shop.removeStaffs(username);
            shopManager.updateShop(shop);
            break;
          }
        }
      } else {
        System.out.println();
        System.out.println("This account is not a staff account");
        return;
      }
    }

    accountManager.deleteAccount(username);
  }

  public void execute() {
    String cmd;
    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "1":
          createShop();
          break;
        case "2":
          deleteShop();
          break;
        case "3":
          createAccount("Staff");
          break;
        case "4":
          deleteAccount();
          break;
        case "5":
          System.out.println("Signout successfully");
          break;
        case "6":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("5"));
  }
}
