package CouponRedeemSystem.Main;

import java.io.IOException;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.Password.PasswordManager;

public class Main {

  public static void main(String[] args) throws IOException {
    new HomePage().execute();
  }
}
