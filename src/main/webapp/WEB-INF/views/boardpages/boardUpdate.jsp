<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 3:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <form action="/board/update" method="post" name="updateForm">
        <h2>게시글 수정</h2>
        <table>
            <tr>
                <th>제목과 내용만 수정 가능</th><br><br>
            </tr>
            <tr>
                <th>글번호</th>
                <th><input type="text" name="id" value="${board.id}" readonly></th>
            </tr>
            <tr>
                <th>제목</th>
                <th><input type="text" name="boardTitle" value="${board.boardTitle}" placeholder="수정할 제목"></th>
            </tr>
            <tr>
                <th>작성자</th>
                <th><input type="text" name="boardWriter" value="${board.boardWriter}" readonly></th>
            </tr>

            <tr>
                <th>내용</th>
                <th><input type="text" name="boardContents" value="${board.boardContents}" placeholder="수정할 내용"></th>
            </tr>
            <tr>
                <th>작성시간</th>
                <th><input type="text" name="boardCreatedDate" value="<fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" readonly></th>
            </tr>
            <tr>
                <th>파일</th>
                <th><input type="text" name="fileAttached" value="${board.fileAttached}" readonly></th>
            </tr>
                <th>
                    <input type="submit" value="수정">
                </th>
            </tr>
        </table>
    </form>
</div>
<%@include file="../componnet/footer.jsp" %>
</body>

</html>
