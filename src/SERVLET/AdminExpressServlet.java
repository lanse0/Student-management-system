package SERVLET;

import DAO.ExpressDao;
import JAVABEAN.Dorm;
import JAVABEAN.express;
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

@WebServlet(name = "AdminExpressServlet")
public class AdminExpressServlet extends HttpServlet {
    ExpressDao expressDao;

    @Override
    public void init() throws ServletException {
        expressDao = new ExpressDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "exp":
                exp(request, response);
                break;
            case "list":
                list(request, response);
                break;
            case "del":
                del(request, response);
                break;
            case "modify":
                modify(request, response);
                break;
            default:
                break;
        }
    }

    protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuName = request.getParameter("stuName");
        String  arriveTime = request.getParameter("arriveTime");

        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();

        out.write(jobj.toString());
        out.flush();
        out.close();
    }
    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuName = request.getParameter("stuName");
        String  arriveTime = request.getParameter("arriveTime");
        boolean f = expressDao.delExpress(stuName,arriveTime);
        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        if (f){
            jobj.element("result",true);
        }else {
            jobj.element("result",false);
        }
        out.write(jobj.toString());
        out.flush();
        out.close();
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息

        String stuName = request.getParameter("stuNameSearch");
        String dormIdStr = request.getParameter("dormIdSearch");
        String arriveTimeStr = request.getParameter("arriveTimeSearch");
        String currPageStr = request.getParameter("currPage");
        Map filter = new HashMap();
        if (stuName != null && !"".equals(stuName)) {
            filter.put("stuName", stuName);
        }
        if (dormIdStr != null && !"".equals(dormIdStr)) {
            filter.put("dormId", Integer.parseInt(dormIdStr));
        }
        if (arriveTimeStr != null && !"".equals(arriveTimeStr)) {
//            Date arriveTime = Util.strToDate(arriveTimeStr,"yyyy-mm-dd");
            filter.put("arriveTime", arriveTimeStr);
        }

        PageUtils page = new PageUtils();
        page.setPageSize(5);    //设置每页记录数
        int totalSze = expressDao.getCountBySearch(filter, dormBuild);
        page.setTotalSize(totalSze);//总记录数
        page.setTotalPage(totalSze);//总页数
        if (currPageStr != null && !"".equals(currPageStr)) {
            page.setCurrPage(Integer.parseInt(currPageStr));
        }
        System.out.println(page);
        ArrayList<express> expressArrayList = expressDao.getExpressBySearch(filter, page, dormBuild);
        request.getSession().setAttribute("exprFilter", filter);
        request.getSession().setAttribute("exprPage", page);
        request.getSession().setAttribute("expresses", expressArrayList);
        request.getRequestDispatcher("admin_student_expess.jsp").forward(request, response);
    }

    protected void exp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取按下的是哪个按钮
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");
        String button = request.getParameter("submit");
        String studentname = request.getParameter("studentname");
        String dormitoryid = request.getParameter("dormitoryid");
        String arrive = request.getParameter("arrivetime");
        String collect = request.getParameter("collecttime");
        arrive = arrive.replace("T", " ");
        collect = collect.replace("T", " ");
        Date arrivetime = null;
        Date collecttime = null;
        try {
            if (arrive != null && !"".equals(arrive)) {
                arrivetime = Util.strToDate(arrive, "yyyy-MM-dd hh:mm");
            }
            if (collect != null && !"".equals(collect)) {
                collecttime = Util.strToDate(collect, "yyyy-MM-dd hh:mm");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String collectname = request.getParameter("collectname");
        String collectnum = request.getParameter("collectnum");
        String collectphoneid = request.getParameter("collectphoneid");

        express express = new express();
        if (studentname != null && !"".equals(studentname)) {
            express.setStudentname(studentname);
        }
        if (dormitoryid != null && !"".equals(dormitoryid)) {
            Dorm dorm = new Dorm();
            dorm.setId(Integer.parseInt(dormitoryid));
            express.setDorm(dorm);
        }
        if (arrivetime != null) {
            express.setArrivetime(arrivetime);
        }
        if (collecttime != null) {
            express.setCollecttime(collecttime);
        }
        if (collectnum != null && !"".equals(collectnum)) {
            express.setCollectnum(Integer.parseInt(collectnum));
        }
        if (collectname != null && !"".equals(collectname)) {
            express.setCollectname(collectname);
        }
        if (collectphoneid != null && !"".equals(collectphoneid)) {
            express.setCollectphoneid(collectphoneid);
        }
        try {
            if (button.equals("submit")) {
                expressDao.submit(express);
            } else if (button.equals("modify")) {
                expressDao.modify(express);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        request.getRequestDispatcher("AdminExpressServlet?method=list").forward(request, response);
    }
}
//快件模糊查询 初始化
