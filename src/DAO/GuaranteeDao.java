package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.guarantee;
import JDBC.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class GuaranteeDao {
    // 得到所有维修信息
    public ArrayList<guarantee> getAllguarantee(String dormBuild) throws SQLException, ClassNotFoundException {
        ArrayList<guarantee> guarantees = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from guarantee g,dorm d where g.dormitoryid=d.id and d.build=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,dormBuild);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            guarantee guarantee = new guarantee();
            guarantee.setStudentname(rs.getString("studentname"));
            Dorm dormitoryid = new Dorm();
            dormitoryid.setId(rs.getInt("dormitoryid"));
            dormitoryid.setBuild(rs.getInt("build"));
            dormitoryid.setNumber(rs.getInt("number"));
            dormitoryid.setStatus(rs.getString("status"));
            guarantee.setDorm(dormitoryid);
            guarantee.setGoodsname(rs.getString("goodsname"));
            guarantee.setReason(rs.getString("reason"));
            guarantee.setGuaranteetime(rs.getDate("guaranteetime"));
            guarantee.setPhoneid(rs.getString("phoneid"));
            guarantee.setGuaranteestaus(rs.getString("guaranteestaus"));
            guarantees.add(guarantee);
        }
        return  guarantees;
    }

    //根据条件查询维修信息
    public ArrayList<guarantee> getGuaranteesBySearch(Map filter,String dormBuild){
        String sql = "select * from guarantee g,dorm d where g.dormitoryid=d.id and build = ?";
        if(filter.get("stuName")!=null){
            sql+=" and studentname like '%"+filter.get("stuName")+"%'";
        }
        if(filter.get("dormId")!=null){
            sql+=" and dormitoryid = "+filter.get("dormId");
        }
        ArrayList<guarantee> guaranteeArrayList = new ArrayList<guarantee>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,dormBuild);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                guarantee guarantee = new guarantee();
                guarantee.setStudentname(rs.getString("studentname"));
                    Dorm dorm = new Dorm();
                    dorm.setId(rs.getInt("dormitoryid"));
                    dorm.setBuild(rs.getInt("build"));
                    dorm.setNumber(rs.getInt("number"));
                    dorm.setStatus(rs.getString("status"));
                    guarantee.setDorm(dorm);
                guarantee.setGoodsname(rs.getString("goodsname"));
                guarantee.setReason(rs.getString("reason"));
                guarantee.setGuaranteetime(rs.getDate("guaranteetime"));
                guarantee.setPhoneid(rs.getString("phoneid"));
                guarantee.setGuaranteestaus(rs.getString("guaranteestaus"));
                guaranteeArrayList.add(guarantee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs,pstmt,conn);
        }
        return guaranteeArrayList;
    }

    public boolean delete(int dormId,String stuName,String goodsName){
        String sql = "delete from guarantee where dormitoryid = ? and studentname = ? and goodsname = ?";
        Object[] params = {dormId,stuName,goodsName};
        return DBUtils.executeUpdate(sql,params);
    }

    // 修改维修信息（维修完成）
    public boolean modify(String goodsName,int dormId){
        String sql = "update guarantee set guaranteetime = sysdate(), guaranteestaus = 1 where dormitoryid = ? and goodsname = ?";
        Object[] params = {dormId,goodsName};
        return DBUtils.executeUpdate(sql,params);
    }
    // 提交修改信息
    public void submit(guarantee guarantee) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "insert into guarantee(dormitoryid,studentname,goodsname,reason,phoneid,guaranteestaus) values(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,guarantee.getDorm().getId());
        pstmt.setString(2,guarantee.getStudentname());
        pstmt.setString(3,guarantee.getGoodsname());
        pstmt.setString(4,guarantee.getReason());
        pstmt.setString(5,guarantee.getPhoneid());
        pstmt.setString(6,guarantee.getGuaranteestaus());
        pstmt.executeUpdate();
    }

    // 根据寝室号查询维修信息
    public ArrayList<guarantee> getGuaranteesByDormitoryid(Dorm dormitoryid) throws SQLException, ClassNotFoundException {
        ArrayList<guarantee> guarantees = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from guarantee g,dorm d where g.dormitoryid=d.id and dormitoryid = ? order by guaranteestaus asc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, dormitoryid.getId());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            guarantee guarantee = new guarantee();
            guarantee.setStudentname(rs.getString("studentname"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setStatus(rs.getString("status"));
            guarantee.setDorm(dorm);
            guarantee.setGoodsname(rs.getString("goodsname"));
            guarantee.setReason(rs.getString("reason"));
            guarantee.setGuaranteetime(rs.getDate("guaranteetime"));
            guarantee.setPhoneid(rs.getString("phoneid"));
            guarantee.setGuaranteestaus(rs.getString("guaranteestaus"));
            guarantees.add(guarantee);
        }
        return  guarantees;
    }
}
