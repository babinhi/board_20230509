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
        <table>
            <tr>
                <th colspan='7' style="text-align: center">제목과 내용만 수정이 가능합니다 </th><br><br>
            </tr>
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>내용</th>
                <th>조회수</th>
                <th>작성시간</th>
                <th>파일</th>

            </tr>
            <tr>
                <td>
                    <input type="text" name="id" value="${board.id}" readonly>
                </td>
                <td>
                    <input type="text" name="boardTitle" value="${board.boardTitle}">
                </td>
                <td><input type="text" name="boardWriter" value="${board.boardWriter}" readonly>
                </td>
                <td><input type="text" name="boardContents" value="${board.boardContents}">
                </td>
                <td><input type="text" name="boardHits" value="${board.boardHits}" readonly>
                </td>
                <td>
                    <input type="text" name="boardCreatedDate" value="<fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" readonly>

                </td>
                <td><input type="text" name="fileAttached" value="${board.fileAttached}" readonly></td>
                <%--            <td><input type="text" name="boardFile" value="${board.boardFile}" hidden="hidden"></td>--%>
                <td>
                    <input type="submit" value="수정">
                </td>
            </tr>
        </table>
    </form>
</div>
<%@include file="../componnet/footer.jsp" %>
</body>
</html>
