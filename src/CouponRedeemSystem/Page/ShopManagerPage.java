package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import java.util.HashMap;
import java.util.Map;

public class ShopManagerPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Shop");
    System.out.println("2. Delete Shop");
    System.out.println("3. Create Staff Account");
    System.out.println("4. Delete Staff Account");
    System.out.println("5. Signout");
    System.out.println();
  }

  public void createShop() {
    ShopManager shopManager = ShopManager.getInstance();

    String shopName = strInput("shop name");
    Shop shop = shopManager.getShop(shopName);
    if (shop != null) {
      System.out.println();
      System.out.println("Shop already exists");
      return;
    }

    shopManager.createShop(shopName);

    System.out.println();
    System.out.println("Shop created");
  }

  public void deleteShop() {
    ShopManager shopManager = ShopManager.getInstance();

    String shopName = strInput("shop name");
    Shop shop = shopManager.getShop(shopName);
    if (shop == null) {
      System.out.println();
      System.out.println("Shop not found");
      return;
    }

    shopManager.deleteShop(shop);

    System.out.println();
    System.out.println("Shop deleted");
  }

  public void deleteAccount() {
    AccountManager accountManager = AccountManager.getInstance();
    ShopManager shopManager = ShopManager.getInstance();

    String userName = strInput("staff name");
    Account account = accountManager.getAccount(userName);
    if (account == null) {
      System.out.println();
      System.out.println("Account not found");
      return;
    }

    if (account.getRole().equals("Staff")) {
      Shop shop = shopManager.getShopByStaff(userName);
      shop.removeStaff(userName);
      shopManager.updateShop(shop);

      accountManager.deleteAccount(account);
      System.out.println();
      System.out.println("Account deleted");
    } else {
      System.out.println();
      System.out.println("This account is not a staff account");
    }
  }

  public void execute() {
    String cmd;
    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("1", () -> createShop());
    cmdMap.put("2", () -> deleteShop());
    cmdMap.put("3", () -> createAccount("Staff"));
    cmdMap.put("4", () -> deleteAccount());
    cmdMap.put("5", () -> System.out.println("Signout successfully"));
    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();
      cmdExecute(cmdMap, cmd);
    } while (!cmd.equals("5"));
  }
}
