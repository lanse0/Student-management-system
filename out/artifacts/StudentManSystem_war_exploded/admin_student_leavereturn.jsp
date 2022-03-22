<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="tag.jsp"%>
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
    <script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
    <script language="JavaScript" src="js/showLR.js"></script>
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
                <li><a href="admin_student_information.jsp">宿舍信息</a></li>
                <li><a href="admin_student_expess.jsp">快件信息</a></li>
                <li><a href="admin_student_guarantee.jsp">维修信息</a></li>
                <li class="active"><a href="admin_student_leavereturn.jsp">学生离校与返校</a></li>
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
            <h2 class="sub-header">学生离校与返校</h2>

            <form class="form-horizontal clearfix" role="form"
                  action="${pageContext.request.contextPath}/AdminLeavereturnServlet" method="post" id="searchForm">
                <div class="clearfix sub-header">
                    <input type="hidden" name="method" value="list">
                    <div class="form-grou">
                        <label class="control-label">姓名</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.leaFilter['stuName']}" class="form-control"
                                   placeholder="输入学生姓名" name="stuNameSearch">
                        </div>
                    </div>
                    <div class="form-grou">
                        <label class="control-label">宿舍号</label>
                        <div class="col-lg-4">
                            <select name="dormIdSearch" class="form-control">
                                <option value="">请选择</option>
                                <c:forEach items="${dorms}" var="dorm">
                                    <option value="${dorm.id}"
                                            <c:if test="${sessionScope.leaFilter['dormId']==dorm.id}">selected="selected"</c:if>>${dorm.number}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-grou" style="float: right">
                        <div>
                            <input type="submit" class="btn btn-default" value="查询"/>
                        </div>
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped sub-header" id="leaTab">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>宿舍号</th>
                            <th>离校时间</th>
                            <th>返校时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.leavereturns}" var="leavereturns">
                            <tr>
                                <td>${leavereturns.studentname}</td>
                                <td>${leavereturns.dorm.build}-${leavereturns.dorm.number}</td>
                                <td>
                                    <fmt:formatDate value="${leavereturns.leavetime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                </td>
                                <td>
                                    <fmt:formatDate value="${leavereturns.returntime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                </td>
                                <td>
                                    <button type="button" id="collBtn"
                                            onclick="modifyLea(this.parentNode.parentNode.rowIndex,'${leavereturns.studentname}','<fmt:formatDate value="${leavereturns.leavetime}" pattern="yyyy-MM-dd HH:mm"/>');"
                                            class="btn btn-primary btn-xs">修 改
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="2">
                                共 ${sessionScope.leaPage.totalSize} 条记录
                                第 ${sessionScope.leaPage.currPage} 页/共 ${sessionScope.leaPage.totalPage} 页&nbsp;&nbsp;
                                跳转到第 <input type="text" value="1" size="1"> 页
                            </td>
                            <td colspan="1"></td>
                            <td colspan="2" align="right">
                                <input type="submit" value="首页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(0);"
                                       <c:if test="${sessionScope.leaPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="上一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(-1);"
                                       <c:if test="${sessionScope.leaPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="下一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(1);"
                                       <c:if test="${sessionScope.leaPage.currPage>=sessionScope.leaPage.totalPage}">disabled</c:if>>
                                <input type="submit" value="尾页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(2);"
                                       <c:if test="${sessionScope.leaPage.currPage>=sessionScope.leaPage.totalPage}">disabled</c:if>>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            <input type="hidden" name="currPage" id="currPage" value="${sessionScope.leaPage.currPage}">
            </form>
            <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/AdminLeavereturnServlet" method="post">
                <input type="hidden" name="method" value="opera">
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名</label>
                    <div class="col-lg-4">
                        <input id="stuName" type="text" class="form-control" placeholder="输入学生姓名" name="studentname">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">宿舍号</label>
                    <div class="col-lg-4">
                        <select id="dorm" name="dormitoryid" class="form-control">
                            <option value="">请选择</option>
                            <c:forEach items="${dorms}" var="dorm">
                                <option value="${dorm.id}">${dorm.number}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">离校时间</label>
                    <div class="col-lg-4">
                        <input id="leaveTime" type="datetime-local" class="form-control" placeholder="输入离校时间" name="leavetime">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">返校时间</label>
                    <div class="col-lg-4">
                        <input id="returnTime" type="datetime-local" class="form-control" placeholder="输入返校时间" name="returntime">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" onclick="return sub();" class="btn btn-default" name="submit" value="submit" id="subBtn">提交</button>
                        <button type="submit" onclick="return sub();" style="display: none" class="btn btn-default" name="submit" value="modify" id="modiBtn">修改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    //修改离校返校记录
    function modifyLea(rowIndex, stuName, leavetime) {
        $.ajax({
            type: "POST",
            url:"AdminLeavereturnServlet?method=modify",
            data: "stuName=" + stuName + "&leavetime=" + leavetime,
            success:function (data) {
                data = JSON.parse(data);
                var leaver = data.leaver;
                $("#stuName").val(leaver.studentname);
                $("#dorm").val(leaver.dorm.id);
                $("#subBtn").css({"display":"none"});
                $("#modiBtn").css({"display":"block"});
            }
        });
    }

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
                $("#currPage").val(${sessionScope.leaPage.totalPage });
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

    //入库取件表单验证
    var stuName = document.getElementById("stuName");
    var dorm = document.getElementById("dorm");
    var leaveTime = document.getElementById("leaveTime");
    var returnTime = document.getElementById("returnTime");

    function sub() {
        if (stuName.value == "") {
            alert("请输入姓名");
            stuName.focus();
            return false;
        }
        if (dorm.value == "") {
            alert("请选择宿舍");
            dorm.focus();
            return false;
        }
        if (leaveTime.value == "") {
            alert("请输入离校时间");
            leaveTime.focus();
            return false;
        }
        if (returnTime.value == "") {
            alert("请输入返校数量");
            returnTime.focus();
            return false;
        }
        return true;
    }
</script>
</body>
</html>

