package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.fee;
import JDBC.DBUtils;
import UTIL.PageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FeeDao {
    // 根据宿舍号返回水电费信息信息 学生界面
    public ArrayList<fee> getFeesByStudentname(Dorm dormitoryid) throws SQLException, ClassNotFoundException {
        ArrayList<fee> fees = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from fee f,dorm d where f.dormitoryid=d.id and f.dormitoryid = ? order by year desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, dormitoryid.getId());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            fee fee = new fee();
            fee.setYear(rs.getString("year"));
            fee.setMonth(rs.getString("month"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setStatus(rs.getString("status"));
            fee.setDorm(dorm);
            fee.setElectricfee(rs.getString("electricfee"));
            fee.setElectricnum(rs.getString("electricnum"));
            fee.setWaterfee(rs.getString("waterfee"));
            fee.setWaternum(rs.getString("waternum"));
            fees.add(fee);
        }
        return  fees;
    }

    // 提交水电信息
    public void submit(fee fee) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "insert into fee(year,month,dormitoryid,electricfee,electricnum,waterfee,waternum) values(YEAR(SYSDATE()),?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,fee.getMonth());
        pstmt.setInt(2,fee.getDorm().getId());
        pstmt.setString(3,fee.getElectricfee());
        pstmt.setString(4,fee.getElectricnum());
        pstmt.setString(5,fee.getElectricfee());
        pstmt.setString(6,fee.getElectricnum());
        pstmt.executeUpdate();
    }

    //修改水电费信息
    public void modify(fee fee) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "update fee set month = ?,dormitoryid = ?, electricfee = ?, electricnum = ?, waterfee = ?, waternum = ? where dormitoryid = ? and month = ? and year = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,fee.getMonth());
        pstmt.setInt(2,fee.getDorm().getId());
        pstmt.setString(3,fee.getElectricfee());
        pstmt.setString(4,fee.getElectricnum());
        pstmt.setString(5,fee.getElectricfee());
        pstmt.setString(6,fee.getElectricnum());
        pstmt.setInt(7,fee.getDorm().getId());
        pstmt.setString(8,fee.getMonth());
        pstmt.setString(9,fee.getYear());
        pstmt.executeUpdate();
    }

    //获取表中所有学年
    public ArrayList getAllYear(){
        String sql = "SELECT year FROM fee GROUP BY year";
        ArrayList yearList = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                yearList.add(rs.getString("year"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.release(rs,pstmt,conn);
        }
        return yearList;
    }

    //根据学年 月份 宿舍获取水电费
    public fee getFee(String year,String month,String dormId) {
        String sql = "select * from fee f,dorm d where f.dormitoryid=d.id and dormitoryid = ? and year = ? and month = ?";
        fee fee = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,dormId);
            pstmt.setString(2,year);
            pstmt.setString(3,month);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fee = new fee();
                fee.setYear(rs.getString("year"));
                fee.setMonth(rs.getString("month"));
                    Dorm dorm = new Dorm();
                    dorm.setId(rs.getInt("dormitoryid"));
                    dorm.setBuild(rs.getInt("build"));
                    dorm.setNumber(rs.getInt("number"));
                    dorm.setStatus(rs.getString("status"));
                fee.setDorm(dorm);
                fee.setElectricfee(rs.getString("electricfee"));
                fee.setElectricnum(rs.getString("electricnum"));
                fee.setWaterfee(rs.getString("waterfee"));
                fee.setWaternum(rs.getString("waternum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return fee;
    }

    // 根据条件查询总记录数
    public int getCountBySearch(Map filter, String dormBuild){
        int totalSize = 0;
        String sql = "select count(*) total from fee f,dorm d where f.dormitoryid=d.id and d.build = "+dormBuild;
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        if (filter.get("year") != null) {
            sql += " and `year` = " + filter.get("year");
        }
        if (filter.get("month") != null) {
            sql += " and month = " + filter.get("month");
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

    // 根据条件返回水电费 翻页
    public ArrayList<fee> getFeeBySearch(Map filter, PageUtils page, String dormBuild) {
        String sql = "select * from fee f,dorm d where f.dormitoryid=d.id and d.build = "+dormBuild;
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        if (filter.get("year") != null) {
            sql += " and `year` = " + filter.get("year");
        }
        if (filter.get("month") != null) {
            sql += " and month = " + filter.get("month");
        }
        sql+=" order by year desc limit ?,?";
        ArrayList<fee> fees = new ArrayList<>();
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
                fee fee = new fee();
                fee.setYear(rs.getString("year"));
                fee.setMonth(rs.getString("month"));
                    Dorm dorm = new Dorm();
                    dorm.setId(rs.getInt("dormitoryid"));
                    dorm.setBuild(rs.getInt("build"));
                    dorm.setNumber(rs.getInt("number"));
                    dorm.setStatus(rs.getString("status"));
                fee.setDorm(dorm);
                fee.setElectricfee(rs.getString("electricfee"));
                fee.setElectricnum(rs.getString("electricnum"));
                fee.setWaterfee(rs.getString("waterfee"));
                fee.setWaternum(rs.getString("waternum"));
                fees.add(fee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return fees;
    }
}