package SERVLET;

import DAO.FeeDao;
import JAVABEAN.Dorm;
import JAVABEAN.fee;
import UTIL.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminFeeServlet")
public class AdminFeeServlet extends HttpServlet {
    FeeDao feeDao;

    @Override
    public void init() throws ServletException {
        feeDao = new FeeDao();
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
            default:
                break;
        }
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息

        String year = request.getParameter("yearSear");
        String dormIdStr = request.getParameter("dormIdSearch");
        String monSear = request.getParameter("monSear");
        String currPageStr = request.getParameter("currPage");
        Map filter = new HashMap();
        if (year != null && !"".equals(year)) {
            filter.put("year", year);
        }
        if (dormIdStr != null && !"".equals(dormIdStr)) {
            filter.put("dormId", Integer.parseInt(dormIdStr));
        }
        if (monSear != null && !"".equals(monSear)) {
            filter.put("month", Integer.parseInt(monSear));
        }

        PageUtils page = new PageUtils();
        page.setPageSize(5);    //设置每页记录数
        int totalSze = feeDao.getCountBySearch(filter, dormBuild);
        page.setTotalSize(totalSze);//总记录数
        page.setTotalPage(totalSze);//总页数
        if (currPageStr != null && !"".equals(currPageStr)) {
            page.setCurrPage(Integer.parseInt(currPageStr));
        }
        ArrayList<fee> fees = feeDao.getFeeBySearch(filter, page, dormBuild);
        request.getSession().setAttribute("feeFilter", filter);
        request.getSession().setAttribute("feePage", page);
        request.getSession().setAttribute("fees", fees);
        response.sendRedirect("admin_student_fee.jsp");
    }

    protected void opera(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取按下的是哪个按钮
        String button = request.getParameter("submit");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String dormitoryid = request.getParameter("dormitoryid");
        String electricfee = request.getParameter("electricfee");
        String electricnum = request.getParameter("electricnum");
        String waterfee = request.getParameter("waterfee");
        String waternum = request.getParameter("waternum");

        fee fee = new fee();
        if (year!=null&&!"".equals(year)){
            fee.setYear(year);
        }
        fee.setMonth(month);
        Dorm dorm = new Dorm();
        dorm.setId(Integer.parseInt(dormitoryid));
        fee.setDorm(dorm);
        fee.setElectricfee(electricfee);
        fee.setElectricnum(electricnum);
        fee.setWaterfee(waterfee);
        fee.setWaternum(waternum);

        System.out.println(fee);
        System.out.println(button);
        try {
            if(button.equals("submit"))
            {
                feeDao.submit(fee);
            } else if(button.equals("modify")){
                feeDao.modify(fee);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        response.sendRedirect("AdminFeeServlet?method=list");
    }
}
