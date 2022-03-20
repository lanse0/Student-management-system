<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>学生宿舍管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/dashboard.css" rel="stylesheet">
    <script src="https://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
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
                <li class="active"><a href="admin_student_information.jsp">宿舍信息</a></li>
                <li><a href="admin_student_expess.jsp">快件信息</a></li>
                <li><a href="admin_student_guarantee.jsp">维修信息</a></li>
                <li><a href="admin_student_leavereturn.jsp">学生离校与返校</a></li>
                <li><a href="admin_student_laterecord.jsp">晚归记录</a></li>
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
            <h2 class="sub-header">宿舍信息</h2>

            <form class="form-horizontal sub-header clearfix" role="form" action="${pageContext.request.contextPath}/AdminSearchServlet" method="post">
                <input type="hidden" name="per" value="service">
                <div class="form-group form-grou">
                    <label class="control-label">学号</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入学号" name="studentid">
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">姓名</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入学生姓名" name="studentname">
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">专业</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入学生专业" name="major">
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">院系</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入学生院系" name="department">
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">班级</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入学生班级" name="classes">
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">宿舍</label>
                    <div class="col-lg-4">
                        <select name="dormitoryid" class="form-control">
                            <option value="">请选择</option>
                            <c:forEach items="${dorms}" var="dorm">
                                <option value="${dorm.id}">${dorm.number}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group form-grou">
                    <label class="control-label">电话</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-contro" placeholder="输入电话号码" name="phoneid">
                    </div>
                </div>
                <div class="form-group form-grou" style="float: right">
                    <div>
                        <button type="submit" class="btn btn-default">查询</button>
                    </div>
                </div>
            </form>

            <div class="table-responsive table-responsiv">
                <table class="table table-striped" >
                    <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>专业</th>
                        <th>宿舍号</th>
                        <th>院系</th>
                        <th>班级</th>
                        <th>手机号</th>
                        <th>入住时间</th>
                        <th>账号</th>
                        <th>密码</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.students}" var="students">
                            <tr>
                                <td>${students.studentid}</td>
                                <td>${students.studentname}</td>
                                <td>${students.gender}</td>
                                <td>${students.major}</td>
                                <td>${students.dorm.number}</td>
                                <td>${students.department}</td>
                                <td>${students.classes}</td>
                                <td>${students.phoneid}</td>
                                <td>${students.entrytime}</td>
                                <td>${students.username}</td>
                                <td>${students.password}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

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

