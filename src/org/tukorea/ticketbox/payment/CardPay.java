package org.tukorea.ticketbox.payment;

public class CardPay implements Pay {
	public static final double CARD_COMMISION = 0.15;
	
	public Receipt charge(String product, double amount, String name, String number) {
		Receipt r = new Receipt();
		r.client = name;
		r.productName = product;
		r.payMethod = "카드 결제";
		r.payNumber = number;
		r.subTotalAmount = amount;
		r.totalAmount = amount + amount*CARD_COMMISION;
		
		return r;
	}
}