package com.humancoffee.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import com.humancoffee.HumanCoffee;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class Common {
	final static String DEV_SUB_FOLDER = "\\src\\main\\webapp\\";
	public static String strProjectPath = null;
	
	//	날짜시간형 문자를 Date타입으로 변환
	public Date getTypeStrToDate(String datetime) {
		Date date = null;
		String exception;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyHHddHHmmss");
		try {
			date = sdf.parse(datetime);
		}catch(Exception e) {
			exception = getStackTraceAsString(e);
			System.out.println("getTypeStrToDate Exception :\n" + exception);
		}
		return date;
	}
	
	//	Date 타입을 문자형으로 변환
	public String getTypeDateToStr(Date date) {
		String strdate;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyHHddHHmmss");
		strdate = sdf.format(date);
		return strdate;
	}
	
	//	DB의 CLOB 타입을 문자열로 변환
	public String getClobToString(Object obj) {
		String str = "";
		if(obj instanceof java.sql.Clob) {
			java.sql.Clob clob = (java.sql.Clob) obj;
			if(clob != null) {
				try {
					// CLOB 데이터를 문자열로 변환
					str = clob.getSubString(1, (int) clob.length());
				} catch (SQLException e) {
					str = getStackTraceAsString(e);
					System.out.println("ClobToString Exception\n : " + str);
				}
			}
		}else if (obj != null) {
			// CLOB 타입이 아닐 경우를 대비
			str = Objects.toString(obj, "");
		}
		return str;
	}
	
	//	Exception 발생 시 문자열로 변환
//	public static String getStackTraceAsString(Throwable throwable) {
	public String getStackTraceAsString(Throwable throwable) {
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
	}
	
	//	yyyyMMdd + Sequence(2) : 입력값은 현재 최대의 ID를 입력.
	//	시스템의 현재날짜와 입력값의 날짜가 동일하다면 Sequence를 1증가하여 생성.
	//	날짜가 다르다면 Sequence는 01 로 생성.
	public String generateDateSequenceId10(String nowMaxId) {	// Product, Order 테이블의 ID 생성 함수
		LocalDateTime nowDateTime = LocalDateTime.now();
		int cnt = 1;
		String strId = String.format("%04d%02d%02d", 
				nowDateTime.getYear(), nowDateTime.getMonthValue(), nowDateTime.getDayOfMonth(), nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());
		
		if(nowMaxId != null && nowMaxId.substring(0, strId.length()).equals(strId)) {
			String strSequence;
			strSequence = nowMaxId.substring(strId.length());
			cnt = Integer.parseInt(strSequence);
			cnt++;
		}
		return (strId + String.format("%02d", cnt));
	}
	
	public String generateDateSequenceId16(String nowMaxId) {	// Product, Order 테이블의 ID 생성 함수
		LocalDateTime nowDateTime = LocalDateTime.now();
		int cnt = 1;
		String strId = String.format("%04d%02d%02d", 
				nowDateTime.getYear(), nowDateTime.getMonthValue(), nowDateTime.getDayOfMonth());
		
		if(nowMaxId != null && nowMaxId.substring(0, strId.length()).equals(strId)) {
			String strSequence;
			strSequence = nowMaxId.substring(strId.length());
			cnt = Integer.parseInt(strSequence);
			cnt++;
		}
		return (strId + String.format("%08d", cnt));
	}
	
	public String generateDateTimeSequenceId16(String nowMaxId) {	// Product, Order 테이블의 ID 생성 함수
		LocalDateTime nowDateTime = LocalDateTime.now();
		int cnt = 1;
		String strId = String.format("%04d%02d%02d%02d%02d%02d", 
				nowDateTime.getYear(), nowDateTime.getMonthValue(), nowDateTime.getDayOfMonth(), nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());
		
		if(nowMaxId != null && nowMaxId.substring(0, strId.length()).equals(strId)) {
			String strSequence;
			strSequence = nowMaxId.substring(strId.length());
			cnt = Integer.parseInt(strSequence);
			cnt++;
		}
		return (strId + String.format("%02d", cnt));
	}
	
	public String generateDateTimeSequenceId20(String nowMaxId) {	// Product, Order 테이블의 ID 생성 함수
		LocalDateTime nowDateTime = LocalDateTime.now();
		int cnt = 1;
		String strId = String.format("%04d%02d%02d%02d%02d%02d", 
				nowDateTime.getYear(), nowDateTime.getMonthValue(), nowDateTime.getDayOfMonth(), nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());
		
		if(nowMaxId != null && nowMaxId.substring(0, strId.length()).equals(strId)) {
			String strSequence;
			strSequence = nowMaxId.substring(strId.length());
			cnt = Integer.parseInt(strSequence);
			cnt++;
		}
		return (strId + String.format("%06d", cnt));
	}
	
	//	파일을 저장할 때 개발모드의 경우 절대경로로 해야 함.
	//	밑의 readFileToString()는 해당 경로의 파일을 읽어 내용을 리턴하는데 동일함.  
	public static String getProjectPath(String projectPath, String projectName) {
		
		if(strProjectPath == null) {
			System.out.println("projectPath: " + projectPath);
			System.out.println("projectName: " + projectName);
			System.out.println("getProperty: " + System.getProperty("user.dir"));
			int idx = projectPath.indexOf("\\.", 0);
			if(idx >= 0) {
				//	개발계
				String	myPath = projectPath.substring(0, idx);
				idx = projectName.indexOf("/");
				projectName = projectName.substring(idx + 1);
				myPath += "\\" + projectName;
				myPath += DEV_SUB_FOLDER;
				System.out.println("myPath: " + myPath);
				strProjectPath = myPath;
			}
		}
		return strProjectPath;
	}
	
	public static String readFileToString(String filePath) throws IOException {
		// Path 객체를 생성하여 파일 경로를 나타냅니다.
		System.out.println("filePath: " + filePath);
//		filePath = "C:\\ThisIsJava\\workspace\\starbucks_web2\\src\\main\\webapp\\uploads\\html_tag정리.txt";
        Path path = Paths.get(filePath);
        // 파일이 존재하지 않으면 예외를 던집니다.
        if (!Files.exists(path)) {
            throw new IOException("파일을 찾을 수 없습니다: " + filePath);
        }

        // Files.readAllLines()를 사용하여 파일의 모든 라인을 읽어옵니다.
        // 그리고 Collectors.joining()을 사용하여 모든 라인을 하나의 문자열로 합칩니다.
        // 이 방법은 작은 파일에 적합하며, 대용량 파일의 경우 메모리 문제가 발생할 수 있습니다.
        try (var lines = Files.lines(path)) {
            return lines.collect(Collectors.joining(System.lineSeparator()));
        }
	}
	
	public Blob getPartToBlob(Part part, ServletContext servletContext) {
		// ServletContext에서 HumanCoffee 객체를 가져옴
		HumanCoffee hcInstance;
		Blob blob = null;
		try {
			hcInstance = (HumanCoffee)servletContext.getAttribute("HumanCoffee");
			InputStream is = null;
			is = part.getInputStream();
			
			blob = hcInstance.oraConn.conn.createBlob();
			OutputStream os = blob.setBinaryStream(1);
			byte[] bytes = is.readAllBytes();
			os.write(bytes);
			os.close();
			is.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blob;
	}
}
