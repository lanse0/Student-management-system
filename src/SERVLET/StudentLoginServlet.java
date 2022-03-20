package SERVLET;

import DAO.*;
import JAVABEAN.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentLoginServlet")
public class StudentLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取用户名 密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String checkCode = (String) request.getSession().getAttribute("validateCode");
        //判断用户名、密码是否为空
        if (username == "" || username.length() == 0) {
            request.setAttribute("namemsg", "用户名为空!");
            request.getRequestDispatcher("student_login.jsp").forward(request, response);
        }
        if (password == "" || password.length() == 0) {
            request.setAttribute("pwdmsg", "密码为空!");
            request.getRequestDispatcher("student_login.jsp").forward(request, response);
        }
        if ("".equals(code) || code.length() == 0) {
            request.setAttribute("codeMsg", "验证码为空!");
            request.getRequestDispatcher("student_login.jsp").forward(request, response);
        }
        if (checkCode.equals(code)) {
            StudentDao studentDao = new StudentDao();
            ExpressDao expressDao = new ExpressDao();
            GuaranteeDao guaranteeDao = new GuaranteeDao();
            LeavereturnDao leavereturnDao = new LeavereturnDao();
            LaterecordDao laterecordDao = new LaterecordDao();
            FeeDao feeDao = new FeeDao();
            DormDao dormDao = new DormDao();
            try {
                ResultSet rs = studentDao.selectStudent(username);
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        //登陆成功
                        //将用户stu存入session域
                        student stu = new student();
                        stu.setUsername(username);
                        stu.setPassword(password);
                        stu.setStudentid(rs.getString("studentid"));
                        stu.setStudentname(rs.getString("studentname"));
                        stu.setMajor(rs.getString("major"));
                        stu.setGender(rs.getString("gender"));
                        stu.setDepartment(rs.getString("department"));
                        stu.setClasses(rs.getString("classes"));
                            Dorm d = new Dorm();
                            d.setId(rs.getInt("dormitoryid"));
                            d.setBuild(rs.getInt("build"));
                            d.setNumber(rs.getInt("number"));
                            d.setStatus(rs.getString("status"));
                        stu.setDorm(d);
                        stu.setPhoneid(rs.getString("phoneid"));
                        stu.setEntrytime(rs.getDate("entrytime"));
                        request.getSession().setAttribute("stu", stu);
                        // 传递students 相关信息
                        // 得到宿舍对象
                        Dorm dorm = studentDao.getDormirotyByUsername(username);
                        // 得到同寝室人员
                        ArrayList<student> student = studentDao.getStudentsByDormitoryid(dorm);
                        String studentname = studentDao.getStudentnameByUsername(username);
                        // 得到快件信息
                        ArrayList<express> express = expressDao.getExpressesByStudentname(studentname);
                        // 得到寝室维修信息
                        ArrayList<guarantee> guarantee = guaranteeDao.getGuaranteesByDormitoryid(dorm);
                        // 得到个人的离校返校信息
                        ArrayList<leavereturn> leavereturn = leavereturnDao.getLeavereturnsByStudentname(studentname);
                        // 得到个人的晚归记录
                        ArrayList<laterecord> laterecord = laterecordDao.getLaterecordsByStudentname(studentname);
                        // 得到寝室的水电费信息
                        ArrayList<fee> fee = feeDao.getFeesByStudentname(dorm);
                        List<Dorm> dorms = dormDao.getAllDorm(dorm.getBuild());

                        request.getSession().setAttribute("dorm", dorm);
                        request.getSession().setAttribute("dorms", dorms);
                        request.getSession().setAttribute("student", student);
                        request.getSession().setAttribute("studentname", studentname);
                        request.getSession().setAttribute("studentid", studentDao.getStudentidByStudentname(studentname));
                        request.getSession().setAttribute("express", express);
                        request.getSession().setAttribute("guarantee", guarantee);
                        request.getSession().setAttribute("leavereturn", leavereturn);
                        request.getSession().setAttribute("laterecord", laterecord);
                        request.getSession().setAttribute("fee", fee);

                        Cookie cookie = new Cookie("SESSION", stu.getUsername());
                        //设置作用域，这里设置为根目录以下所有
                        cookie.setPath("/");
                        //将设置好的cookie保存到客户端的浏览器
                        response.addCookie(cookie);
                        // 跳转至首页
                        request.getRequestDispatcher("student_information.jsp").forward(request, response);

                    } else {
                        request.setAttribute("pwdError", "密码不正确!");
                        request.getRequestDispatcher("student_login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("nameError", "用户名不存在!");
                    request.getRequestDispatcher("student_login.jsp").forward(request, response);
                }

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            request.setAttribute("codeError", "验证码错误!");
            request.getRequestDispatcher("student_login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
