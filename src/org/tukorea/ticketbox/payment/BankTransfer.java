package org.tukorea.ticketbox.payment;

public class BankTransfer implements Pay {
	public static final double BANK_TRANSFER_COMMISION = 0.1;
	
	public Receipt charge(String product, double amount, String name, String number) {
		Receipt r = new Receipt();
		r.client = name;
		r.productName = product;
		r.payMethod = "은행 이체";
		r.payNumber = number;
		r.subTotalAmount = amount;
		r.totalAmount = amount + amount*BANK_TRANSFER_COMMISION;
		
		return r;
	}
}
