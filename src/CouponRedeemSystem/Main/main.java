package CouponRedeemSystem.Main;

import java.io.IOException;

import CouponRedeemSystem.System.File.FileManager;
import CouponRedeemSystem.System.File.model.TextFile;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileManager mgr = FileManager.getInstance();
		TextFile textFile = new TextFile("0001", "Tony:1111");
		mgr.modifyFile("Password", textFile);
	}

}
