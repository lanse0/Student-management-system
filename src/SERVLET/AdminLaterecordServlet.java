package SERVLET;

import DAO.LaterecordDao;
import JAVABEAN.Dorm;
import JAVABEAN.laterecord;
import UTIL.PageUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminLaterecordServlet")
public class AdminLaterecordServlet extends HttpServlet {

    LaterecordDao laterecordDao;
    @Override
    public void init() throws ServletException {
        laterecordDao = new LaterecordDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "sub":
                sub(request, response);
                break;
            case "list":
                list(request, response);
                break;
            case "del":
                del(request, response);
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
        int totalSze = laterecordDao.getCountBySearch(filter, dormBuild);
        page.setTotalSize(totalSze);//总记录数
        page.setTotalPage(totalSze);//总页数
        if (currPageStr != null && !"".equals(currPageStr)) {
            page.setCurrPage(Integer.parseInt(currPageStr));
        }
        System.out.println(page);
        ArrayList<laterecord> laterecordArrayList = laterecordDao.getLaterBySearch(filter, page, dormBuild);
        request.getSession().setAttribute("latFilter", filter);
        request.getSession().setAttribute("latPage", page);
        request.getSession().setAttribute("laterecords", laterecordArrayList);
        request.getRequestDispatcher("admin_student_laterecord.jsp").forward(request, response);
    }

    protected void sub(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String)request.getSession().getAttribute("dormbuildid");
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
        String reason = request.getParameter("reason");

        laterecord laterecord = new laterecord();
        laterecord.setStudentname(studentname);
        Dorm dorm = new Dorm();
        dorm.setId(Integer.parseInt(dormitoryid));
        laterecord.setDorm(dorm);
        laterecord.setLatetime(latetime);
        laterecord.setReason(reason);

        LaterecordDao laterecordDao = new LaterecordDao();
        try {
            laterecordDao.submit(laterecord);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("AdminLaterecordServlet?method=list");
    }
    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuName = request.getParameter("stuName");
        String  latTime = request.getParameter("latTime");
        boolean f = laterecordDao.del(stuName,latTime);
        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        jobj.element("result",f);
        out.write(jobj.toString());
        out.flush();
        out.close();
    }
}
