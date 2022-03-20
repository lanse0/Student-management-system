package SERVLET;

import DAO.LaterecordDao;
import JAVABEAN.Dorm;
import JAVABEAN.laterecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "AdminExpressServlet")
public class AdminLaterecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取按下的是哪个按钮
        String button = request.getParameter("submit");
        String dormBuild = (String)request.getSession().getAttribute("dormbuildid");
//        String studentid = request.getParameter("studentid");
        String studentname = request.getParameter("studentname");
        String dormitoryid = request.getParameter("dormitoryid");
        String letetimeStr = request.getParameter("latetime");
        letetimeStr = letetimeStr.replace("T"," ");
        Date latetime = new Date();
        try {
            latetime = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(letetimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(letetimeStr);
        System.out.println(latetime);
        String reason = request.getParameter("reason");

        laterecord laterecord = new laterecord();
        laterecord.setStudentname(studentname);
        Dorm dorm = new Dorm();
        dorm.setId(Integer.parseInt(dormitoryid));
        laterecord.setDorm(dorm);
        laterecord.setLatetime(latetime);
        laterecord.setReason(reason);

        LaterecordDao laterecordDao = new LaterecordDao();
        ArrayList<laterecord> laterecords = new ArrayList<>();
        try {
            if (button.equals("submit")) {
                laterecordDao.submit(laterecord);
            } else if (button.equals("modify")) {
                laterecordDao.modify(laterecord);
            }
            laterecords = laterecordDao.getAllLaterecords(dormBuild);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        request.getSession().setAttribute("laterecords", laterecords);
        request.getRequestDispatcher("admin_student_laterecord.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    }
}
