package org.tukorea.ticketbox.payment;

public class MobliePay implements Pay {
	public static final double MOBILE_COMMISION = 0.2;
	
	public Receipt charge(String product, double amount, String name, String number) {
		Receipt r = new Receipt();
		r.client = name;
		r.productName = product;
		r.payMethod = "모바일 결제";
		r.payNumber = number;
		r.subTotalAmount = amount;
		r.totalAmount = amount + amount*MOBILE_COMMISION;
		
		return r;
	}
}
