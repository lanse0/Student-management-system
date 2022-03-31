<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="tag.jsp" %>
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

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.4.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/json2.js"></script>

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
                <li><a href="admin_student_information.jsp">宿舍信息</a></li>
                <li><a href="admin_student_expess.jsp">快件信息</a></li>
                <li><a href="admin_student_guarantee.jsp">维修信息</a></li>
                <li><a href="admin_student_leavereturn.jsp">学生离校与返校</a></li>
                <li><a href="admin_student_laterecord.jsp">晚归记录</a></li>
                <li class="active"><a href="admin_student_fee.jsp">水电费信息</a></li>
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
            <h2 class="sub-header">水电费查询</h2>

            <form class="form-horizontal clearfix" role="form"
                  action="${pageContext.request.contextPath}/AdminFeeServlet" method="post" style="">
                <div class="clearfix sub-header">
                    <input type="hidden" name="method" value="list">
                    <div class="form-grou">
                        <label class="control-label">宿舍号</label>
                        <div class="col-lg-4">
                            <select name="dormIdSearch" class="form-control">
                                <option value="">请选择</option>
                                <c:forEach items="${dorms}" var="dorm">
                                    <option value="${dorm.id}"
                                            <c:if test="${sessionScope.feeFilter['dormId']==dorm.id}">selected="selected"</c:if>>${dorm.number}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-grou">
                        <label class="control-label">学年</label>
                        <div class="col-lg-4">
                            <select class="form-control" name="yearSear">
                                <option value="">选择学年</option>
                                <c:forEach items="${sessionScope.feeYears }" var="year">
                                    <option value="${year }"
                                            <c:if test="${sessionScope.feeFilter['year']==year}">selected="selected"</c:if>>${year }
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-grou">
                        <label class="control-label">时间</label>
                        <div class="col-lg-4">
                            <select class="form-control" name="monSear">
                                <option value="">选择月份</option>
                                <option value="1" <c:if test="${sessionScope.feeFilter['month']==1}">selected="selected"</c:if>>一月</option>
                                <option value="2" <c:if test="${sessionScope.feeFilter['month']==2}">selected="selected"</c:if>>二月</option>
                                <option value="3" <c:if test="${sessionScope.feeFilter['month']==3}">selected="selected"</c:if>>三月</option>
                                <option value="4" <c:if test="${sessionScope.feeFilter['month']==4}">selected="selected"</c:if>>四月</option>
                                <option value="5" <c:if test="${sessionScope.feeFilter['month']==5}">selected="selected"</c:if>>五月</option>
                                <option value="6" <c:if test="${sessionScope.feeFilter['month']==6}">selected="selected"</c:if>>六月</option>
                                <option value="7" <c:if test="${sessionScope.feeFilter['month']==7}">selected="selected"</c:if>>七月</option>
                                <option value="8" <c:if test="${sessionScope.feeFilter['month']==8}">selected="selected"</c:if>>八月</option>
                                <option value="9" <c:if test="${sessionScope.feeFilter['month']==9}">selected="selected"</c:if>>九月</option>
                                <option value="10" <c:if test="${sessionScope.feeFilter['month']==10}">selected="selected"</c:if>>十月</option>
                                <option value="11" <c:if test="${sessionScope.feeFilter['month']==11}">selected="selected"</c:if>>十一月</option>
                                <option value="12" <c:if test="${sessionScope.feeFilter['month']==12}">selected="selected"</c:if>>十二月</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-grou" style="float: right">
                        <div>
                            <button type="submit" class="btn btn-default">查询</button>
                        </div>
                    </div>
                </div>
                <div class="table-responsive" style="overflow-x: unset">
                    <table class="table table-striped sub-header">
                        <thead>
                        <tr>
                            <th>月份</th>
                            <th>宿舍号</th>
                            <th>用电量</th>
                            <th>电费</th>
                            <th>用水量</th>
                            <th>水费</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.fees}" var="fees">
                            <tr>
                                <td>${fees.month}</td>
                                <td>${fees.dorm.build}-${fees.dorm.number}</td>
                                <td>${fees.electricfee}</td>
                                <td>${fees.electricnum}</td>
                                <td>${fees.waterfee}</td>
                                <td>${fees.waternum}</td>
                                <td>
                                    <button type="button" id="collBtn"
                                            onclick="modifyLea(${fees.dorm.id}, ${fees.year}, ${fees.month})"
                                            class="btn btn-primary btn-xs">修 改
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="3">
                                共 ${sessionScope.feePage.totalSize} 条记录
                                第 ${sessionScope.feePage.currPage} 页/共 ${sessionScope.feePage.totalPage} 页&nbsp;&nbsp;
                                跳转到第 <input type="text" value="1" size="1"> 页
                            </td>
                            <td colspan="4" align="right">
                                <input type="submit" value="首页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(0);"
                                       <c:if test="${sessionScope.feePage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="上一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(-1);"
                                       <c:if test="${sessionScope.feePage.currPage<=1}">disabled</c:if>>
                                <input type="submit" value="下一页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(1);"
                                       <c:if test="${sessionScope.feePage.currPage>=sessionScope.feePage.totalPage}">disabled</c:if>>
                                <input type="submit" value="尾页" class="btn btn-default btn-sm"
                                       onclick="return subSearchForm(2);"
                                       <c:if test="${sessionScope.feePage.currPage>=sessionScope.feePage.totalPage}">disabled</c:if>>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <input type="hidden" name="currPage" id="currPage" value="${sessionScope.feePage.currPage}">
            </form>

            <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/AdminFeeServlet"
                  method="post">
                <input type="hidden" name="method" value="opera">
                <div class="form-group">
                    <label class="col-sm-2 control-label">时间</label>
                    <div class="col-lg-4">
                        <select class="form-control" name="month" id="month">
                            <option value="">选择月份</option>
                            <option value="1">一月</option>
                            <option value="2">二月</option>
                            <option value="3">三月</option>
                            <option value="4">四月</option>
                            <option value="5">五月</option>
                            <option value="6">六月</option>
                            <option value="7">七月</option>
                            <option value="8">八月</option>
                            <option value="9">九月</option>
                            <option value="10">十月</option>
                            <option value="11">十一月</option>
                            <option value="12">十二月</option>
                        </select>
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
                    <label class="col-sm-2 control-label">用电量</label>
                    <div class="col-lg-4">
                        <input type="number" class="form-control" placeholder="输入用电量" name="electricfee" id="elect">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电费</label>
                    <div class="col-lg-4">
                        <input type="number" class="form-control" placeholder="输入电费" name="electricnum" id="elFee">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用水量</label>
                    <div class="col-lg-4">
                        <input type="number" class="form-control" placeholder="输入用水量" name="waterfee" id="water">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">水费</label>
                    <div class="col-lg-4">
                        <input type="number" class="form-control" placeholder="输入水费" name="waternum" id="watFee">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" id="subBtn" class="btn btn-default" name="submit" value="submit" onclick="return sub();">提交</button>
                        <button type="submit" id="modiBtn" style="display: none" class="btn btn-default" name="submit" value="modify"onclick="return sub();">修改</button>
                    </div>
                </div>
                <input type="hidden" id="year" name = "year" value="">
            </form>
        </div>
    </div>
</div>
<script>
    //修改
    function modifyLea(dormId, year, month) {
        alert(dormId+","+year+","+month);
        $("#month").val(month);
        $("#year").val(year);
        $("#dorm").val(dormId);
        $("#subBtn").css({"display":"none"});
        $("#modiBtn").css({"display":"block"});
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
                $("#currPage").val(${sessionScope.feePage.totalPage });
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

    function sub() {
        var month = document.getElementById("month");
        var dorm = document.getElementById("dorm");
        var elect = document.getElementById("elect");
        var elFee = document.getElementById("elFee");
        var water = document.getElementById("water");
        var watFee = document.getElementById("watFee");
        if (month.value == "") {
            alert("请选择月份");
            month.focus();
            return false;
        }
        if (dorm.value == "") {
            alert("请选择宿舍");
            dorm.focus();
            return false;
        }
        if (elect.value == "") {
            alert("请输入用电量");
            elect.focus();
            return false;
        }
        if (elFee.value == "") {
            alert("请输入电费");
            elFee.focus();
            return false;
        }
        if (water.value == "") {
            alert("请输入用水量");
            water.focus();
            return false;
        }
        if (watFee.value == "") {
            alert("请输入水费");
            watFee.focus();
            return false;
        }
        alert("提交成功");
        return true;
    }
</script>
</body>
</html>

