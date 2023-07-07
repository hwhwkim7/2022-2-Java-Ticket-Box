package org.tukorea.ticketbox.main;

import java.util.Scanner;
import org.tukorea.ticketbox.cinema.*;
import org.tukorea.ticketbox.util.ReceiptWriter;
import org.tukorea.ticketbox.util.Statistics;

public class TicketBox {
	private FamillyScreen famillyScreen;
	private AnimationScreen animationScreen;
	private PremiumScreen premiumScreen;
	public static final String ADMIN_PASSWORD = "admin";
	Scanner scan = new Scanner(System.in);
	
	public void initScreen() {
		// Screen(영화제목, 영화 줄거리, 티켓가격, 좌석(rowMax), 좌석(colMax))
		famillyScreen = new FamillyScreen("미니언즈2", "세계 최고의 슈퍼 악당을 꿈꾸는 미니보스 '그루'와 미니언들의 이야기", 12000, 10, 10);
		animationScreen = new AnimationScreen("보스베이비2", "베이비인줄 알았던 조카 '티나'가 알고 보니 베이비 주식회사 소속 임원!", 20000, 10, 10);
		premiumScreen = new PremiumScreen("인턴", "70세의 인턴 '벤'과 30세의 CEO '줄스'의 이야기", 50000, 5, 5);
	}
	
	public Screen selectScreen() {
		System.out.println("----------------------");
		System.out.println("-  상영관 선택 메인메뉴  -");
		System.out.println("----------------------");
		System.out.println("1. 가족 영화 1관");
		System.out.println("2. 애니메이션 고화질 영화 2관");
		System.out.println("3. 프리미엄 고화질 영화 3관 (맥주, 피자 등 음식 제공)");
		System.out.println("9. 관리자 메뉴");
		System.out.println("선택(1~3, 9)외 종료");
		System.out.println();
		System.out.print("상영관 선택 : ");
		Screen screen = null;
		try {
			int sel = scan.nextInt();
			switch(sel) {
				case 1:
					screen = famillyScreen;
					break;
				case 2:
					screen = animationScreen;
					break;
				case 3:
					screen = premiumScreen;
					break;
				case 9:
					managerMode();
			}
		} catch(Exception e) {}
		
		return screen;
	}
	
	void managerMode() { // 관리자 기능
		System.out.print("암호 입력 : ");
		String pw = scan.next();
		if(pw.equals(ADMIN_PASSWORD)) {
			System.out.println("----------------------");
			System.out.println("----   관리자 기능   ----");
			System.out.println("----------------------");
			
			int count = famillyScreen.getReceiptMap().size() + animationScreen.getReceiptMap().size() + premiumScreen.getReceiptMap().size();
			System.out.println("영화관 총 티켓 판매량 : " + count);
			System.out.println("가족 영화관 결제 총액 : " + Statistics.sum(famillyScreen.getReceiptMap()));
			System.out.println("애니메이션 영화관 결제 총액 : " + Statistics.sum(animationScreen.getReceiptMap()));
			System.out.println("프리미엄 영화관 결제 총액 : " + Statistics.sum(premiumScreen.getReceiptMap()));
			System.out.println();
			
			ReceiptWriter writer = new ReceiptWriter();
			System.out.println("가족 영화관 영수증 전체 출력");
			writer.printConsole(famillyScreen.getReceiptMap());
			System.out.println("애니메이션 영화관 영수증 전체 출력");
			writer.printConsole(animationScreen.getReceiptMap());
			System.out.println("프리미엄 영화관 영수증 전체 출력");
			writer.printConsole(premiumScreen.getReceiptMap());
			System.out.println();
		}
	}

}
