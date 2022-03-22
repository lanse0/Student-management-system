package SERVLET;

import DAO.LeavereturnDao;
import JAVABEAN.Dorm;
import JAVABEAN.leavereturn;
import UTIL.PageUtils;
import UTIL.Util;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminLeavereturnServlet")
public class AdminLeavereturnServlet extends HttpServlet {

    LeavereturnDao leavereturnDao;

    @Override
    public void init() throws ServletException {
        leavereturnDao = new LeavereturnDao();
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "opera":
                opera(request, response);
                break;
            case "list":
                list(request, response);
                break;
            case "modify"://取件获取学生对象
                modify(request, response);
                break;
            default:
                break;
        }
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息

        String stuName = request.getParameter("stuNameSearch");
        String dormIdStr = request.getParameter("dormIdSearch");
        String currPageStr = request.getParameter("currPage");
        Map filter = new HashMap();
        if (stuName != null && !"".equals(stuName)) {
            filter.put("stuName", stuName);
        }
        if (dormIdStr != null && !"".equals(dormIdStr)) {
            filter.put("dormId", Integer.parseInt(dormIdStr));
        }

        PageUtils page = new PageUtils();
        page.setPageSize(5);    //设置每页记录数
        int totalSze = leavereturnDao.getCountBySearch(filter, dormBuild);
        page.setTotalSize(totalSze);//总记录数
        page.setTotalPage(totalSze);//总页数
        if (currPageStr != null && !"".equals(currPageStr)) {
            page.setCurrPage(Integer.parseInt(currPageStr));
        }
        System.out.println(page);
        ArrayList<leavereturn> leavereturnArrayList = leavereturnDao.getLeaverBySearch(filter, page, dormBuild);
        request.getSession().setAttribute("leaFilter", filter);
        request.getSession().setAttribute("leaPage", page);
        request.getSession().setAttribute("leavereturns", leavereturnArrayList);
        request.getRequestDispatcher("admin_student_leavereturn.jsp").forward(request, response);
    }

    protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuName = request.getParameter("stuName");
        String leaveTime = request.getParameter("leavetime");
        leavereturn leavereturn = leavereturnDao.getLeaverByModify(stuName,leaveTime);
        JSONObject jobj = new JSONObject();
        jobj.element("leaver",leavereturn);
        PrintWriter out = response.getWriter();
        out.write(jobj.toString());
        out.flush();
        out.close();
    }

    protected void opera(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        ArrayList<leavereturn> leavereturns = new ArrayList<>();
        try {
            if(button.equals("提交"))
            {
                leavereturnDao.submit(leavereturn);
            } else if(button.equals("修改")){
                leavereturnDao.modify(leavereturn);
            }
            leavereturns = leavereturnDao.getAllLeavereturn(dormBuild);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        request.getSession().setAttribute("leavereturns",leavereturns);
        request.getRequestDispatcher("admin_student_leavereturn.jsp").forward(request,response);
    }

}
