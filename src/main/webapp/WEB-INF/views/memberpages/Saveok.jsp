<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-10
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    const save ='${save}';
    console.log(save);
    if(save>0){
        alert("회원가입에 실패하셨습니다\n 다시 진행해주세요");
        location.href = "/member/save";

    }else {
        alert("회원가입에 성공하셨습니다");
        location.href="/member/login";
    }


</script>
</body>
</html>
