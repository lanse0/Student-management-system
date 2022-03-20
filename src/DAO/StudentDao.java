package DAO;

import JAVABEAN.Dorm;
import JAVABEAN.student;
import JDBC.DBUtils;
import sun.security.pkcs11.Secmod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {
    // 根据用户名得到 进行登陆验证
    public ResultSet selectStudent(String username) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        ;
        PreparedStatement pstmt = null;
        String sql = "select * from student s,dorm d where s.dormitoryid = d.id and username = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    // 根据名字返回宿舍号
    public Dorm getDormirotyByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student s,dorm d where s.dormitoryid=d.id and username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        Dorm dormitoryid = null;
        if (rs.next()) {
            dormitoryid = new Dorm();
            dormitoryid.setId(rs.getInt("dormitoryid"));
            dormitoryid.setBuild(rs.getInt("build"));
            dormitoryid.setNumber(rs.getInt("number"));
            dormitoryid.setStatus(rs.getString("status"));
        }
        return dormitoryid;
    }

    // 根据名字返回学号
    public String getStudentidByStudentname(String studentname) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student where studentname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentname);
        ResultSet rs = pstmt.executeQuery();

        String studentid = null;
        while (rs.next()) {
            studentid = rs.getString("studentid");
        }
        return studentid;
    }

    //根据学号返回学生
    public student getStudentById(int id) {
        String sql = "select * from student s, dorm d where s.dormitoryid = d.id and studentid=?";
        student stu = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                stu = new student();
                stu.setStudentid(rs.getString("studentid"));
                stu.setStudentname(rs.getString("studentname"));
                stu.setMajor(rs.getString("major"));
                stu.setGender(rs.getString("gender"));
                stu.setDepartment(rs.getString("department"));
                stu.setClasses(rs.getString("classes"));
                Dorm dorm = new Dorm();
                dorm.setId(rs.getInt("dormitoryid"));
                dorm.setBuild(rs.getInt("build"));
                dorm.setNumber(rs.getInt("number"));
                dorm.setStatus(rs.getString("status"));
                stu.setDorm(dorm);
                stu.setPhoneid(rs.getString("phoneid"));
                stu.setEntrytime(rs.getDate("entrytime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pstmt, conn);
        }
        return stu;
    }

    // 根据宿舍号返回宿舍成员
    public ArrayList<student> getStudentsByDormitoryid(Dorm dormitoryid) throws SQLException, ClassNotFoundException {
        ArrayList<student> students = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student s, dorm d where s.dormitoryid=d.id and dormitoryid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, dormitoryid.getId());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            student stu = new student();
            stu.setStudentid(rs.getString("studentid"));
            stu.setStudentname(rs.getString("studentname"));
            stu.setMajor(rs.getString("major"));
            stu.setGender(rs.getString("gender"));
            stu.setDepartment(rs.getString("department"));
            stu.setClasses(rs.getString("classes"));
            Dorm dorm = new Dorm();
            dorm.setId(rs.getInt("dormitoryid"));
            dorm.setBuild(rs.getInt("build"));
            dorm.setNumber(rs.getInt("number"));
            dorm.setStatus(rs.getString("status"));
            stu.setDorm(dorm);
            stu.setPhoneid(rs.getString("phoneid"));
            stu.setEntrytime(rs.getDate("entrytime"));
            students.add(stu);
        }
        return students;
    }

    // 管理员得到所有学生
    public ArrayList<student> getAllStudents(String dormBuild) throws SQLException, ClassNotFoundException {
        ArrayList<student> students = new ArrayList<>();
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student s, dorm d where s.dormitoryid = d.id and d.build=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, dormBuild);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            student stu = new student();
            stu.setStudentid(rs.getString("studentid"));
            stu.setStudentname(rs.getString("studentname"));
            stu.setMajor(rs.getString("major"));
            stu.setGender(rs.getString("gender"));
            stu.setDepartment(rs.getString("department"));
            stu.setClasses(rs.getString("classes"));
            Dorm dorm = new Dorm();
            dorm.setId(rs.getInt("dormitoryid"));
            dorm.setBuild(rs.getInt("build"));
            dorm.setNumber(rs.getInt("number"));
            dorm.setStatus(rs.getString("status"));
            stu.setDorm(dorm);
            stu.setPhoneid(rs.getString("phoneid"));
            stu.setEntrytime(rs.getDate("entrytime"));
            stu.setUsername(rs.getString("username"));
            stu.setPassword(rs.getString("password"));
            students.add(stu);
        }
        return students;
    }

    // 根据学号得到名字
    public String getStudentnameByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student where username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        String studentname = null;
        while (rs.next()) {
            studentname = rs.getString("studentname");
        }
        return studentname;
    }

    // 根据条件查询得到学生
    public ArrayList<student> getStudentsByCondition(student stu,String dormBuild) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtils.getConnection();
        String sql = "select * from student s, dorm d where s.dormitoryid = d.id and d.build=?";
        ArrayList<String> parm = new ArrayList<>();
        parm.add(dormBuild);
        ArrayList<student> students = new ArrayList<>();

        String studentid = stu.getStudentid();
        String studentname = stu.getStudentname();
        String major = stu.getMajor();
        String department = stu.getDepartment();
        String classes = stu.getClasses();
        Dorm dormitoryid = stu.getDorm();
        String phoneid = stu.getPhoneid();

        if (studentid != null && !"".equals(studentid)) {
            sql += " and studentid = ?";
            parm.add(studentid);
        }
        if (studentname != null && !"".equals(studentname)) {
            sql += " and studentname = ?";
            parm.add(studentname);
        }
        if (major != null && !"".equals(major)) {
            sql += " and major = ?";
            parm.add(major);
        }
        if (department != null && !"".equals(department)) {
            sql += " and department = ?";
            parm.add(department);
        }
        if (classes != null && !"".equals(classes)) {
            sql += " and classes = ?";
            parm.add(classes);
        }
        if (dormitoryid != null) {
            sql += " and s.dormitoryid = ?";
            parm.add(dormitoryid.getId() + "");
        }
        if (phoneid != null && !"".equals(phoneid)) {
            sql += " and phoneid = ?";
            parm.add(phoneid);
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("sql :" + sql);
        for (int i = 0; i < parm.size(); i++) {
            pstmt.setString(i + 1, parm.get(i));
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            student student = new student();

            student.setStudentid(rs.getString("studentid"));
            student.setStudentname(rs.getString("studentname"));
            student.setGender(rs.getString("gender"));
            student.setMajor(rs.getString("major"));
            student.setDepartment(rs.getString("department"));
            student.setClasses(rs.getString("classes"));
            Dorm dorm = new Dorm();
            dorm.setId(rs.getInt("dormitoryid"));
            dorm.setBuild(rs.getInt("build"));
            dorm.setNumber(rs.getInt("number"));
            dorm.setStatus(rs.getString("status"));
            student.setDorm(dorm);
            student.setPhoneid(rs.getString("phoneid"));
            student.setEntrytime(rs.getDate("entrytime"));
            student.setUsername(rs.getString("username"));
            student.setPassword(rs.getString("password"));

            System.out.println("username :" + rs.getString("username"));

            students.add(student);
        }
        return students;
    }
}
