<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 3:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <h2>입력</h2>
    <table>
        <tr>
            <th>글번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성시간</th>
            <th>조회수</th>
        </tr>
        <c:forEach items="${boardList}" var="board">
            <tr>
                <th>${board.id}</th>
                <th>${board.boardTitle}</th>
                <th>${board.boardWriter}</th>
                <th>${board.boardCreatedDate}</th>
                <th>${board.boardHits}</th>
            </tr>
        </c:forEach>
    </table>

</div>
<%@include file="../componnet/footer.jsp" %>
</body>
</html>
