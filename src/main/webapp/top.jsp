<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>トップ | 高速バス予約</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>


<div class="container">
  <h1>高速バス予約サイト</h1>

  <form action="${pageContext.request.contextPath}/search" method="get">
    <div>
      <label>出発日</label>
      <input type="date" name="date" required>
    </div>
    <div>
      <label>出発地</label>
      <select name="fromArea" required>
        <option value="">--選択--</option>
        <option value="東京">東京</option>
        <option value="大阪">大阪</option>
        <option value="名古屋">名古屋</option>
      </select>
    </div>
    <div>
      <label>到着地</label>
      <select name="toArea" required>
        <option value="">--選択--</option>
        <option value="東京">東京</option>
        <option value="大阪">大阪</option>
        <option value="名古屋">名古屋</option>
      </select>
    </div>
    <div>
      <label>昼行/夜行</label>
      <select name="type">
        <option value="">指定なし</option>
        <option value="昼行">昼行</option>
        <option value="夜行">夜行</option>
      </select>
    </div>
    <div><button type="submit">検索</button></div>
  </form>

  <h2>残席わずかの便</h2>
  <table>
    <tr>
      <th>会社</th><th>出発地</th><th>到着地</th>
      <th>出発</th><th>到着</th><th>種別</th><th>残席</th><th>詳細</th>
    </tr>
    <c:forEach var="trip" items="${fewSeatsTrips}">
      <tr>
        <td>${trip.companyName}</td>
        <td>${trip.fromArea}</td>
        <td>${trip.toArea}</td>
        <td>${trip.departureTime}</td>
        <td>${trip.arrivalTime}</td>
        <td>${trip.type}</td>
        <td>${trip.availableSeats}/${trip.capacity}</td>
        <td>
          <form action="<c:url value='/detail'/>" method="get" style="margin:0;">
            <input type="hidden" name="tripId" value="${trip.id}">
            <button type="submit">詳細</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty fewSeatsTrips}">
      <tr><td colspan="8">表示できる便がありません。</td></tr>
    </c:if>
  </table>
</div>
</body>
</html>