<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="nav">
    <ul>
        <li>
            <a href="/">메인으로</a>
        </li>
        <li>
            <a href="/member/save">회원가입</a>
        </li>
        <li>
            <a href="/member/login">로그인</a>
        </li>
        <li>
            <a href="#">게시판</a>
        </li>
        <li class="login-name" id="login-area">
        </li>
    </ul>
</div>

<script>
    const loginArea = document.getElementById("login-area");
    const loginEmail = '${sessionScope.loginEmail}';
    console.log(loginEmail.length);
    if (loginEmail.length != 0) {
        loginArea.innerHTML = "<a href='/mypage' style='color: black;'>"+loginEmail +"님 환영해요!</a>"+
            "<a href='/logout'>logout</a>";
    } else {
        loginArea.innerHTML = "<a href='/login'>login</a>";
    }
</script>