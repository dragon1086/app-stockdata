package com.rocky.appstockdata.infrastructure;

import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.dto.DailyDealRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@SpringBootTest
public class MySQLConnectionTest {
    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/stock_db?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "gmrfyd12";

    @Autowired
    private StockDealRepository stockDealRepository;

    @Test
    public void testConnection() throws Exception {
        // DBMS에게 DB 연결 드라이버의 위치를 알려주기 위한 메소드
        Class.forName(DRIVER);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stockDealRepository(){
        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder().build());
        for(DailyDeal dailyDeal : dailyDealList){
            System.out.println("dailyDeal.toString() = " + dailyDeal.toString());
        }
    }
}
