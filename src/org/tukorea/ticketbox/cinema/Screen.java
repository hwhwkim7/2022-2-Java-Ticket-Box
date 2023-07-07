package org.tukorea.ticketbox.cinema;

import java.util.*;

import org.tukorea.ticketbox.payment.*;

public abstract class Screen {
	protected int ticketPrice; // 티켓 가격
	protected int rowMax; // 좌석 행 최대 값
	protected int colMax; // 좌석 열 최대 값
	protected String movieName; // 상영중인 영화 제목
	protected String movieStory; // 상영중인 영화 줄거리
	protected MovieTicket [][] seatArray; // 좌석 2차원 배열
	Scanner scan = new Scanner(System.in);
	private HashMap<Integer, Receipt> receiptMap = new HashMap<Integer, Receipt>();
	
	public abstract void showMovieInfo(); // 영화 정보 보여주기
	
	public HashMap<Integer, Receipt> getReceiptMap() {
		return receiptMap;
	}

	Screen(){}
	
	Screen(String name, String story, int price, int row, int col) { // 생성자
		movieName = name;
		movieStory = story;
		ticketPrice = price;
		rowMax = row;
		colMax = col;
		seatArray = new MovieTicket[rowMax][colMax];
		for(int i=0; i<rowMax; i++) {
			for(int j=0; j<colMax; j++) {
				seatArray[i][j] = new MovieTicket();
				seatArray[i][j].setStatus(MovieTicket.SEAT_EMPTY_MARK);
			}
		}
	}
	
	public void showScreenMenu() { // 상영관 메뉴 보여주기
		System.out.println("----------------------");
		System.out.println("영화 메뉴 - " + movieName);
		System.out.println("----------------------");
		System.out.println("1. 상영 영화 정보");
		System.out.println("2. 좌석 예약 현황");
		System.out.println("3. 좌석 예약 하기");
		System.out.println("4. 예약 취소 하기");
		System.out.println("5. 좌석 결제 하기");
		System.out.println("6. 티켓 출력 하기");
		System.out.println("7. 메인 메뉴 이동");
	}
	
	public void showSeatMap() { // 상영관 좌석 예약 현황 보여주기
		System.out.println("\t[ 좌석 예약 현황 ]");
		System.out.print("\t");
		for(int i=0; i<colMax; i++) {
			System.out.print("[" + (i+1) + "] ");
		}
		System.out.println();
		for(int i=0; i<rowMax; i++) {
			System.out.print("[" + (i+1) + "]\t");
			for(int j=0; j<colMax; j++) {
				System.out.print("[" + seatArray[i][j].getStatus() + "] ");
			}
			System.out.println();
		}
	}
	
	// 랜덤으로 예약 번호 발급
	// 범위 : 100 부터 (총 좌석수 * 4 + 100) 까지 부여
	public void reserveTicket() {// 좌석 예약
		System.out.println("[ 좌석 예약 ]");
		System.out.print("좌석 행 번호 입력(1 ~ " + rowMax + ") : ");
		int rowNum = scan.nextInt();
		System.out.print("좌석 열 번호 입력(1 ~ " + colMax + ") : ");
		int colNum = scan.nextInt();
		int id = (int)(Math.random()*(rowMax*colMax*4 + 1) + 100);
		
		seatArray[rowNum-1][colNum-1].setReservedId(id);
		seatArray[rowNum-1][colNum-1].setSeat(rowNum, colNum);
		seatArray[rowNum-1][colNum-1].setStatus(MovieTicket.SEAT_RESERVED_MARK);
		
		System.out.println("행[" + seatArray[rowNum-1][colNum-1].getRow() + 
				"] 열[" + seatArray[rowNum-1][colNum-1].getCol() + "] " +
				seatArray[rowNum-1][colNum-1].getReservedId() + " 예약 번호로 접수되었습니다.");
	}
	
	private MovieTicket checkReservedId(int id) { // 예약 번호로 티켓 체크하기
		MovieTicket m = null;
		for(int i=0; i<rowMax; i++) {
			for(int j=0; j<colMax; j++) {
				if(seatArray[i][j].getReservedId() == id) {
					m = seatArray[i][j];
				}
			}
		}
		return m;
	}
	
	public void cancelReservation() { // 예약 취소
		System.out.println("[ 좌석 예약 취소 ]");
		System.out.print("좌석 예약 번호 입력 : ");
		try {
			int num = scan.nextInt();
			MovieTicket m = checkReservedId(num);
			if(m.getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				System.out.println("결제한 좌석은 취소가 불가능합니다.");
			} else {
				System.out.println("예약번호 " + m.getReservedId() + " 예약 취소 처리되었습니다.");
				seatArray[m.getRow()-1][m.getCol()-1].setStatus(MovieTicket.SEAT_EMPTY_MARK);
			}
		} catch (Exception e) {
			scan.nextLine();
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	public void payment() { // 결제 하기
		System.out.println("[ 좌석 예약 결제 ]");
		System.out.print("예약 번호 입력 : ");
		try {
			int num = scan.nextInt();
			MovieTicket m = checkReservedId(num);
			
			if(m == null) {
				throw new Exception();
			}
			
			System.out.println("----------------------");
			System.out.println("     결재 방식 선택     ");
			System.out.println("----------------------");
			System.out.println("1. 은행 이체");
			System.out.println("2. 카드 결제");
			System.out.println("3. 모바일 결제");
			System.out.print("결제 방식 입력(1~3) : ");
			
			Pay pay = null;
			int sel = scan.nextInt();
			String client;
			String number;
			
			switch(sel) {
			case 1:
				pay = new BankTransfer();
				System.out.println("[ 은행 이체 ]");
				System.out.print("이름 입력 : ");
				client = scan.next();
				System.out.print("은행 번호 입력(8자리수) : ");
				number = scan.next();
				break;
			case 2:
				pay = new CardPay();
				System.out.println("[ 카드 결제 ]");
				System.out.print("이름 입력 : ");
				client = scan.next();
				System.out.print("카드 번호 입력(10자리수) : ");
				number = scan.next();
				break;
			case 3:
				pay = new MobliePay();
				System.out.println("[ 모바일 결제 ]");
				System.out.print("이름 입력 : ");
				client = scan.next();
				System.out.print("핸드폰 번호 입력(11자리수) : ");
				number = scan.next();
				break;
			default:
				throw new Exception();
			}
			
			Receipt r = pay.charge(movieName, ticketPrice, client, number);
			receiptMap.put(m.getReservedId(), r); // 키(예약 번호) + Receipt 객체
			System.out.println(r.getPayMethod() + "가 완료되었습니다. : " + r.getTotalAmount() + "원");
			seatArray[m.getRow()-1][m.getCol()-1].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
			
		} catch (Exception e) {
			scan.nextLine();
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	public void printTicket() { // 티켓 영수증 출력
		System.out.println("[ 좌석 티켓 출력 ]");
		System.out.print("예약 번호 입력 : ");
		try {
			int num = scan.nextInt();
			MovieTicket m = checkReservedId(num);
			
			if(m == null) {
				throw new Exception();
			}
			
			System.out.println("----------------------");
			System.out.println("       Receipt       ");
			System.out.println("----------------------");
			Receipt r = receiptMap.get(m.getReservedId());
			System.out.println(r.toString());
			
		} catch(Exception e) {
			scan.nextLine();
			System.out.println("잘못된 입력입니다.");
		}
	
	}

}