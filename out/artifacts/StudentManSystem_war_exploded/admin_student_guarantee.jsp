<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="tag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <script language="JavaScript" src="js/showOrder.js"></script>
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
        <%--        手机端导航栏--%>
        <div class="navbar-toggle collapsed" style="float: left">
            <ul>
                <li>
                    <a href="#">正在登陆的用户为：${sessionScope.dormadminname}(宿管)</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/LoginOutServlet" onclick="return logout()">退出</a>
                </li>
            </ul>
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
                <li class="active"><a href="admin_student_guarantee.jsp">维修信息</a></li>
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
            <h2 class="sub-header">维修信息</h2>

            <form class="form-horizontal clearfix" role="form"
                  action="${pageContext.request.contextPath}/AdminGuaranteeServlet" method="post" id="searchForm">
                <input type="hidden" name="method" value="search">
                <div class="clearfix sub-header">
                    <input type="hidden" name="method" value="list">
                    <div class="form-grou">
                        <label class="control-label">姓名</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.guaFilter['stuName']}" class="form-control"
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
                                            <c:if test="${sessionScope.guaFilter['dormId']==dorm.id}">selected="selected"</c:if>>${dorm.number}</option>
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
            </form>

            <div class="table-responsive">
                <table class="table table-striped" id="guaTab">
                    <thead>
                    <tr>
                        <th>宿舍号</th>
                        <th>报修人姓名</th>
                        <th>物品名称</th>
                        <th>报修原因</th>
                        <th>报修人电话号码</th>
                        <th>维修日期</th>
                        <th>维修状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.guarantees}" var="guarantees">
                        <tr>
                            <td>${guarantees.dorm.build}-${guarantees.dorm.number}</td>
                            <td>${guarantees.studentname}</td>
                            <td>${guarantees.goodsname}</td>
                            <td>${guarantees.reason}</td>
                            <td>${guarantees.phoneid}</td>
                            <td>${guarantees.guaranteetime}</td>
                            <td class="status${guarantees.goodsname}${guarantees.dorm.id}">
                                <c:if test="${guarantees.guaranteestaus=='0'}">未维修</c:if>
                                <c:if test="${guarantees.guaranteestaus=='1'}">维修完成</c:if>
                            </td>
                            <td>
                                <button type="button" id="collBtn"
                                        onclick="finshGua(this.parentNode.parentNode.rowIndex,'${guarantees.goodsname}','${guarantees.dorm.id}');"
                                        class="btn btn-primary btn-xs" <c:if test="${guarantees.guaranteestaus=='1'}">disabled</c:if>>完成
                                </button>
                                <button type="button" id="delBtn"
                                        onclick="delGua(this.parentNode.parentNode.rowIndex,'${guarantees.dorm.id}','${guarantees.studentname}','${guarantees.goodsname }');"
                                        class="btn btn-danger btn-xs">移除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    //维修完成按钮
    function finshGua(rowIndex,goodsName,dormId) {
        // alert(rowIndex+","+stuName+","+dormId);
        $.ajax({
            type:"GET",
            url:"AdminGuaranteeServlet?method=modify",
            data:"goodsName="+goodsName+"&dormId="+dormId,
            success:function (data) {
                data = JSON.parse(data);
                var result = data.result;
                if (result){
                    alert("修改成功");
                    $(".status"+goodsName+dormId).text("维修完成");
                }else{
                    alert("修改失败");
                }
            }
        });

    }
    //删除记录按钮
    function delGua(rowIndex,dormId,stuName,goodsName) {
        // alert(rowIndex+","+stuName+","+dormId+","+goodsName);
        $.ajax({
            type:"GET",
            url:"AdminGuaranteeServlet?method=del",
            data:"goodsName="+goodsName+"&dormId="+dormId+"&stuName="+stuName,
            success:function (data) {
                data = JSON.parse(data);
                var result = data.result;
                if (result){
                    $("#guaTab").get(0).deleteRow(rowIndex);
                    alert("删除成功");
                }else{
                    alert("删除失败");
                }
            }
        });
    }
</script>
</body>
</html>

