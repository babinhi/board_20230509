<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-11
  Time: 오전 9:43
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
    <h2> 회원정보 수정 </h2>
    <form action="/member/update" method="post" name="updateForm">
        <input type="text" name="id" value="${member.id}" readonly> <br>
        <input type="text" name="memberEmail" value="${sessionScope.loginEmail}" readonly placeholder="이메일"> <br>
        <input type="text" name="memberPassword" id="memberPassword" placeholder="비밀번호"> <br>
        <input type="text" name="memberName" value="${member.memberName}" placeholder="이름"> <br>
        <input type="text" name="memberMobile" value="${member.memberMobile}" placeholder="전화번호"> <br>
        <input type="button" onclick="update_check()" value="수정">
    </form>
</div>
<%@include file="../componnet/footer.jsp" %>
</body>
<script>
    // 비밀번호 확인
    const update_check = () => {
        //  수정할 비밀번호
        const inputPass = document.getElementById("memberPassword").value;
        // 서버에 저장되있는 비밀번호
        const DBPass = '${member.memberPassword}';
        if (inputPass == DBPass) {
            alert("사용하던 비밀번호 입니다 다시 입력해주세요");
        } else {
            document.updateForm.submit();
        }
    }

</script>
</html>
