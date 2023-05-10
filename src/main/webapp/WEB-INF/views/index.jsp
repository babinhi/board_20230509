<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오전 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<%@include file="componnet/header.jsp"%>
<%@include file="componnet/nav.jsp"%>
<input type="button" onclick="go_save()" value="회원가입">
<input type="button" onclick="go_login()" value="로그인">
<input type="button" onclick="go_list()" value="글목록">
<%@include file="componnet/footer.jsp"%>
</body>
<script>
    const go_save = ()=>{
        location.href="/member/save";
    }
    const go_login= () =>{
        location.href = "/member/login";
    }
    const go_list = () =>{
        location.href = "/board/paging";
    }
</script>
</html>
