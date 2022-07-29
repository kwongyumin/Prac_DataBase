package com.prac.jdbc.repository;

import com.prac.jdbc.connection.ConnectionConst;
import com.prac.jdbc.connection.DBConnectionUtil;
import com.prac.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.prac.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {


    MemberRepositoryV1 repository;

    @BeforeEach // @Test 보다 먼저 실행
    void beforeEach(){
        //기본 DriverManager - 항상 새로운 커넥션을 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,USERNAME,PASSWORD);
//
//        repository = new MemberRepositoryV1(dataSource);

        //커넥션 풀링
       HikariDataSource dataSource =  new HikariDataSource();
       dataSource.setJdbcUrl(URL);
       dataSource.setUsername(USERNAME);
       dataSource.setPassword(PASSWORD);
       repository = new MemberRepositoryV1(dataSource);

    }

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV3",10000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}",findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        //update money 10000 -> 20000

        repository.update(member.getMemberId(),20000);
        Member updateMember = repository.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete

        repository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class); // 예외에 대한 검증 -> 해당 예외가 발생해야 true



    }
}