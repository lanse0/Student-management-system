package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.express;
import JDBC.DBUtils;
import UTIL.PageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ExpressDao {
    // 根据姓名返回快件信息
    public ArrayList<express> getExpressesByStudentname(String studentname) throws SQLException, ClassNotFoundException {
        ArrayList<express> expresses = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from express e,dorm d where e.dormitoryid=d.id and studentname = ? order by arrivetime desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentname);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            express exp = new express();
            exp.setStudentname(rs.getString("studentname"));
            Dorm dormitoryid = new Dorm();
            dormitoryid.setId(rs.getInt("dormitoryid"));
            dormitoryid.setBuild(rs.getInt("build"));
            dormitoryid.setNumber(rs.getInt("number"));
            dormitoryid.setStatus(rs.getString("status"));
            exp.setDorm(dormitoryid);
            exp.setArrivetime(rs.getTimestamp("arrivetime"));
            exp.setCollecttime(rs.getTimestamp("collecttime"));
            exp.setCollectnum(rs.getInt("collectnum"));
            exp.setCollectname(rs.getString("collectname"));
            exp.setCollectphoneid(rs.getString("collectphoneid"));
            expresses.add(exp);
        }
        return expresses;
    }

    //删除快件
    public boolean delExpress(String stuName,String arriveTime){
        String sql = "DELETE FROM express where studentname=? and DATE_FORMAT( arrivetime, '%Y-%m-%d %H:%i' ) = ?";
        Object[] params = {stuName,arriveTime};
        return DBUtils.executeUpdate(sql,params);
    }
    //根据学生姓名和宿舍获取学生对象
    public express getExprByColl(String stuName,String arriveTime){
        String sql = "select * from express e,dorm d where e.dormitoryid=d.id and studentname=? and DATE_FORMAT( arrivetime, '%Y-%m-%d %H:%i' ) = ?";
        express express = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,stuName);
            pstmt.setString(2,arriveTime);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                express = new express();
                express.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setStatus(rs.getString("status"));
                express.setDorm(dorm);
                express.setArrivetime(rs.getTimestamp("arrivetime"));
                express.setCollecttime(rs.getTimestamp("collecttime"));
                express.setCollectnum(rs.getInt("collectnum"));
                express.setCollectname(rs.getString("collectname"));
                express.setCollectphoneid(rs.getString("collectphoneid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return express;
    }

    // 根据条件查询总记录数
    public int getCountBySearch(Map filter,String dormBuild){
        int totalSize = 0;
        String sql = "select count(*) total from express e, dorm d where e.dormitoryid = d.id and d.build = "+dormBuild;
        if (filter.get("stuName") != null) {
            sql += " and studentname like '%" + filter.get("stuName") + "%'";
        }
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        if (filter.get("arriveTime") != null) {
            sql += " and DATE_FORMAT(arrivetime, '%Y-%m-%d') = '" + filter.get("arriveTime") + "'";
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

    // 根据用户名跟时间、宿舍查询快件 翻页
    public ArrayList<express> getExpressBySearch(Map filter, PageUtils page,String dormBuild) {
        String sql = "select * from express e, dorm d where e.dormitoryid = d.id and d.build = "+dormBuild;
        ArrayList<express> expressArrayList = new ArrayList<express>();
        if (filter.get("stuName") != null) {
            sql += " and studentname like '%" + filter.get("stuName") + "%'";
        }
        if (filter.get("dormId") != null) {
            sql += " and dormitoryid = " + filter.get("dormId");
        }
        if (filter.get("arriveTime") != null) {
            sql += " and DATE_FORMAT(arrivetime, '%Y-%m-%d') = '" + filter.get("arriveTime") + "'";
        }
        sql+=" order by arrivetime desc limit ?,?";
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
                express express = new express();
                express.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setStatus(rs.getString("status"));
                express.setDorm(dorm);
                express.setArrivetime(rs.getTimestamp("arrivetime"));
                express.setCollecttime(rs.getTimestamp("collecttime"));
                express.setCollectnum(rs.getInt("collectnum"));
                express.setCollectname(rs.getString("collectname"));
                express.setCollectphoneid(rs.getString("collectphoneid"));
                expressArrayList.add(express);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return expressArrayList;
    }

    // 快件出库
    public void modify(express express) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "update express set  collecttime = ?,  collectname = ?, collectphoneid = ? where dormitoryid = ? and studentname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setTimestamp(1, new java.sql.Timestamp(express.getCollecttime().getTime()));//出库时间
        pstmt.setString(2, express.getCollectname());//取件人
        pstmt.setString(3, express.getCollectphoneid());//取件号码
        pstmt.setInt(4, express.getDorm().getId());
        pstmt.setString(5, express.getStudentname());

        pstmt.executeUpdate();
    }

    // 快件入库
    public void submit(express express) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "insert into express(studentname,dormitoryid,arrivetime,collectnum) values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, express.getStudentname());
        pstmt.setInt(2, express.getDorm().getId());
        pstmt.setTimestamp(3, new java.sql.Timestamp(express.getArrivetime().getTime()));
        pstmt.setInt(4, express.getCollectnum());
        pstmt.executeUpdate();
    }
}
