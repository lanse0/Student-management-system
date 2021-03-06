<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/FINN.ico">
    <title>学生宿舍管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/dashboard.css" rel="stylesheet">
    <script type="text/javascript">
        function logout(){
            if(!confirm("真的要退出吗？")){
                window["event"].returnValue = false;
            }
        }
    </script>
</head>

<body>
<%
    String studentname = (String) request.getSession().getAttribute("studentname");
    if(studentname == null)
    {
        response.sendRedirect("index.jsp");
    }
%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand text-primary" href="index.jsp">学生宿舍管理系统${sessionScope.dorm.build}-${sessionScope.dorm.number}</a>
        </div>
        <%--        手机端导航栏--%>
        <div class="navbar-toggle collapsed" style="float: left">
            <ul>
                <li>
                    <a href="#">正在登陆的用户为：${sessionScope.studentname}(学生)</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/LoginOutServlet" onclick="return logout()">退出</a>
                </li>
            </ul>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav justify-content-end">
                <li class="nav-item active">
                    <a class="navbar-right" href="student_information.jsp">正在登陆的用户为：${sessionScope.studentname}(学生)</a>
                </li>
                <li class="nav-item active">
                    <a class="navbar-right" href="${pageContext.request.contextPath}/LoginOutServlet" onclick="return logout()">退出</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2 sidebar hidden-xs">
                <ul class="nav nav-sidebar">
                    <li><a href="student_information.jsp">宿舍信息</a></li>
                    <li><a href="student_express.jsp">快件信息</a></li>
                    <li class="active"><a href="student_guarantee.jsp">维修信息</a></li>
                    <li><a href="student_leavereturn.jsp">学生离校与返校</a></li>
                    <li><a href="student_laterecord.jsp">晚归记录</a></li>
                    <li><a href="student_fee.jsp">水电费查询</a></li>
                </ul>
            </div>
            <div class="visible-xs">
                <li><a href="student_information.jsp">宿舍信息</a></li>
                <li><a href="student_express.jsp">快件信息</a></li>
                <li><a href="student_guarantee.jsp">维修信息</a></li>
                <li><a href="student_leavereturn.jsp">学生离校与返校</a></li>
                <li><a href="student_laterecord.jsp">晚归记录</a></li>
                <li><a href="student_fee.jsp">水电费查询</a></li>
            </div>

            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">维修信息</h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>宿舍号</th>
                            <th>报修人姓名</th>
                            <th>物品名称</th>
                            <th>报修原因</th>
                            <th>报修人电话号码</th>
                            <th>维修日期</th>
                            <th>维修状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.guarantee}" var="guarantees">
                            <tr>
                                <td>${guarantees.dorm.build}-${guarantees.dorm.number}</td>
                                <td>${guarantees.studentname}</td>
                                <td>${guarantees.goodsname}</td>
                                <td>${guarantees.reason}</td>
                                <td>${guarantees.phoneid}</td>
                                <td>${guarantees.guaranteetime}</td>
                                <td>
                                    <c:if test="${guarantees.guaranteestaus=='1'}">已维修</c:if>
                                    <c:if test="${guarantees.guaranteestaus=='0'}">未维修</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/StudentGuaranteeServlet" method="post">
                    <input type="hidden" name="per" value="service">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">宿舍号</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" placeholder="输入宿舍号" value="${sessionScope.dorm.number}" disabled>
                            <input type="hidden" name="dormitoryid" value="${sessionScope.dorm.id}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">物品名称</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" placeholder="输入报修物品" name="goodsname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">报修原因</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" placeholder="输入报修原因" name="reason">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">报修人姓名</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" placeholder="输入报修人姓名" name="studentname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">报修人电话号码</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" placeholder="输入报修电话号码" name="phoneid">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">报修</button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

</body>
</html>

