package CouponRedeemSystem.Account.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CouponRedeemSystem.Coupon.model.Coupon;

public class Account {
	String userName;
	double points;
	Date dateOfBirth;
	List<Coupon> userOwnsCoupon;
	
	public Account(String userName,String dateOfBirth) throws ParseException {
		this.userName = userName;
		this.points = 0;
		this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
		userOwnsCoupon = new ArrayList<Coupon>();
	}


	public String getUserName() {
		return userName;
	}
	public int getAge() {
		return 18;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public List<Coupon> getCoupons() {
		return userOwnsCoupon;
	}
	public double getPoints() {
		return points;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.userOwnsCoupon = coupons;
	}
	public void setPoints(double score) {
		this.points = score;
	}
	
}
