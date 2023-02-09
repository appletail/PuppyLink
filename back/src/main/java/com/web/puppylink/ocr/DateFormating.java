//package com.web.puppylink.ocr;
//
//public class DateFormating {
//	public static void main(String[] args) {
//		String engDate = "19NOV18(MON) 05:30 (Local Time)";
//		String korDate = "19NOV18(월) 05:30 (Local Time)";		
//		dateFormat(engDate);
//		dateFormat(korDate);
//	}
//		public static String dateFormat(String date) {
//		// [월 영문 표기] 1월:JAN 2월:FEB 3월:MAR 4월:APR 5월:MAY 6월:JUN 7월:JUL 8월:AUG 9월:SEP 10월:OCT 11월:NOV 12월:DEC
//		
//		// STEP1. 공백 및 특수문자 제거
//		String trim = date.replaceAll(" ", "");
//		trim = trim.replaceAll("[():]", "");
//		trim = trim.replaceAll("(LocalTime)", "");
//		
//		// STEP2. 영문 요일 및 한글 요일 제거
//		String[] weeks = {"월", "화", "수", "목", "금", "토", "일", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
//		for(int i=0; i<weeks.length; i++) {
//			trim = trim.replaceAll(weeks[i], "");
//		}
//		System.out.println(trim);
//		
//		// STEP3. Date 정보 추출
//		String year = Integer.toString(2000 + Integer.parseInt(trim.substring(5, 7))); 
//		String month = trim.substring(2, 5);
//		String day = trim.substring(0, 2);
//		String hour = trim.substring(7, 9);
//		String minute = trim.substring(9, 11);
//		String second = "00";
//		
//		// STEP4. 영문 월 숫자 변환
//		String[] trans = {"", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
//		for(int i=0; i<trans.length; i++) {
//			if(trans[i].equals(month)) {
//				if(i<10) {
//					month = "0";
//					month += Integer.toString(i);
//				}
//				else {
//					month = Integer.toString(i);					
//				}
//			}
//		}
//		
//		// STEP5. 최종 문자열 형태 구성
//		String time = year + "-" + month + "-" + day +" "+ hour +":" + minute+":" + second;
//		System.out.println(time);
//		return time;
//	}
//}
