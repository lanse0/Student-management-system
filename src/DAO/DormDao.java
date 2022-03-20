package DAO;

import JAVABEAN.Dorm;
import JDBC.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DormDao {
    //根据楼栋获取所有宿舍
    public List<Dorm> getAllDorm(int build){
        String sql = "select * from dorm where build = ?";
        List<Dorm> dormList = new ArrayList<Dorm>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,build);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("id"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setStatus(rs.getString("status"));
                dormList.add(dorm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.release(rs,pstmt,conn);
        }
        return  dormList;
    }
}
