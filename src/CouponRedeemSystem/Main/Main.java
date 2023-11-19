package CouponRedeemSystem.Main;

import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
	  DiscountManager.getInstance().updateActiveDiscountList();
  }
}
