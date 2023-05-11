<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-11
  Time: 오전 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <h2>상세조회</h2>
    <table>
        <tr>
            <th>id</th>
            <th>${board.id}</th>
        </tr>
        <tr>
            <th>작성자</th>
            <th>${board.boardWriter}</th>
        </tr>
        <tr>
            <th>제목</th>
            <th>${board.boardTitle}</th>
        </tr>
        <tr>
            <th>내용</th>
            <th>${board.boardContents}</th>
        </tr>
        <tr>
            <th>작성시간</th>
            <th>
                <fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>
            </th>
        </tr>
        <tr>
            <th>첨부파일</th>
            <th>
                <c:forEach items="${boardFileList}" var="boardFile">
                    <c:if test="${board.fileAttached ==1 }">
                        <img src="${pageContext.request.contextPath}/upload/${boardFile.storedFileName}"
                             alt="" width="150" height="150">
                    </c:if>
                </c:forEach>
            </th>
        </tr>
        <tr>
            <th>조회수</th>
            <th>${board.boardHits}</th>
        </tr>
    </table>
    <button onclick="board_update('${board.id}')">수정</button>
    <button onclick="board_delete('${board.id}')">삭제</button>
    <button onclick="board_list()">목록</button>
</div>
<%@include file="../componnet/footer.jsp" %>

</body>
<script>
    const board_update = () => {
        const id =${board.id};
        location.href = "/board/update?id=" + id;
    }
    const board_delete = () => {
        const id =${board.id};
        location.href = "/board/delete-check?id=" + id;
    }
    const board_list = () => {
        const type = '${type}';
        const q ='${q}';
        const page = '${page}'
        location.href = "/board/paging?page=" +page + "&type=" + type + "&q=" +q ;
    }
</script>
</html>
