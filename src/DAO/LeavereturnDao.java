package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.leavereturn;
import JDBC.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeavereturnDao {
    // 根据姓名返回离校返校信息
    public ArrayList<leavereturn> getLeavereturnsByStudentname(String studentname) throws SQLException, ClassNotFoundException {
        ArrayList<leavereturn> leavereturns = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from leavereturn l, dorm d where l.dormitoryid=d.id and studentname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentname);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            leavereturn leavereturn = new leavereturn();
            leavereturn.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
            leavereturn.setDorm(dorm);
            leavereturn.setLeavetime(rs.getTimestamp("leavetime"));
            leavereturn.setReturntime(rs.getTimestamp("returntime"));
            leavereturns.add(leavereturn);
        }
        return  leavereturns;
    }

    // 得到所有离校返校信息
    public ArrayList<leavereturn> getAllLeavereturn(String dormBuild) throws SQLException, ClassNotFoundException {
        ArrayList<leavereturn> leavereturns = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from leavereturn l, dorm d where l.dormitoryid=d.id and d.build=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,dormBuild);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            leavereturn leavereturn = new leavereturn();
            leavereturn.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
            leavereturn.setDorm(dorm);
            leavereturn.setLeavetime(rs.getTimestamp("leavetime"));
            leavereturn.setReturntime(rs.getTimestamp("returntime"));
            leavereturns.add(leavereturn);
        }
        return  leavereturns;
    }
    // 修改离校返校信息
    public void modify(leavereturn leavereturn) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "update leavereturn set  studentname = ?,dormitoryid = ?, leavetime = ?, returntime = ? where dormitoryid = ? and studentname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,leavereturn.getStudentname());
        pstmt.setInt(2,leavereturn.getDorm().getId());
        pstmt.setTimestamp(3,new java.sql.Timestamp(leavereturn.getLeavetime().getTime()));
        pstmt.setTimestamp(4,new java.sql.Timestamp(leavereturn.getReturntime().getTime()));
        pstmt.setInt(5,leavereturn.getDorm().getId());
        pstmt.setString(6,leavereturn.getStudentname());
        pstmt.executeUpdate();
    }
    // 提交修改信息
    public void submit(leavereturn leavereturn) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "insert into leavereturn(studentname,dormitoryid,leavetime,returntime) values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,leavereturn.getStudentname());
        pstmt.setInt(2,leavereturn.getDorm().getId());
        pstmt.setTimestamp(3,new java.sql.Timestamp(leavereturn.getLeavetime().getTime()));
        pstmt.setTimestamp(4,new java.sql.Timestamp(leavereturn.getReturntime().getTime()));
        pstmt.executeUpdate();
    }
}
