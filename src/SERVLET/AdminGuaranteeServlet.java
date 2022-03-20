package SERVLET;

import DAO.GuaranteeDao;
import JAVABEAN.Dorm;
import JAVABEAN.guarantee;

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

@WebServlet(name = "AdminGuaranteeServlet")
public class AdminGuaranteeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String)request.getSession().getAttribute("dormbuildid");
        String dormitoryid = request.getParameter("dormitoryid");
        String studentname = request.getParameter("studentname");
        String goodsname = request.getParameter("goodsname");
        String reason = request.getParameter("reason");
        String phoneid = request.getParameter("phoneid");
        String guaranteet = request.getParameter("guaranteetime");
        Date guaranteetime = null;
        if(guaranteet!=null&&!"".equals(guaranteet)){
            try {
                guaranteetime =new SimpleDateFormat("yyyy-MM-dd").parse(guaranteet);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        guarantee guarantee = new guarantee();
        if(dormitoryid!=null&&!"".equals(dormitoryid)){
            Dorm dorm = new Dorm();
            dorm.setId(Integer.parseInt(dormitoryid));
            guarantee.setDorm(dorm);
        }
        if(goodsname!=null&&!"".equals(goodsname)){
            guarantee.setGoodsname(goodsname);
        }
        if(guaranteetime!=null){
            guarantee.setGuaranteetime(guaranteetime);
        }

        GuaranteeDao guaranteeDao = new GuaranteeDao();
        ArrayList<guarantee> guarantees = new ArrayList<>();
        try {
            guaranteeDao.modify(guarantee);
            guarantees = guaranteeDao.getAllguarantee(dormBuild);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        request.getSession().setAttribute("guarantees",guarantees);
        request.getRequestDispatcher("admin_student_guarantee.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
