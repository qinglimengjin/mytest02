<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">${param.msg}</span>
    <div>
        <a href="BookServlet?action=page&pageNo=1&pageSize=4">图书管理</a>
        <a href="pages/manager/order_manager.jsp">订单管理</a>
        <a href="BookServlet?action=searchPage">返回商城</a>
    </div>
</div>
</body>
</html>
