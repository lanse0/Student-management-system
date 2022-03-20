package SERVLET;

import DAO.LeavereturnDao;
import JAVABEAN.Dorm;
import JAVABEAN.leavereturn;
import UTIL.Util;

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

@WebServlet(name = "AdminLeavereturnServlet")
public class AdminLeavereturnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取按下的是哪个按钮
        String dormBuild = (String)request.getSession().getAttribute("dormbuildid");
        String button = request.getParameter("submit");
        String studentname = request.getParameter("studentname");
        String dormitoryid = request.getParameter("dormitoryid");
        String levetimeStr = request.getParameter("leavetime");
        levetimeStr = levetimeStr.replace("T"," "); //input type="datetime-local" 次属性生成的字符串：2022-03-19T00:27
        String returntimeStr = request.getParameter("returntime");
        returntimeStr = returntimeStr.replace("T"," "); //把T替换成空格
        Date leavetime = null;
        Date returntime = null;
        try {
            leavetime = Util.strToDate(levetimeStr,"yyyy-MM-dd hh:mm");
            returntime = Util.strToDate(returntimeStr,"yyyy-MM-dd hh:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        leavereturn leavereturn = new leavereturn();
        leavereturn.setStudentname(studentname);
        Dorm dorm = new Dorm();
        dorm.setId(Integer.parseInt(dormitoryid));
        leavereturn.setDorm(dorm);
        leavereturn.setLeavetime(leavetime);
        leavereturn.setReturntime(returntime);

        LeavereturnDao leavereturnDao = new LeavereturnDao();
        ArrayList<leavereturn> leavereturns = new ArrayList<>();
        try {
            if(button.equals("submit"))
            {
                leavereturnDao.submit(leavereturn);
            } else if(button.equals("modify")){
                leavereturnDao.modify(leavereturn);
            }
            leavereturns = leavereturnDao.getAllLeavereturn(dormBuild);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        request.getSession().setAttribute("leavereturns",leavereturns);
        request.getRequestDispatcher("admin_student_leavereturn.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
