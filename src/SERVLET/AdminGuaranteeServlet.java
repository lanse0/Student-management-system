package SERVLET;

import DAO.GuaranteeDao;
import JAVABEAN.guarantee;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminGuaranteeServlet")
public class AdminGuaranteeServlet extends HttpServlet {

    GuaranteeDao guaranteeDao;

    @Override
    public void init() throws ServletException {
        guaranteeDao = new GuaranteeDao();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "modify"://修改报修状态
                try {
                    modify(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "search"://查找保修信息
                search(request, response);
                break;
            case "del":
                try {
                    del(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息

        String stuName = request.getParameter("stuNameSearch");
        String dormIdStr = request.getParameter("dormIdSearch");
        Map filter = new HashMap();
        if (stuName != null && !"".equals(stuName)) {
            filter.put("stuName", stuName);
        }
        if (dormIdStr != null && !"".equals(dormIdStr)) {
            filter.put("dormId", Integer.parseInt(dormIdStr));
        }
        ArrayList<guarantee> guaranteeArrayList = guaranteeDao.getGuaranteesBySearch(filter, dormBuild);
        request.getSession().setAttribute("guaFilter", filter);
        request.getSession().setAttribute("guarantees", guaranteeArrayList);
        request.getRequestDispatcher("admin_student_guarantee.jsp").forward(request, response);
    }
    protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息
        int dormId = Integer.parseInt(request.getParameter("dormId"));
        String goodsName = request.getParameter("goodsName");

        boolean f = guaranteeDao.modify(goodsName,dormId);
        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        jobj.element("result",f);
        out.write(jobj.toString());
        out.flush();
        out.close();

        //更新session 域中的报修信息
        //此servlet没有载入方法 故修改数据后需更新session域中的集合
        ArrayList<guarantee> guaranteeArrayList = guaranteeDao.getAllguarantee(dormBuild);
        request.getSession().setAttribute("guarantees", guaranteeArrayList);
    }

    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        int dormId = Integer.parseInt(request.getParameter("dormId"));
        String goodsName = request.getParameter("goodsName");
        String stuName = request.getParameter("stuName");
        boolean f = guaranteeDao.delete(dormId,stuName,goodsName);
        PrintWriter out = response.getWriter();
        JSONObject jobj = new JSONObject();
        jobj.element("result",f);
        out.write(jobj.toString());
        out.flush();
        out.close();

        //更新session 域中的报修信息
        //此servlet没有载入方法 故修改数据后需更新session域中的集合
        String dormBuild = (String) request.getSession().getAttribute("dormbuildid");//楼栋信息
        ArrayList<guarantee> guaranteeArrayList = guaranteeDao.getAllguarantee(dormBuild);
        request.getSession().setAttribute("guarantees", guaranteeArrayList);
    }
}
