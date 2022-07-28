package com.prac.jdbc.repository;

import com.prac.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
class MemberRepositoryV0Test {


    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV2",10000);
        repository.save(member);
//
//        //findById
//        Member findMember = repository.findById(member.getMemberId());
//        log.info("findMember={}",findMember);
//        Assertions.assertThat(findMember).isEqualTo(member);
//
//        //update money 10000 -> 20000
//
//        repository.update(member.getMemberId(),20000);
//        Member updateMember = repository.findById(member.getMemberId());
//        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);
//
//        //delete
//
//        repository.delete(member.getMemberId());
//        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
//                .isInstanceOf(NoSuchElementException.class); // 예외에 대한 검증 -> 해당 예외가 발생해야 true
//


    }
}