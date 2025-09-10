<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>予約キャンセル | 高速バス予約</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>

<div class="container">
  <h1>予約キャンセル</h1>

  <c:if test="${not empty error}">
    <p style="color:red">${error}</p>
  </c:if>

  <form action="${pageContext.request.contextPath}/cancel" method="post">
    <div>
      <label>予約番号</label><br>
      <input type="text" name="reservationCode" required>
    </div>
    <div>
      <label>電話番号</label><br>
      <input type="tel" name="phone" required>
    </div>
    <div>
      <button type="submit">キャンセルする</button>
    </div>
  </form>
</div>
</body>
</html>
