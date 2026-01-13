package com.humancoffee.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CtrlScanner {
	private final static String MENU_OK_CANCEL = "선택 > 1. 확인 : 2. 취소";
	private final static String PHONE_FORMAT = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
	Scanner scanner = new Scanner(System.in);
	
	public int getIntByScanner() {
		int sel;
		String rcv;
		while(true) {
			rcv = scanner.nextLine();
			if(rcv.isEmpty()) {
				sel = 0;
				break;
			}else {
				try {
					sel = Integer.parseInt(rcv.trim());
					break;
				}catch(NumberFormatException e) {
					System.out.println("(0 ~ 9)숫자만 입력 부탁드립니다.");
					continue;
				}
			}
		}
		return sel;
	}
	
	public String getPhoneByScanner(String strFormat) {
		String rcv, format;
		format = strFormat;
		if(strFormat == null)
			format = PHONE_FORMAT;
		while(true) {
			rcv = scanner.nextLine();
			if(rcv.isEmpty())
				return "";
			if(rcv.matches(format))
				break;
			else {
				System.out.println("전화번호 형식이 아닙니다.\n다시 입력하시겠습니까?");
				if(menuOkCancel(null))
					continue;
				else
					return "";
			}
		}
		return rcv;
	}
	
	public String getStrByScanner() {
		String rcv;
		rcv = scanner.nextLine();
		return rcv;
	}
	
	public boolean menuOkCancel(String strMenu) {
//		System.out.println("1 : 확인 : 2 : 취소");
		String display = strMenu;
		if(display == null)
			display = MENU_OK_CANCEL;
		System.out.println(display);
		int sel = getIntByScanner();
		if(sel == 2)
			return false;
		return true;
	}
	
	public Date getDateByScanner(String dateFormat) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		String strDate;
		
		while(date == null) {
			strDate = scanner.nextLine();
			try {
				date = sdf.parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("입력방식: " + dateFormat + "\n잘못된 날짜 형식입니다.\n다시 입력하시겠습니까?");
				if(!menuOkCancel("선택 > 1. 확인 : 2. 취소"))
					break;
			}
			
		}
		return date;
	}
	
	public void close() {
		scanner.close();
	}
}
