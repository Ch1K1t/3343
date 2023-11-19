package CouponRedeemSystem.Role;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Role.model.Admin;
import CouponRedeemSystem.Role.model.Customer;
import CouponRedeemSystem.Role.model.Role;
import CouponRedeemSystem.Role.model.Staff;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;

public class RoleManager {
    private static RoleManager instance = null;

    private RoleManager() {}

    public static RoleManager getInstance() {
        if (instance == null) 
            instance = new RoleManager();
        return instance;
    }

    public Account setRole(Account account, String roleString) {
        Role role;
        switch (roleString.toLowerCase()) {
            case "admin":
            // How to check admin?
                role = new Admin();
                break;

            case "customer":
                role = new Customer();
                break;

            case "staff":
                System.out.println("Please input shop you are working in:");
                return null;

            default:
                System.out.println("Invalid role");
                //TODO: detect if return null then get input again
                return null;
        }
        account.setRole(role);
        return account;
    } 

    public Account setRole(Account account, String roleString, String shopName) {
        if (roleString.equals("staff")) {
            ShopManager shopManager = ShopManager.getInstance();
            Shop shop = shopManager.findShop(shopName);
            Role role = new Staff(shop);
            account.setRole(role);
        } else {
            System.out.println("Invalid role");
            return null;
        }
        return account;
    }
}
