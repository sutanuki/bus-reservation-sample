<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>会社用管理画面 | 高速バス予約</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>
<div class="container">
  <h1>${companyName} 管理画面</h1>

  <h2>自社便一覧</h2>
  <table>
    <tr>
      <th>出発地</th><th>到着地</th><th>出発</th><th>到着</th>
      <th>種別</th><th>空席/定員</th>
    </tr>
    <c:forEach var="t" items="${companyTrips}">
      <tr>
        <td>${t.fromArea}</td>
        <td>${t.toArea}</td>
        <td>${t.departureTime}</td>
        <td>${t.arrivalTime}</td>
        <td>${t.type}</td>
        <td>${t.availableSeats}/${t.capacity}</td>
      </tr>
    </c:forEach>
    <c:if test="${empty companyTrips}">
      <tr><td colspan="7">登録された便はありません。</td></tr>
    </c:if>
  </table>

  <h2>新規便登録</h2>
  <form class="inline" action="${pageContext.request.contextPath}/company" method="post">
    <div>
      <label>出発地</label><br>
      <input type="text" name="fromArea" required>
    </div>
    <div>
      <label>到着地</label><br>
      <input type="text" name="toArea" required>
    </div>
    <div>
      <label>出発日時</label><br>
      <input type="datetime-local" name="departureTime" required>
    </div>
    <div>
      <label>到着日時</label><br>
      <input type="datetime-local" name="arrivalTime" required>
    </div>
    <div>
      <label>最終予約受付日時</label><br>
      <input type="datetime-local" name="limitTime" required>
    </div>
    <div>
      <label>座席数</label><br>
      <input type="number" name="capacity" min="1" required>
    </div>
    <div><button type="submit">登録</button></div>
  </form>
</div>
</body>
</html>