package org.tukorea.ticketbox.payment;

public class Receipt {
	String client; // 사용자 이름
	String productName; // 영화 상품 이름
	String payMethod; // 결제 수단
	String payNumber; // 결제 정보(번호)
	double subTotalAmount; // 커미션 제외한 금액
	double totalAmount; // 커미션 포함한 금액
	
	public String getProductName() {
		return productName;
	}
	
	public String getPayMethod() {
		return payMethod;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	@Override
	public String toString() { // 티켓 결제 내용 출력
		return "[ Received by : " + client + "]\n"
			 + "[ Product :     " + productName + "]\n"
			 + "[ PayMethod :   " + payMethod + "]\n"
			 + "[ payNumber :   " + payNumber + "]\n"
			 + "[ SubTotal :    " + subTotalAmount + "]\n"
			 + "[ Total :       " + totalAmount + "]";
	}
	
	
	public String toBackupString() { // 영수증 내용을 구분자(,)를 사용하여 한 줄에 출력
		return client + ", " + productName + ", " + payMethod + ", " + payNumber + ", " + subTotalAmount + ", " + totalAmount;
	}
}
