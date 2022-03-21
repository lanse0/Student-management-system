<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <div class="navbar-header">
            <a class="navbar-brand text-primary" href="index.jsp">学生宿舍管理系统(${sessionScope.dormbuildid}栋)</a>
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
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar hidden-xs">
            <ul class="nav nav-sidebar">
                <li><a href="admin_student_information.jsp">宿舍信息 </a></li>
                <li class="active"><a href="admin_student_expess.jsp">快件信息</a></li>
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
            <h2 class="sub-header">快件信息</h2>

            <form class="form-horizontal clearfix" role="form"
                  action="${pageContext.request.contextPath}/AdminExpressServlet" method="post" id="searchForm">
                <div class="clearfix sub-header">
                    <input type="hidden" name="method" value="list">
                    <div class="form-grou">
                        <label class="control-label">姓名</label>
                        <div class="col-lg-4">
                            <input type="text" value="${sessionScope.exprFilter['stuName']}" class="form-control"
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
                                            <c:if test="${sessionScope.exprFilter['dormId']==dorm.id}">selected="selected"</c:if>>${dorm.number}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-grou">
                        <label class="control-label">到达时间</label>
                        <div class="col-lg-4">
                            <input type="date" value="${sessionScope.exprFilter['arriveTime']}" class="form-control"
                                   placeholder="输入到达时间" name="arriveTimeSearch">
                        </div>
                    </div>
                    <div class="form-group form-grou" style="float: right">
                        <div>
                            <input type="submit" class="btn btn-default" value="查询"/>
                        </div>
                    </div>
                </div>
                <div class="table-responsive" style="overflow-x: unset">
                    <table class="table table-striped sub-header" id="exprTable">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>宿舍号</th>
                            <th>到达时间</th>
                            <th>收件时间</th>
                            <th>快件数量</th>
                            <th>取件人姓名</th>
                            <th>取件人电话</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.expresses}" var="express">
                            <tr>
                                <td>${express.studentname}</td>
                                <td>${express.dorm.build}-${express.dorm.number}</td>
                                <td>
                                    <fmt:formatDate value="${express.arrivetime}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${express.collecttime}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td>${express.collectnum}</td>
                                <td>${express.collectname}</td>
                                <td>${express.collectphoneid}</td>
                                <td>
                                    <button type="button" id="collBtn"
                                            onclick="collExpr(this.parentNode.parentNode.rowIndex,'${express.studentname}','<fmt:formatDate value="${express.arrivetime}" pattern="yyyy-MM-dd HH:mm"/>');"
                                            class="btn btn-primary btn-xs" <c:if test="${not empty express.collectname }">disabled</c:if>>取件
                                    </button>
                                    <button type="button" id="delBtn"
                                            onclick="delExpr(this.parentNode.parentNode.rowIndex,'${express.studentname}','<fmt:formatDate value="${express.arrivetime}" pattern="yyyy-MM-dd HH:mm"/>');"
                                            class="btn btn-danger btn-xs">移除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="3">
                                共 ${sessionScope.exprPage.totalSize} 条记录
                                第 ${sessionScope.exprPage.currPage} 页/共 ${sessionScope.exprPage.totalPage} 页&nbsp;&nbsp;
                                跳转到第 <input type="text" value="1" size="1"> 页
                            </td>
                            <td colspan="2"></td>
                            <td colspan="3" align="right">
                                <input type="submit" value="首页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(0);"
                                       <c:if test="${sessionScope.exprPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="上一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(-1);"
                                       <c:if test="${sessionScope.exprPage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="下一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(1);"
                                       <c:if test="${sessionScope.exprPage.currPage>=sessionScope.exprPage.totalPage}">disabled</c:if>>
                                <input type="submit" value="尾页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(2);"
                                       <c:if test="${sessionScope.exprPage.currPage>=sessionScope.exprPage.totalPage}">disabled</c:if>>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <input type="hidden" name="currPage" id="currPage" value="${sessionScope.exprPage.currPage}">
            </form>

            <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/AdminExpressServlet"
                  method="post">
                <input type="hidden" name="method" value="exp">
                <div id="arrBox">
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名</label>
                    <div class="col-lg-4">
                        <input type="text" id="stuName" class="form-control" placeholder="输入学生姓名" name="studentname">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">宿舍号</label>
                    <div class="col-lg-4">
                        <select name="dormitoryid" id="dorm" class="form-control">
                            <option value="">请选择</option>
                            <c:forEach items="${dorms}" var="dorm">
                                <option value="${dorm.id}">${dorm.number}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">到达时间</label>
                    <div class="col-lg-4">
                        <input type="datetime-local" id="arrive" class="form-control" placeholder="输入到达时间"
                               name="arrivetime">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">快件数量</label>
                    <div class="col-lg-4">
                        <input type="text" id="collectNum" class="form-control" placeholder="输入快件数量" name="collectnum">
                    </div>
                </div>

                </div>

                <div id="collBox" style="display: none">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">收件时间</label>
                        <div class="col-lg-4">
                            <input type="datetime-local" id="collect" class="form-control" placeholder="输入收件时间"
                                   name="collecttime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">取件人姓名</label>
                        <div class="col-lg-4">
                            <input type="text" id="collectName" class="form-control" placeholder="输入取件人姓名"
                                   name="collectname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">取件人电话</label>
                        <div class="col-lg-4">
                            <input type="text" id="collectPhone" class="form-control" placeholder="输入取件人电话"
                                   name="collectphoneid">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2  col-sm-10">
                        <button type="submit" id="arrBtn" class="btn btn-default" name="submit" value="submit"
                                onclick="return sub()">入库
                        </button>
                        <button type="submit" id="collButt" style="display: none" class="btn btn-default" name="submit" value="modify"
                                onclick="return modify()">取件
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    //取件
    function collExpr(rowIndex, stuName, arriveTime) {
        $.ajax({
            type: "POST",
            url:"AdminExpressServlet?method=modify",
            data: "stuName=" + stuName + "&arriveTime=" + arriveTime,
            success:function (data) {
                data = JSON.parse(data);
                var expr = data.express;
                $("#stuName").val(expr.studentname);
                $("#dorm").val(expr.dorm.id);
                $("#arrBox").css({"display":"none"});
                $("#collBox").css({"display":"block"});
                $("#arrBtn").css({"display":"none"});
                $("#collButt").css({"display":"block"});
            }
        });
    }
    //移除快件
    function delExpr(rowIndex, stuName, arriveTime) {
        // alert(rowIndex + "," + stuName + "," + arriveTime);
        // var exprTable = document.getElementById("exprTable");
        $.ajax({
            type: "POST",
            url: "AdminExpressServlet?method=del",
            data: "stuName=" + stuName + "&arriveTime=" + arriveTime,
            success: function (data) {
                data = JSON.parse(data);
                var result = data.result;
                if (result) {
                    // exprTable.deleteRow(rowIndex);
                    // get(0)转为dom对象
                    $("#exprTable").get(0).deleteRow(rowIndex);
                    alert("删除成功！");
                } else {
                    alert("删除失败！");
                }
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
                $("#currPage").val(${sessionScope.exprPage.totalPage });
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
    var arrive = document.getElementById("arrive");
    var collect = document.getElementById("collect");
    var collectNum = document.getElementById("collectNum");
    var collectName = document.getElementById("collectName");
    var collectPhone = document.getElementById("collectPhone");

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
        if (arrive.value == "") {
            alert("请输入到达时间");
            arrive.focus();
            return false;
        }
        if (collectNum.value == "") {
            alert("请输入快件数量");
            collectNum.focus();
            return false;
        }
        return true;
    }

    function modify() {
        if (collect.value == "") {
            alert("请输入取件时间");
            collect.focus();
            return false;
        }
        if (collectName.value == "") {
            alert("请输入取件人姓名");
            collectName.focus();
            return false;
        }
        if (collectPhone.value == "") {
            alert("请输入取件人电话");
            collectPhone.focus();
            return false;
        }
        return true;
    }

</script>
</body>
</html>

