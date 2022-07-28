package com.prac.jdbc.repository;

/*
    JDBC - DriverManager 사용
 */

import com.prac.jdbc.connection.DBConnectionUtil;
import com.prac.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException{
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate();
            return member;

        }catch (SQLException e){
            log.error("db error", e);
            throw e;

        }finally {
            close(con,pstmt,null);
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            rs = pstmt.executeQuery();

            //next()호출 시 다음 데이터가 있는지 없는지 확인.
            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else {
                throw new NoSuchElementException("member not fount memberId = "+ memberId);
            }


        }catch (SQLException e){
            log.error("db error", e);
            throw e;

        }finally {
            close(con,pstmt,rs);
        }
    }

    public void update(String memberId, int money){
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,money);
            pstmt.setString(2,memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);

        }catch (SQLException e){
            log.error("db error", e);
        }finally {
            close(con,pstmt,null);
        }
    }


    public void delete(String memberId){
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            pstmt.executeUpdate();


        }catch (SQLException e){
            log.error("db error", e);
        }finally {
            close(con,pstmt,null);
        }
    }


    //사용한 자원들은 닫아준다.
    private void close(Connection con, Statement stmt, ResultSet rs){
    // stmt , pstmt 파라미터값을 바인딩하느냐 , 그대로 던지느냐의 차이.
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }

        if(stmt != null){
            try{
                stmt.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }

        if(con != null){
            try{
                con.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }


    }


    private Connection getConnection(){
        return DBConnectionUtil.getConnection();
    }
}
