<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ログイン画面 | 高速バス予約</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>

<div class="container">
    <h1>ログイン</h1>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <h2>バス会社ログイン</h2>
    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <input type="hidden" name="target" value="company"/>
        <div>
            <label for="companyId">ログインID</label>
            <input type="text" id="companyId" name="loginId" required>
        </div>
        <div>
            <label for="companyPw">パスワード</label>
            <input type="password" id="companyPw" name="password" required>
        </div>
        <div>
            <button type="submit">ログイン</button>
        </div>
    </form>

    <h2>管理者ログイン</h2>
    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <input type="hidden" name="target" value="admin"/>
        <div>
            <label for="adminId">ログインID</label>
            <input type="text" id="adminId" name="loginId" required>
        </div>
        <div>
            <label for="adminPw">パスワード</label>
            <input type="password" id="adminPw" name="password" required>
        </div>
        <div>
            <button type="submit">ログイン</button>
        </div>
    </form>
</div>
</body>
</html>

