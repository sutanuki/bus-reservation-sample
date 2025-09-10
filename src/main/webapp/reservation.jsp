<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8"><title>予約画面 | 高速バス予約</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>
<div class="container">
  <h1>予約画面</h1>
  <p>
    出発地: ${trip.fromArea} → 到着地: ${trip.toArea}<br>
    出発日時: ${trip.departureTime} / 到着日時: ${trip.arrivalTime}<br>
    種別: ${trip.type} / 残席: ${trip.availableSeats}
  </p>

  <form action="${pageContext.request.contextPath}/reservation" method="post">
    <input type="hidden" name="tripId" value="${trip.id}">
    <div>
      <label>お名前</label><br>
      <input type="text" name="name" required>
    </div>
    <div>
      <label>電話番号</label><br>
      <input type="tel" name="phone" required>
    </div>
    <div>
      <button type="submit">予約する</button>
    </div>
  </form>
</div>
</body>
</html>