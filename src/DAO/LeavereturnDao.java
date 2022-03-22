package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.leavereturn;
import JDBC.DBUtils;
import UTIL.PageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class LeavereturnDao {
    // 修改离校返校信息
    public void modify(leavereturn leavereturn) throws SQLException, ClassNotFoundException {
        String sql = "update leavereturn set  studentname = ?,dormitoryid = ?, leavetime = ?, returntime = ? where dormitoryid = ? and studentname = ?";
        Connection conn = DBUtils.getConnection();
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

    // 根据条件查询总记录数
    public int getCountBySearch(Map filter, String dormBuild){
        int totalSize = 0;
        String sql = "SELECT COUNT(*) total FROM leavereturn l, dorm d  WHERE l.dormitoryid = d.id  AND d.build = "+dormBuild;
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
    public ArrayList<leavereturn> getLeaverBySearch(Map filter, PageUtils page, String dormBuild) {
        String sql = "SELECT * FROM leavereturn l, dorm d  WHERE l.dormitoryid = d.id  AND d.build = "+dormBuild;
        if (filter.get("stuName") != null) {
            sql += " and studentname like '%" + filter.get("stuName") + "%'";
        }
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        sql+=" order by leavetime desc limit ?,?";
        ArrayList<leavereturn> leavereturns = new ArrayList<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return leavereturns;
    }
    //根据姓名 离校时间获取记录
    public leavereturn getLeaverByModify(String stuName, String leaveTime) {
        String sql = "SELECT * FROM leavereturn l, dorm d  WHERE l.dormitoryid = d.id  AND studentname = ? AND DATE_FORMAT( leavetime, '%Y-%m-%d %H:%i' ) = ?";
        leavereturn leavereturn = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,stuName);
            pstmt.setString(2,leaveTime);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                leavereturn = new leavereturn();
                leavereturn.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
                leavereturn.setDorm(dorm);
                leavereturn.setLeavetime(rs.getTimestamp("leavetime"));
                leavereturn.setReturntime(rs.getTimestamp("returntime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return leavereturn;
    }
}
