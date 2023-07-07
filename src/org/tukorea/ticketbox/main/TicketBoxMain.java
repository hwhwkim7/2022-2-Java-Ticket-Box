package org.tukorea.ticketbox.main;

import java.util.Scanner;

import org.tukorea.ticketbox.cinema.Screen;


public class TicketBoxMain {
	public static void main(String[] args) {
		TicketBox ticketBox = new TicketBox();
		Scanner scan = new Scanner(System.in);
		Screen screen = null;
		boolean mainMenu = true; // 상영관 선택 메뉴 사용
		ticketBox.initScreen(); // 3개의 스크린 객체를 생성
		
		while(true) {
			if(mainMenu) {
				screen = ticketBox.selectScreen();
				if(screen == null) {
					System.out.print("안녕히 가세요!");
					scan.close();
					System.exit(0);
				}
				mainMenu = false;
			}
			
			screen.showScreenMenu();
			System.out.print(" 메뉴를 선택하세요 >> ");
			try {
				int select = scan.nextInt();
				switch(select) {
					case 1: // 스크린 상영 영화 정보 보기
						screen.showMovieInfo();
						break;
					case 2: // 좌석 예약 현황
						screen.showSeatMap();
						break;
					case 3: // 좌석 예약 하기
						screen.reserveTicket();
						break;
					case 4: // 좌석 예약 하기
						screen.cancelReservation();
						break;
					case 5: // 좌석 예약 결제 하기
						screen.payment();
						break;
					case 6: // 티켓 출력 하기
						screen.printTicket();
						break;
					case 7: // 메인 메뉴 이동
						mainMenu = true;
						break;
				}
			} catch(Exception e) {
				scan.nextLine();
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
}
