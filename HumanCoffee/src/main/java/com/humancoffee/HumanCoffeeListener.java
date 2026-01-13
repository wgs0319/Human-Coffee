package com.humancoffee;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//톰캣 버전에 따라 구동방식이 다를 수 있음.
@WebListener // 이 어노테이션을 추가하여 톰캣에 리스너를 등록합니다. 톰캣 10.1 버전은 jakarta 패키지를 사용하므로...
public class HumanCoffeeListener implements ServletContextListener{
 
 // HumanCoffee 인스턴스를 유지
 private HumanCoffee humanCoffeeInstance;

 @Override
 public void contextInitialized(ServletContextEvent sce) {
     // 웹 애플리케이션이 시작될 때 호출되는 메소드
     System.out.println("웹 애플리케이션 시작됨.");
     
     // 톰캣 시작 시 HumanCoffee 인스턴스 생성 및 스레드 구동
     humanCoffeeInstance = new HumanCoffee();
     humanCoffeeInstance.runMenu(); // 기존의 while(true) 루프는 제거
     
     // HumanCoffee 인스턴스를 ServletContext에 저장
     sce.getServletContext().setAttribute("HumanCoffee", humanCoffeeInstance);
     System.out.println("HumanCoffee-thread started.");
 }
 
 @Override
 public void contextDestroyed(ServletContextEvent sce) {
     // 웹 애플리케이션이 종료될 때 호출되는 메소드
     if (humanCoffeeInstance != null) {
         // 종료 로직을 추가하여 스레드 및 연결을 안전하게 종료
         humanCoffeeInstance.exit(); 
     }
     System.out.println("웹 애플리케이션 종료됨!");
 }
}
