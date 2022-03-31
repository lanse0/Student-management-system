<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="tag.jsp" %>
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
    <script src="js/jquery-3.4.1.min.js"></script>
    <script src="js/json2.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
    <script type="text/javascript">
        function logout() {
            if (!confirm("真的要退出吗？")) {
                window["event"].returnValue = false;
            }
        }
    </script>
</head>
<body>
<%
    String dormadminname = (String) request.getSession().getAttribute("dormadminname");
    if (dormadminname == null) {
        response.sendRedirect("index.jsp");
    }
%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header" id="nav">
            <a class="navbar-brand text-primary" href="index.jsp">学生宿舍管理系统(${sessionScope.dormbuildid}栋)</a>
        </div>
<%--        手机端导航栏--%>
        <div class="navbar-toggle collapsed" style="float: left">
            <ul>
                <li>
                    <a href="student_information.jsp">正在登陆的用户为：${sessionScope.dormadminname}(宿管)</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/LoginOutServlet" onclick="return logout()">退出</a>
                </li>
            </ul>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav justify-content-end">
                <li class="nav-item active">
                    <a class="navbar-right"
                       href="student_information.jsp">正在登陆的用户为：${sessionScope.dormadminname}(宿管)</a>
                </li>
                <li class="nav-item active">
                    <a class="navbar-right" href="${pageContext.request.contextPath}/LoginOutServlet"
                       onclick="return logout()">退出</a>
                </li>
            </ul>
        </div>

    </div>
</nav>
<%--<nav class="navbar navbar-inverse navbar-fixed-top">--%>
<%--    <div class="container-fluid">--%>
<%--        <div class="navbar-header">--%>
<%--            <a class="navbar-brand text-primary" href="index.jsp">学生宿舍管理系统(${sessionScope.dormbuildid}栋)</a>--%>
<%--        </div>--%>
<%--        <div id="navbar" class="navbar-collapse collapse">--%>
<%--            <ul class="nav justify-content-end">--%>
<%--                <li class="nav-item active">--%>
<%--                    <a class="navbar-right"--%>
<%--                       href="student_information.jsp">正在登陆的用户为：${sessionScope.dormadminname}(宿管)</a>--%>
<%--                </li>--%>
<%--                <li class="nav-item active">--%>
<%--                    <a class="navbar-right" href="${pageContext.request.contextPath}/LoginOutServlet"--%>
<%--                       onclick="return logout()">退出</a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>
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

            <form class="form-horizontal clearfix" role="form"
                  action="${pageContext.request.contextPath}/AdminSearchServlet" method="post">
                <input type="hidden" name="method" value="list">
                <div class="clearfix sub-header">
                    <div class="form-group form-grou">
                        <label class="control-label">学号</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.studentid}"
                                   class="form-control form-contro" placeholder="输入学号" name="studentid">
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">姓名</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.studentname}"
                                   class="form-control form-contro" placeholder="输入学生姓名" name="studentname">
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">专业</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.major}" class="form-control form-contro"
                                   placeholder="输入学生专业" name="major">
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">院系</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.department}"
                                   class="form-control form-contro" placeholder="输入学生院系" name="department">
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">班级</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.classes}"
                                   class="form-control form-contro" placeholder="输入学生班级" name="classes">
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">宿舍</label>
                        <div class="col-lg-4">
                            <select name="dormitoryid" class="form-control">
                                <option value="">请选择</option>
                                <c:forEach items="${dorms}" var="dorm">
                                    <option value="${dorm.id}"
                                            <c:if test="${sessionScope.stuSearch.dorm.id==dorm.id}">selected="selected"</c:if> >${dorm.number}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-grou">
                        <label class="control-label">电话</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.stuSearch.phoneid}"
                                   class="form-control form-contro" placeholder="输入电话号码" name="phoneid">
                        </div>
                    </div>
                    <div class="form-group form-grou" style="float: right">
                        <div>
                            <button type="submit" class="btn btn-default">查询</button>
                        </div>
                    </div>
                </div>
                <div class="table-responsive table-responsiv" style="overflow-x: unset">
                    <table class="table table-striped sub-header">
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
                        <tfoot>
                        <tr>
                            <td colspan="4">
                                共 ${sessionScope.stuPage.totalSize} 条记录
                                第 ${sessionScope.stuPage.currPage} 页/共 ${sessionScope.stuPage.totalPage} 页&nbsp;&nbsp;
                                跳转到第 <input type="text" value="1" size="1"> 页
                            </td>
                            <td colspan="3"></td>
                            <td colspan="4" align="right">
                                <input type="submit" value="首页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(0);"
                                       <c:if test="${sessionScope.stuPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="上一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(-1);"
                                       <c:if test="${sessionScope.stuPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="下一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(1);"
                                       <c:if test="${sessionScope.stuPage.currPage>=sessionScope.stuPage.totalPage}">disabled</c:if>>
                                <input type="submit" value="尾页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(2);"
                                       <c:if test="${sessionScope.stuPage.currPage>=sessionScope.stuPage.totalPage}">disabled</c:if>>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <input type="hidden" id="currPage" name="currPage" value="${sessionScope.stuPage.currPage}">
            </form>
        </div>
    </div>
</div>
<script>
    //翻页
    function subSearchForm(n) {
        var curr = $("#currPage").val();
        // alert(curr);
        if (n == 0) {
            $("#currPage").val(1);
            // alert($("#currPage").val());
            return true;
        } else {
            if (n == 2) {
                $("#currPage").val(${sessionScope.stuPage.totalPage });
                // alert($("#currPage").val());
                return true;
            } else {
                curr = parseInt(curr) + n;
                $("#currPage").val(curr);
                // alert($("#currPage").val());
                return true;
            }
        }
        alert("err");
        return false;
    }
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>

