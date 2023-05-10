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
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>


</head>
<body>
<%@include file="../componnet/header.jsp" %>
<%@include file="../componnet/nav.jsp" %>
<div id="section">
    <form action="/member/save" method="post" id="save-form">
        <input type="text" name="memberEmail" id="member-email" onblur="email_check()" placeholder="이메일">
        <p id="check-result"></p><br>
        <input type="text" name="memberPassword" id="member-password" onblur="password_check()" placeholder="비밀번호"><br>
        <p id="pass-result"></p><br>
        <input type="text" id="password-check" onblur="check_password()" placeholder="비밀번호"><br>
        <p id="pass-check"></p><br>
        <input type="text" name="memberName" id="member-check" onblur="name_check()" placeholder="성명"><br>
        <p id="name-result"></p><br>
        <input type="text" name="memberMobile" id="member-mobile" onblur="mobile_check()" placeholder="전화번호"><br>
        <p id="mobile-result"></p><br>
        <input type="file" name="boardFile" placeholder="프로필사진"> <br>
        <input type="submit" value="가입하기">
    </form>
</div>
<%@include file="../componnet/footer.jsp" %>

</body>
<script>
    <!-- 이벤트 리스너 적용 -->
    const saveForm = document.getElementById("save-form");
    saveForm.addEventListener("submit", function (e) {
        e.preventDefault(); // 해당 요소의 기본 동작을 수행하지 않을 때
        // 가입하기 버튼을 아무리 눌러도 서브밋을 막음

        const email = document.getElementById("member-email");
        const password = document.getElementById("member-password");
        const check = document.getElementById("password-check");
        const name = document.getElementById("member-check");
        const mobile = document.getElementById("member-mobile");
        if (email.value == "") {
            alert("이메일을 입력하세요");
            email.focus();
        } else if (password.value == "") {
            alert("비밀번호를 입력하세요");
            password.focus();
        }else if(check.value==""){
            alert("비밀번호를 확인해주세요");
            check.focus();
        }else if (name.value == "") {
            alert("이름을 입력하세요");
            name.focus();
        } else if (mobile.value == "") {
            alert("전화번호를 입력하세요");
            mobile.focus();
        } else if (password_check()) {
            alert("비밀번호를 다시입력해주세요 ");
            password.focus();
        } else if (mobile_check()) {
            alert("전화번호를 다시입력해주세요");
            mobile.focus();
        } else {
            saveForm.submit();
        }

    });

    const password_check = () => {
        const password = document.getElementById("member-password").value;
        const resultp = document.getElementById("pass-result");
        const exp = /^(?=.*[a-z])(?=.*\d)(?=.*[-_!#])[A-Za-z\d-_!#]{5,10}$/;

        if (password.match(exp)) {
            resultp.innerHTML = "사용가능한 비밀번호입니다.";
            resultp.style.color = "green";
        } else {
            resultp.innerHTML = "소문자,숫자,특수문자(-_!# 중) 필수입력 입니다\n5~10글자 내로 입력하세요";
            resultp.style.color = "red";
        }

    }
    const check_password =() =>{
        const password = document.getElementById("member-password").value;
        const check = document.getElementById("password-check").value;
        const checkresult = document.getElementById("pass-check");

        if(password == check){
            checkresult.innerHTML = "비밀번호가 일치합니다";
            checkresult.style.color = "green";
        }else {
            checkresult.innerHTML = "비밀번호를 확인해주세요";
            checkresult.style.color = "red";
        }

    }

    const name_check = () => {
        const name = document.getElementById("member-check").value;
        const resultn = document.getElementById("name-result");
        const exp = /^[가-힣a-zA-Z]{2,10}$/

        if (name.match(exp)) {
            resultn.innerHTML = "멋진 이름이군요! 하핫 :)";
            resultn.style.color = "green";

        } else {
            resultn.innerHTML = "다시한번 확인해주세요";
            resultn.style.color = "red";
        }

    }
    const mobile_check = () => {
        const mobile = document.getElementById("member-mobile").value;
        const resultm = document.getElementById("mobile-result");
        const exp = /^\d{3}-\d{4}-\d{4}$/;

        if (!(mobile.match(exp))) {
            resultm.innerHTML = "000 - 0000 - 0000(기호필수)으로 입력해주세요";
            resultm.style.color = "red";
        } else {
            resultm.innerHTML = "입력 가능합니다";
            resultm.style.color = "green";
        }

    }

    const email_check = () => {
        const email = document.getElementById("member-email").value;
        const result = document.getElementById("check-result");
        const exp = /^[a-z\d]{5,15}$/;
        $.ajax({
            type: "post",
            url: "/member/email-check",
            data: {
                "memberEmail": email
            },
            success: function () {
                if (email.length == 0) {
                    result.innerHTML = "필수입력입니다";
                    result.style.color = "red";
                    return false;
                } else if (email.match(exp)) {
                    result.innerHTML = "사용가능한 이메일입니다.";
                    result.style.color = "green";
                    return true;
                } else {
                    result.innerHTML = "6~15자 사이로 입력해주세요.";
                    result.style.color = "red";
                    return false;
                }
            },
            error: function () {
                result.innerHTML = "이미 사용 중인 이메일입니다.";
                result.style.color = "red";
                return false;
            }
        });
    }

</script>
</html>
