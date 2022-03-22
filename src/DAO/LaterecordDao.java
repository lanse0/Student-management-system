package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.laterecord;
import JDBC.DBUtils;
import UTIL.PageUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class LaterecordDao {
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

    public boolean del(String stuName,String latTime){
        String sql = "DELETE FROM laterecord where studentname=? and DATE_FORMAT( latetime, '%Y-%m-%d %H:%i' ) = ?";
        Object[] params = {stuName,latTime};
        return DBUtils.executeUpdate(sql,params);
    }

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

    // 根据条件查询总记录数
    public int getCountBySearch(Map filter, String dormBuild){
        int totalSize = 0;
        String sql = "SELECT COUNT(*) total FROM laterecord l, dorm d  WHERE l.dormitoryid = d.id  AND d.build = "+dormBuild;
        if (filter.get("stuName") != null) {
            sql += " and studentname like '%" + filter.get("stuName") + "%'";
        }
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalSize = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }

        return totalSize;
    }

    // 根据姓名 宿舍查询离校返校 翻页
    public ArrayList<laterecord> getLaterBySearch(Map filter, PageUtils page, String dormBuild) {
        String sql = "SELECT * FROM laterecord l, dorm d  WHERE l.dormitoryid = d.id  AND d.build = "+dormBuild;
        if (filter.get("stuName") != null) {
            sql += " and studentname like '%" + filter.get("stuName") + "%'";
        }
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        sql+=" order by latetime desc limit ?,?";
        ArrayList<laterecord> laterecords = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,(page.getCurrPage()-1)*page.getPageSize());
            pstmt.setInt(2,page.getPageSize());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return laterecords;
    }

}
