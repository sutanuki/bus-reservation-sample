<%@ page contentType="text/html; charset=UTF-8" %>
<head>
  <meta charset="UTF-8">
  <title>詳細画面 | 高速バス予約</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<h2>便の詳細</h2>
<table class="table-detail">
  <tr><th>会社</th><td>${trip.companyName}</td></tr>
  <tr><th>出発地</th><td>${trip.fromArea}</td></tr>
  <tr><th>到着地</th><td>${trip.toArea}</td></tr>
  <tr><th>出発時刻</th><td>${trip.departureTime}</td></tr>
  <tr><th>到着時刻</th><td>${trip.arrivalTime}</td></tr>
  <tr><th>種別</th><td>${trip.type}</td></tr>
  <tr><th>残席</th><td>${trip.availableSeats}/${trip.capacity}</td></tr>
</table>

<form action="${pageContext.request.contextPath}/reservation" method="get">
  <input type="hidden" name="tripId" value="${trip.id}">
  <button type="submit">予約へ進む</button>
</form>


