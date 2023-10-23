<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%
        request.setCharacterEncoding("UTF-8");
    %>
    <meta charset="UTF-8">
    <title>编辑图书</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="编辑图书"/>
</jsp:include>
<div id="main">
    <form action="BookServlet?action=update&pageNo=${pageNo}" enctype="multipart/form-data" method="post">
        <%--        图片id--%>
        <input name="id" type="hidden" value="${book.id}">
        <%--            图片原路径--%>
        <input name="oldPath" type="hidden" value="${book.imgPath}"/>
            <%--修改页面的页码--%>
<%--        <input name="pageNo" type="hidden" value="${pageNo}">--%>
        <table>
            <%--      <tr>--%>
            <%--        <td>名称</td>--%>
            <%--        <td>价格</td>--%>
            <%--        <td>作者</td>--%>
            <%--        <td>销量</td>--%>
            <%--        <td>库存</td>--%>
            <%--        <td colspan="2">操作</td>--%>
            <%--      </tr>--%>
            <tr>
                <td>名称</td>
                <td><input name="name" type="text" value="${book.name}"/></td>
            </tr>
            <tr>
                <td>价格</td>
                <td><input id="price" name="price" type="text" value="${book.price}"
                           onkeyup="if (!(/^[1-9]\d*.\d*|0.\d*[1-9]\d*$/.test(this.value)||/^[0-9]*$/.test(this.value))){alert('填写错误,请填写非负数字(小数)');this.value=''}"/>
                </td>
            </tr>
            <tr>
                <td>作者</td>
                <td><input name="author" type="text" value="${book.author}"/></td>
            </tr>
            <tr>
                <td>销量</td>
                <td><input id="salse" name="sales" type="text" value="${book.sales}"
                           onkeyup="if (! /^[0-9]*$/.test(this.value)){alert('填写错误,请填写非负数字');this.value=''}"/></td>
            </tr>
            <tr>
                <td>库存</td>
                <td><input id="stock" name="stock" type="text" value="${book.stock}"
                           onkeyup="if (! /^[0-9]*$/.test(this.value)){alert('填写错误,请填写非负数字');this.value=''}"/></td>
            </tr>
            <tr>
                <td>封面</td>
                <td>
                    <input name="imgPath" type="file" id="file_input" onchange="show_image()"/>
                    <img src="${book.imgPath}" alt="" id="show_img" width="100px" height="100px"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>
</div>

<%@include file="/pages/common/botton.jsp" %>
</body>
</html>
<script>
    function show_image() {
        //抓取到上传图片的input标签的信息
        file_input = document.getElementById("file_input");
        //抓取到需要展示预览图的img标签信息
        show_img = document.getElementById("show_img");
        //回去预览图的src属性信息
        show_img.src = window.URL.createObjectURL(file_input.files[0]);
        //改变style属性中block的值
        show_img.style.display = 'block';
    }
</script>