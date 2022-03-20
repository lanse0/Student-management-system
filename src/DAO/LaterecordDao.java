package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.laterecord;
import JDBC.DBUtils;

import java.sql.*;
import java.util.ArrayList;

public class LaterecordDao {
    // 根据姓名返回晚归信息
    public ArrayList<laterecord> getLaterecordsByStudentname(String studentname) throws SQLException, ClassNotFoundException {
        ArrayList<laterecord> laterecords = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from laterecord l, dorm d where l.dormitoryid=d.id and studentname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentname);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            laterecord laterecord = new laterecord();
            laterecord.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
            laterecord.setDorm(dorm);
            laterecord.setLatetime(rs.getTimestamp("latetime"));
            laterecord.setReason(rs.getString("reason"));
            laterecords.add(laterecord);
        }
        return  laterecords;
    }

    // 得到所有晚归信息
    public ArrayList<laterecord> getAllLaterecords(String dormBuild) throws SQLException, ClassNotFoundException {
        ArrayList<laterecord> laterecords = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from laterecord l, dorm d where l.dormitoryid=d.id and d.build=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,dormBuild);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            laterecord laterecord = new laterecord();
            laterecord.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
            laterecord.setDorm(dorm);
            laterecord.setLatetime(rs.getTimestamp("latetime"));
            laterecord.setReason(rs.getString("reason"));
            laterecords.add(laterecord);
        }
        return  laterecords;
    }
    // 修改晚归信息
    public void modify(laterecord laterecord) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "update laterecord set  studentname = ?,dormitoryid = ?, latetime = ?, reason = ? where dormitoryid = ? and studentname = ? and latetime = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,laterecord.getStudentname());
        pstmt.setInt(2,laterecord.getDorm().getId());
//        pstmt.setDate(3,new java.sql.Date(laterecord.getLatetime().getTime()));
        pstmt.setTimestamp(3,new Timestamp(laterecord.getLatetime().getTime()));//setTimestamp保留时分秒
        pstmt.setString(4,laterecord.getReason());
        pstmt.setInt(5,laterecord.getDorm().getId());
        pstmt.setString(6,laterecord.getStudentname());
//        pstmt.setDate(7,new java.sql.Date(laterecord.getLatetime().getTime()));
        pstmt.setTimestamp(7,new Timestamp(laterecord.getLatetime().getTime()));//setTimestamp保留时分秒
        pstmt.executeUpdate();
    }
    // 提交晚归信息
    public void submit(laterecord laterecord) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "insert into laterecord(studentname,dormitoryid,latetime,reason) values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,laterecord.getStudentname());
        pstmt.setInt(2,laterecord.getDorm().getId());
//        pstmt.setDate(3,new java.sql.Date(laterecord.getLatetime().getTime()));
        pstmt.setTimestamp(3,new Timestamp(laterecord.getLatetime().getTime()));//setTimestamp保留时分秒
        pstmt.setString(4,laterecord.getReason());
        pstmt.executeUpdate();
    }
}
