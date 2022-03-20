package SERVLET;

import DAO.StudentDao;
import JAVABEAN.Dorm;
import JAVABEAN.student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AdminSearchServlet")
public class AdminSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 对表单提交的数据进行处理
            String dormBuld = (String) request.getSession().getAttribute("dormbuildid");
            String studentid = request.getParameter("studentid");
            String studentname = request.getParameter("studentname");
            String major = request.getParameter("major");
            String department = request.getParameter("department");
            String classes = request.getParameter("classes");
            String dormitoryid = request.getParameter("dormitoryid");
            String phoneid = request.getParameter("phoneid");

            student student = new student();
            if(studentid!=null&&!"".equals(studentid)){
                student.setStudentid(studentid);
            }
            if(studentname!=null&&!"".equals(studentname)){
                student.setStudentname(studentname);
            }
            if(major!=null&&!"".equals(major)){
                student.setMajor(major);
            }
            if(classes!=null&&!"".equals(classes)){
                student.setClasses(classes);
            }
            if(dormitoryid!=null&&!"".equals(dormitoryid)){
                Dorm dorm = new Dorm();
                dorm.setId(Integer.parseInt(dormitoryid));
                student.setDorm(dorm);
            }
            if(department!=null&&!"".equals(department)){
                student.setDepartment(department);
            }
            if(phoneid!=null&&!"".equals(phoneid)){
                student.setPhoneid(phoneid);
            }
            StudentDao studentDao = new StudentDao();
            System.out.println(student);
            ArrayList<student> students = studentDao.getStudentsByCondition(student,dormBuld);

            request.getSession().setAttribute("students",students);
            request.getRequestDispatcher("admin_student_information.jsp").forward(request,response);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
