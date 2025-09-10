<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>完了画面 | 高速バス予約</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>

<div class="container">
  <h1>処理完了</h1>

  <p>
    <c:choose>
      <c:when test="${not empty message}">
        ${message}
      </c:when>
      <c:otherwise>
        処理が完了しました。
      </c:otherwise>
    </c:choose>
  </p>

  <p><a href="${pageContext.request.contextPath}/top">トップへ戻る</a></p>
</div>
</body>
</html>
