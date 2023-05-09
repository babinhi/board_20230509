<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">


</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <form action="member/save" method="post">
        <input type="text" name="memberEmail" id="member-email" onblur="email_check()" placeholder="이메일">
        <p id="check-result"></p><br>
        <input type="text" name="memberPassword" placeholder="비밀번호"><br>
        <input type="text" name="memberName" placeholder="성명"><br>
        <input type="text" name="memberMobile" placeholder="전화번호"><br>
        <input type="file" name="boardFile" placeholder="프로필사진"> <br>
        <input type="submit" value="가입하기">
    </form>
</div>
<%@include file="../componnet/footer.jsp" %>

</body>
<script>
    const email_check = () => {
        const email = document.getElementById("member-email").value;
        const result = document.getElementById("check-result");
        $.ajax({
            type: "post",
            url: "/member/email-check",
            data: {
                "memberEmail": email
            },
            success: function () {
                result.innerHTML = "사용가능한 이메일입니다.";
                result.style.color = "green";
            },
            error: function () {
                result.innerHTML = "이미 사용 중인 이메일입니다.";
                result.style.color = "red";
            }
        });
    }
</script>
</html>
