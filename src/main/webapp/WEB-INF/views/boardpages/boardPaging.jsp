<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-10
  Time: 오전 9:16
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


</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <div class="container" id="search-area">
        <form action="/board/paging" method="get">
            <select name="type">
                <option value="boardTitle">제목</option>
                <option value="boardWriter">작성자</option>
            </select>
            <input type="text" name="q" placeholder="검색어를 입력하세요">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="container" id="list">
        <table class="table table-striped table-hover text-center">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
            <c:forEach items="${boardList}" var="board">
                <tr>
                    <td>${board.id}</td>
                    <td>
                        <a href="/board?id=${board.id}&page=${paging.page}&q=${q}&type=${type}">${board.boardTitle}</a>
                    </td>
                    <td>${board.boardWriter}</td>
                    <td>
                        <fmt:formatDate value="${board.boardCreatedDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </td>
                    <td>${board.boardHits}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="container">
        <ul class="pagination justify-content-center">
            <c:choose>
                <%-- 현재 페이지가 1페이지면 이전 글자만 보여줌 --%>
                <c:when test="${paging.page<=1}">
                    <li class="page-item disabled">  <%--클래스는 부트스트랩 클래스임 --%>
                        <a class="page-link">[이전]</a>
                    </li>
                </c:when>
                <%-- 1페이지가 아닌 경우에는 [이전]을 클릭하면 현재 페이지보다 1 작은 페이지 요청 --%>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="/board/paging?page=${paging.page-1}&q=${q}&type=${type}">[이전]</a>
                            <%-- /board/paging?page=${i}&q=${q}: page라는 파라미터와q라는 파라미터를 둘다 보냄--%>
                    </li>
                </c:otherwise>
            </c:choose> <%-- 여기까지 이전--%>

            <%--  for(int i=startPage; i<=endPage; i++)      --%>
            <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1"> <%--반복변수 i일때 ~ --%>
                <c:choose>
                    <%-- 요청한 페이지에 있는 경우 현재 페이지 번호는 텍스트만 보이게 --%>
                    <c:when test="${i eq paging.page}">
                        <li class="page-item active">
                            <a class="page-link">${i}</a>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="/board/paging?page=${i}&q=${q}&type=${type}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose> <%-- 다음에 대한 처리부분--%>
                <c:when test="${paging.page>=paging.maxPage}">
                    <li class="page-item disabled">
                        <a class="page-link">[다음]</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="/board/paging?page=${paging.page+1}&q=${q}&type=${type}">[다음]</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<%@include file="../componnet/footer.jsp" %>
</body>
</html>
