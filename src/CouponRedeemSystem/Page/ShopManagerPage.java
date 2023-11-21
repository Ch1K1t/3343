package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.Shop.ShopManager;

public class ShopManagerPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("1. Create Shop");
    System.out.println("2. Remove Shop");
    System.out.println("3. Signout");
    System.out.println("4. Exit");
    System.out.println();
  }

  public void createShop() {
    ShopManager shopManager = ShopManager.getInstance();

    String shopName = strInput("shop name");
    shopManager.createShop(shopName);
  }

  public void removeShop() {}

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
          removeShop();
          break;
        case "3":
          System.out.println("Signout successfully");
          break;
        case "4":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("3"));
  }
}
