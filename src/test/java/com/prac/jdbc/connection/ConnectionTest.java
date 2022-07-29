package com.prac.jdbc.connection;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.prac.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        // DriverManager로 직접 커넥션 획득
        //호출할때 마다 URL, USERNAME , PASSWORD 파라미터값이 필요
        Connection con1 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        Connection con2 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        log.info("connection={}, class={}",con1,con1.getClass());
        log.info("connection={}, class={}",con2,con2.getClass());

    }

    //'설정' 과 '사용' 을 분리하여 사용 - 코드의 유연성이 높아진다.
    @Test
    void dataSourceDriverManager(){
        // DriverManagerDataSource - 항상 새로운 커넥션을 획득
        // 파라미터값을 미리 셋팅
       DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,USERNAME,PASSWORD);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10); // 커넥션 풀 최대 생성 갯수 10개
        // 10개가 넘는다면 ?  pool 이 확보될때까지 block 처리된다.
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }


    // DEBUG com.zaxxer.hikari.pool.HikariPool - MyPool - After adding stats (total=10, active=2, idle=8, waiting=0)
    private void useDataSource(DataSource datasource) throws SQLException{
        // active = 2 , 활성화 된 커넥션 = 2
        Connection con1 = datasource.getConnection();
        Connection con2 = datasource.getConnection();
        log.info("connection={}, class={}",con1,con1.getClass());
        log.info("connection={}, class={}",con2,con2.getClass());

    }
}
