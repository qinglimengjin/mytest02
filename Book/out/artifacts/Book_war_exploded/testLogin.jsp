<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘纪新
  Date: 2022/10/11
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

</head>
<body>
<c:choose>
    <%--session 里面的user对象为null 并且cookie中的用户与密码不为null,就自动登录 --%>
    <c:when test="${not empty cookie.username && not empty cookie.password && empty sessionScope.user}">
        <jsp:forward page="/LoginServlet">
            <jsp:param name="action" value="loginTest"/>
            <jsp:param name="username" value="${cookie.username.value}"/>
            <jsp:param name="password" value="${cookie.password.value}"/>
            <jsp:param name="testurl" value="testLogin.jsp"/>
        </jsp:forward>
    </c:when>
    <c:otherwise>
        <jsp:forward page="BookServlet?action=searchPage"></jsp:forward>
    </c:otherwise>
</c:choose>
<form action="LoginServlet?action=loginTest" method="post">
    用户名：<input type="text" name="username" value="${cookie.username.value}"> <br>
    密码：<input type="password" name="password" value="${cookie.password.value}"> <br>
    <input type="submit" value="登录">
</form>
</body>
</html>
