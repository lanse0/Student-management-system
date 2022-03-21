<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="tag.jsp"  %>
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
    <script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>

    <script language="JavaScript" src="js/showRec.js"></script>
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
    String dormadminname = (String) request.getSession().getAttribute("dormadminname");
    if(dormadminname == null)
    {
        response.sendRedirect("index.jsp");
    }
%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand text-primary" href="index.jsp">学生宿舍管理系统(${sessionScope.dormbuildid}栋)</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav justify-content-end">
                <li class="nav-item active">
                    <a class="navbar-right" href="student_information.jsp">正在登陆的用户为：${sessionScope.dormadminname}(宿管)</a>
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
                <li><a href="admin_student_information.jsp">宿舍信息 </a></li>
                <li><a href="admin_student_expess.jsp">快件信息</a></li>
                <li><a href="admin_student_guarantee.jsp">维修信息</a></li>
                <li><a href="admin_student_leavereturn.jsp">学生离校与返校</a></li>
                <li class="active"><a href="admin_student_laterecord.jsp">晚归记录</a></li>
                <li><a href="admin_student_fee.jsp">水电费信息</a></li>
            </ul>
        </div>
        <div class="visible-xs">
            <li><a href="admin_student_information.jsp">宿舍信息</a></li>
            <li><a href="admin_student_expess.jsp">快件信息</a></li>
            <li><a href="admin_student_guarantee.jsp">维修信息</a></li>
            <li><a href="admin_student_leavereturn.jsp">学生离校与返校</a></li>
            <li><a href="admin_student_laterecord.jsp">晚归记录</a></li>
            <li><a href="admin_student_fee.jsp">水电费信息</a></li>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="sub-header">晚归记录</h2>
            <div class="table-responsive">
                <table class="table table-striped" >
                    <thead>
                    <tr>
                        <th>学生姓名</th>
                        <th>宿舍号</th>
                        <th>晚归时间</th>
                        <th>晚归原因</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.laterecords}" var="laterecords">
                        <tr>
                            <td>${laterecords.studentname}</td>
                            <td>${laterecords.dorm.build}-${laterecords.dorm.number}</td>
                            <td>
                                <fmt:formatDate value="${laterecords.latetime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                            </td>
                            <td>${laterecords.reason}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/AdminLaterecordServlet" method="post">
                <input type="hidden" name="per" value="rec">
                <div class="form-group">
                    <label class="col-sm-2 control-label">学生姓名</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" placeholder="输入学生姓名" name="studentname">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">宿舍号</label>
                    <div class="col-lg-4">
                        <select name="dormitoryid" class="form-control">
                            <option value="">请选择</option>
                            <c:forEach items="${dorms}" var="dorm">
                                <option value="${dorm.id}">${dorm.number}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">晚归时间</label>
                    <div class="col-lg-4">
                        <input type="datetime-local" class="form-control" placeholder="输入晚归时间" name="latetime">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">晚归原因</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" placeholder="晚归原因" name="reason">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" name="submit" value="submit">提交</button>
                        <button type="submit" class="btn btn-default" name="submit" value="modify">修改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

